package my.notalonelyday.bsuir.android.notes.ui.details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Save
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.koin.androidx.compose.getViewModel

@ExperimentalMaterial3Api
@Composable
fun DetailScreen(
    noteId: Long,
    closeDetailsScreen: () -> Unit,
    viewModel: DetailsViewModel = getViewModel(),
) {
    val note = viewModel.noteState

    note?.let { note ->
        Box {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState()),
            ) {
                Text(text = "Title:",
                     fontSize = 25.sp,
                )
                BasicTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0x22000000)),
                    value = note.title,
                    onValueChange = { viewModel.updateTitle(it) },
                    textStyle = TextStyle.Default.copy(fontSize = 28.sp),
                )

                Text(text = "Content:",
                     fontSize = 22.sp,
                )
                BasicTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0x22000000)),
                    value = note.text,
                    onValueChange = { viewModel.updateText(it) },
                    textStyle = TextStyle.Default.copy(fontSize = 25.sp)
                )
            }
            FloatingActionButton(
                modifier = Modifier
                    .padding(16.dp)
                    .align(Alignment.BottomEnd),
                onClick = {
                    viewModel.saveNote()
                    closeDetailsScreen()
                },
                containerColor = MaterialTheme.colorScheme.secondary,
                shape = RoundedCornerShape(16.dp),
            ) {
                Icon(
                    imageVector = Icons.Rounded.Save,
                    contentDescription = "Save",
                    tint = Color.White,
                )
            }
        }
    }

    LaunchedEffect(noteId) {
        viewModel.loadNoteIfNotLoaded(noteId)
    }

}
