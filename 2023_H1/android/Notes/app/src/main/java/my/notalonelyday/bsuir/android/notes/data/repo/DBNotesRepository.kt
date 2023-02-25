package my.notalonelyday.bsuir.android.notes.data.repo

import my.notalonelyday.bsuir.android.notes.data.db.NotesDao
import my.notalonelyday.bsuir.android.notes.data.entity.Note

class DBNotesRepository(
    private val notesDao: NotesDao,
) : NotesRepository {
    override suspend fun getNoteById(id: Long): Note? {
        return notesDao.getById(id)
    }

    override suspend fun getNotes(): List<Note> {
        return notesDao.getAll()
    }

    override suspend fun addOrReplace(note: Note) {
        notesDao.update(note)
    }

    override suspend fun delete(note: Note) {
        notesDao.delete(note)
    }
}