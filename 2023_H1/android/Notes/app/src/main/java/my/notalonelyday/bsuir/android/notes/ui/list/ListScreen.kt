@file:OptIn(ExperimentalFoundationApi::class)

package my.notalonelyday.bsuir.android.notes.ui.list

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.Button
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import my.notalonelyday.bsuir.android.notes.data.entity.Note
import my.notalonelyday.bsuir.android.notes.data.repo.RepoProvider
import org.koin.androidx.compose.getViewModel

const val CREATE_NOTE_ID = -1L

@Composable
fun ListScreen(
    openNoteDetails: (Long) -> Unit,
    viewModel: ListViewModel = getViewModel(),
) {

    val notesList by viewModel.notesFlow.collectAsState()
    var isDeleteNoteDialogShown by remember { mutableStateOf(false) }
    var noteToDelete by remember { mutableStateOf<Note?>(null) }

    var isRoom by remember { mutableStateOf(viewModel.getRepoType() == RepoProvider.RepoType.ROOM) }
    Row {
        Text("ROOM")
        Switch(
            checked = isRoom,
            onCheckedChange = { isRoom = it }
        )
        Text("FS")
    }

    LaunchedEffect(isRoom) {
        viewModel.setRepoType(
            if (isRoom) RepoProvider.RepoType.ROOM
            else RepoProvider.RepoType.FS
        )
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                0.dp,
                45.dp,
                0.dp,
                0.dp
            )
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            items(notesList,
                  key = { it.id!! }) {
                ListNoteItem(note = it,
                             onClick = {
                                 openNoteDetails(it.id!!)
                             },
                             onLongClick = {
                                 noteToDelete = it
                                 isDeleteNoteDialogShown = true
                             })
            }
        }
        if (notesList.isEmpty()) {
            Text(
                modifier = Modifier.align(Alignment.Center),
                text = "You have no notes\nCreate your first note"
            )
        }
        FloatingActionButton(
            modifier = Modifier
                .padding(16.dp)
                .align(Alignment.BottomEnd),
            onClick = {
                openNoteDetails(CREATE_NOTE_ID)
            },
            containerColor = MaterialTheme.colorScheme.secondary,
            shape = RoundedCornerShape(16.dp),
        ) {
            Icon(
                imageVector = Icons.Rounded.Add,
                contentDescription = "Add FAB",
                tint = Color.White,
            )
        }
    }


    if (isDeleteNoteDialogShown) {
        Dialog(onDismissRequest = { isDeleteNoteDialogShown = false }) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .background(Color(0x66FFFFFF))
            ) {

                Text(
                    text = "Are you sure want to delete this note?",
                    fontSize = 25.sp
                )
                Button(onClick = { isDeleteNoteDialogShown = false }) {
                    Text(
                        text = "Cancel",
                        fontSize = 25.sp
                    )
                }
                Button(onClick = {
                    viewModel.deleteNote(noteToDelete ?: error("Incosistent state"))
                    isDeleteNoteDialogShown = false
                }) {
                    Text(
                        text = "Delete",
                        fontSize = 25.sp
                    )
                }
            }
        }
    }


    LaunchedEffect(Unit) {
        viewModel.loadNotes()
    }
}

@Composable
fun ListNoteItem(
    note: Note,
    onClick: () -> Unit,
    onLongClick: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .combinedClickable(
                onClick = onClick,
                onLongClick = onLongClick
            )
    ) {
        Text(
            text = note.title,
            modifier = Modifier.fillMaxWidth(),
            fontSize = 30.sp,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1
        )
        Text(
            text = note.text,
            modifier = Modifier.fillMaxWidth(),
            fontSize = 30.sp,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1
        )
    }
}

@Composable
@Preview
fun ListNoteItemPreview() {
    ListNoteItem(note = Note(
        id = 1L,
        title = "Title",
        text = "This is text"
    ),
                 {},
                 {})
}