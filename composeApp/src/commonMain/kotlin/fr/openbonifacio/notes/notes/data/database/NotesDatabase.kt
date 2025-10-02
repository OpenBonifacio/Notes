package fr.openbonifacio.notes.notes.data.database

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import fr.openbonifacio.notes.notes.data.database.NotesDatabaseConstructor

@Database(
    entities = [NoteEntity::class],
    version = 1,
    exportSchema = false
)
@ConstructedBy(NotesDatabaseConstructor::class)
abstract class NotesDatabase : RoomDatabase() {

    abstract val notesDao: NotesDao

    companion object{
        const val DB_NAME = "notes.db"
    }
}