package fr.openbonifacio.notes.notes.data.database

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import fr.openbonifacio.notes.notes.data.database.migrations.MIGRATION_1_2
import fr.openbonifacio.notes.notes.data.database.migrations.MIGRATION_2_3
import fr.openbonifacio.notes.notes.data.database.migrations.MIGRATION_3_4

actual class DatabaseFactory(
    private val context: Context
){
    actual fun create(): RoomDatabase.Builder<NotesDatabase> {
        val appContext = context.applicationContext
        val dbFile = appContext.getDatabasePath(NotesDatabase.DB_NAME)

        val builder = Room.databaseBuilder(
            appContext,
            NotesDatabase::class.java,
            dbFile.absolutePath
        )

        return builder.addMigrations(MIGRATION_1_2, MIGRATION_2_3, MIGRATION_3_4)
    }
}