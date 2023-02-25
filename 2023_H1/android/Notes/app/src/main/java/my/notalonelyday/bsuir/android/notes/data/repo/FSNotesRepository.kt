package my.notalonelyday.bsuir.android.notes.data.repo

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromStream
import my.notalonelyday.bsuir.android.notes.data.entity.Note
import java.io.OutputStreamWriter

@ExperimentalSerializationApi
class FSNotesRepository(
    private val context: Context
): NotesRepository {
    companion object{
        const val NOTES_FILE_NAME = "notes.json"
    }

    override suspend fun getNoteById(id: Long): Note? {
        return loadNotes().firstOrNull { it.id == id }
    }

    override suspend fun getNotes(): List<Note> {
        return loadNotes()
    }

    override suspend fun addOrReplace(note: Note) {
        val notes = loadNotes().toMutableList()
        val index = notes.indexOfFirst { it.id == note.id }
        if (index == -1) {
            notes.add(note.copy(id = notes.maxByOrNull { it.id!! }?.id?.plus(1) ?: 1))
        } else {
            notes[index] = note
        }
        saveNotes(notes)
    }

    override suspend fun delete(note: Note) {
        val notes = loadNotes()
        saveNotes(notes.filter { it.id != note.id })
    }


    private suspend fun loadNotes(): List<Note> {
        return withContext(Dispatchers.IO) {
            try {
                val file = context.openFileInput(NOTES_FILE_NAME)
                val ret = Json.decodeFromStream<List<Note>>(file)
                file.close()
                ret
            } catch (e: Exception) {
                emptyList()
            }
        }
    }

    private suspend fun saveNotes(notes: List<Note>) {
        withContext(Dispatchers.IO) {
            val osw = OutputStreamWriter(context.openFileOutput(NOTES_FILE_NAME, Context.MODE_PRIVATE))
            osw.write(Json.encodeToString(notes))
            osw.close()
        }
    }
}