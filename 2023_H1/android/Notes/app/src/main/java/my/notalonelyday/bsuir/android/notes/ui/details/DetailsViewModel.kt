package my.notalonelyday.bsuir.android.notes.ui.details

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import my.notalonelyday.bsuir.android.notes.data.entity.Note
import my.notalonelyday.bsuir.android.notes.data.repo.RepoProvider

class DetailsViewModel(
    private val repoProvider: RepoProvider,
) : ViewModel() {
    var noteState by mutableStateOf<Note?>(null)
        private set

    private val notesRepository
        get() = repoProvider.getRepo()

    fun loadNoteIfNotLoaded(id: Long) {
        if (noteState != null)
            return

        viewModelScope.launch {
            noteState =
                notesRepository.getNoteById(id)
                    ?: Note(
                        title = "",
                        text = ""
                    )
        }
    }

    fun updateText(text: String) {
        noteState = noteState?.copy(text = text)
    }

    fun updateTitle(title: String) {
        noteState = noteState?.copy(title = title)
    }


    fun saveNote() {
        viewModelScope.launch {
            noteState?.let {
                notesRepository.addOrReplace(it)
            } ?: Log.e(
                "DetailsViewModel",
                "Note is null"
            )
        }
    }
}