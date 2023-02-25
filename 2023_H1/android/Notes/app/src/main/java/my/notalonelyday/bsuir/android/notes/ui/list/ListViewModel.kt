package my.notalonelyday.bsuir.android.notes.ui.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import my.notalonelyday.bsuir.android.notes.data.entity.Note
import my.notalonelyday.bsuir.android.notes.data.repo.NotesRepository
import my.notalonelyday.bsuir.android.notes.data.repo.RepoProvider
import org.koin.android.java.KoinAndroidApplication
import org.koin.androidx.compose.get
import org.koin.core.component.KoinComponent
import org.koin.core.context.KoinContext

class ListViewModel(
    private val repoProvider: RepoProvider,
) : ViewModel() {

    private val repository
        get() = repoProvider.getRepo()

    val notesFlow = MutableStateFlow<List<Note>>(emptyList())

    fun loadNotes() {
        viewModelScope.launch {
            updateNotesFlow()
        }
    }

    private suspend fun updateNotesFlow() {
        notesFlow.value = repository.getNotes()
    }

    fun deleteNote(note: Note) {
        viewModelScope.launch {
            repository.delete(note)
            updateNotesFlow()
        }
    }

    fun setRepoType(repoType: RepoProvider.RepoType) {
        repoProvider.repoType = repoType
        viewModelScope.launch {
            updateNotesFlow()
        }
    }

    fun getRepoType() = repoProvider.repoType
}