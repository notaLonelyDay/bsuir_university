import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.Checkbox
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import di.ServiceLocator
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import vm.ClusterViewModelProtocol
import vm.KMinViewModel.Companion.BASE_CLASS
import kotlin.math.abs

const val automaticDelay = 100L

enum class Method {
    K_MIN,
    MAX_MIN
}

@Composable
@Preview
fun App() {

    var viewModel by remember { mutableStateOf<ClusterViewModelProtocol>(ServiceLocator.kMinViewModel) }


    var canvasSize by remember { mutableStateOf(IntSize(0, 0)) }
    var pointsCount by remember { mutableStateOf(2000) }
    var clazzCount by remember { mutableStateOf(5) }

    var isAutomatic by remember { mutableStateOf(false) }

    val clazzList by viewModel.classesFlow.collectAsState()
    val pointList by viewModel.pointsFlow.collectAsState()

    val colorProvider = ServiceLocator.colorProvider

    var relaxEnabled by remember { mutableStateOf(true) }

    val scope = rememberCoroutineScope()
    var autoJob: Job = Job()

    suspend fun regenerate() {
        autoJob.cancelAndJoin()
        colorProvider.clear()
        relaxEnabled = true
        viewModel.generateInput(
            pointsCount,
            clazzCount,
            1 until canvasSize.width,
            1 until canvasSize.height
        )
    }

    fun changeMethod(method: Method) {
        viewModel = when (method) {
            Method.K_MIN -> ServiceLocator.kMinViewModel
            Method.MAX_MIN -> ServiceLocator.maxMinViewModel
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
    ) {
        Row(
            modifier = Modifier
                .wrapContentSize()
                .padding(16.dp),
        ) {

            Button(
                content = {
                    Text("Relax")
                },
                onClick = {
                    if (!isAutomatic)
                        relaxEnabled = viewModel.relax()
                    else
                        autoJob = scope.launch {
                            relaxEnabled = false
                            while (viewModel.relax() && !relaxEnabled)
                                delay(automaticDelay)
                        }
                },
                enabled = relaxEnabled
            )

            val fontSize = 20.sp
            Text(
                text = "Points:",
                fontSize = fontSize
            )
            TextField(
                value = pointsCount.toString(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                onValueChange = { it ->
                    it.toIntOrNull()?.let {
                        pointsCount = abs(it)
                    }
                }
            )

            Text(
                modifier = Modifier.wrapContentHeight(),
                text = "Classes:",
                fontSize = fontSize
            )
            TextField(
                value = clazzCount.toString(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                onValueChange = { it ->
                    it.toIntOrNull()?.let {
                        clazzCount = abs(it)
                    }
                }
            )

            Text(
                modifier = Modifier.wrapContentHeight(),
                text = "Is automatic:",
                fontSize = fontSize
            )

            Checkbox(
                checked = isAutomatic,
                onCheckedChange = { isAutomatic = it }
            )

            Button(
                onClick = {
                    changeMethod(Method.K_MIN)
                }
            ) {
                Text("K_MIN")
            }
            Button(
                onClick = {
                    changeMethod(Method.MAX_MIN)
                }
            ) {
                Text("MAX_MIN")
            }

            Button(
                onClick = {
                    val oldClasses = clazzList
                    val oldPoints = pointList
                    changeMethod(Method.K_MIN)
                    scope.launch {
                        delay(1001)
                        viewModel.classesFlow.value = oldClasses
                        viewModel.pointsFlow.value = oldPoints.map { it.copy(
                            clazz = BASE_CLASS
                        ) }
                    }
                }
            ) {
                Text("MAX_MIN_and_k")
            }

        }
        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .onGloballyPositioned {
                    canvasSize = it.size
                },
            onDraw = {
                pointList.forEach {
                    drawCircle(
                        colorProvider.getClassColor(it.clazz),
                        radius = 5.dp.toPx(),
                        center = Offset(it.x.toFloat(), it.y.toFloat()),
                    )
                }
                clazzList.forEach {
                    drawCircle(
                        colorProvider.getClassColor(it.clazz),
                        radius = 15.dp.toPx(),
                        center = Offset(it.x.toFloat(), it.y.toFloat())
                    )
                }
            }


        )
    }


    LaunchedEffect(canvasSize, pointsCount, clazzCount, viewModel) {
        regenerate()
    }
}

fun main() = application {
    Window(onCloseRequest = ::exitApplication) {
        App()
    }
}
