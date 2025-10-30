package fr.openbonifacio.notes.notes.data.database

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.sqlite.SQLiteConnection
import androidx.sqlite.execSQL

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

        // Migration 1 -> 2 : ajout des colonnes createdAt et updatedAt avec valeur par défaut 0
        val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL("ALTER TABLE NoteEntity ADD COLUMN createdAt INTEGER NOT NULL DEFAULT 0")
                db.execSQL("ALTER TABLE NoteEntity ADD COLUMN updatedAt INTEGER NOT NULL DEFAULT 0")
            }

            override fun migrate(connection: SQLiteConnection) {
                connection.execSQL("ALTER TABLE NoteEntity ADD COLUMN createdAt INTEGER NOT NULL DEFAULT 0")
                connection.execSQL("ALTER TABLE NoteEntity ADD COLUMN updatedAt INTEGER NOT NULL DEFAULT 0")
            }
        }

        // Migration 2 -> 3: ajoute es colonnes deleted et status
        val MIGRATION_2_3 = object : Migration(2, 3) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL("ALTER TABLE NoteEntity ADD COLUMN deleted INTEGER NOT NULL DEFAULT 0")
                db.execSQL("ALTER TABLE NoteEntity ADD COLUMN status TEXT NOT NULL DEFAULT 'SYNCED'")
            }

            override fun migrate(connection: SQLiteConnection) {
                connection.execSQL("ALTER TABLE NoteEntity ADD COLUMN deleted INTEGER NOT NULL DEFAULT 0")
                connection.execSQL("ALTER TABLE NoteEntity ADD COLUMN status TEXT NOT NULL DEFAULT 'SYNCED'")
            }
        }

        return builder.addMigrations(MIGRATION_1_2, MIGRATION_2_3)
    }
}