package my.notalonelyday.bsuir.android.notes.data.repo

import my.notalonelyday.bsuir.android.notes.data.entity.Note

interface NotesRepository {
    suspend fun getNoteById(id: Long): Note?
    suspend fun getNotes(): List<Note>
    suspend fun addOrReplace(note: Note)
    suspend fun delete(note: Note)
}