package my.notalonelyday.bsuir.android.rssreader.ui.feed

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import org.koin.androidx.compose.get

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun Feed(
    viewModel: FeedViewModel = get()
) {

    val channelState = viewModel.channelFlow.collectAsState(null)
    val errorState = viewModel.errorFlow.collectAsState(null)

    val pullRefreshState = rememberPullRefreshState(
        viewModel.isRefreshing,
        onRefresh = {
            viewModel.updateChannel()
        }
    )


    Box(
        Modifier
            .pullRefresh(pullRefreshState)
            .fillMaxSize()
            .padding(
                horizontal = 15.dp
            )
    ) {
        Text(
            modifier = Modifier.align(Alignment.Center),
            text = errorState.value ?: ""
        )
        val mContext = LocalContext.current
        val openFileLauncher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.GetContent()
        ) { result ->
            result ?: return@rememberLauncherForActivityResult
            val item = mContext.contentResolver.openInputStream(result)
                ?: return@rememberLauncherForActivityResult
            val bytes = item.readBytes()
            println(bytes)
            item.close()
            try {
                viewModel.setFromFile(bytes.decodeToString())
            } catch (_: Exception) {
            }
        }
        LazyColumn {

            item {
                TextField(
                    value = viewModel.channelUrl.value,
                    onValueChange = {
                        viewModel.setUrl(it)
                    }
                )
            }

            item {
                Button(
                    onClick = {
                        openFileLauncher.launch("*/*")
                    }
                ) {
                    Text("Pick file")
                }
            }

            channelState.value?.let { Channel(it) }
        }
        PullRefreshIndicator(
            state = pullRefreshState,
            refreshing = viewModel.isRefreshing,
            modifier = Modifier.align(Alignment.TopCenter)
        )
    }
}
