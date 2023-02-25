package my.notalonelyday.bsuir.android.notes.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import my.notalonelyday.bsuir.android.notes.data.entity.Note

@Dao
interface NotesDao {

    @Query("SELECT * FROM notes WHERE id = :id")
    suspend fun getById(id: Long): Note?

    @Query("SELECT * FROM notes")
    suspend fun getAll(): List<Note>

    @Delete
    suspend fun delete(note: Note)

    @Insert(onConflict = androidx.room.OnConflictStrategy.REPLACE)
    suspend fun update(note: Note)

}