package my.notalonelyday.bsuir.android.notes.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import my.notalonelyday.bsuir.android.notes.data.entity.Note

@Database(entities = [Note::class], version = 1)
abstract class NotesDatabase : RoomDatabase() {
    abstract fun noteDao(): NotesDao
}