package fr.openbonifacio.notes.notes.data.database.migrations

import androidx.room.migration.Migration
import androidx.sqlite.SQLiteConnection
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.sqlite.execSQL

// Migration 2 -> 3: ajoute des colonnes deleted et status
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
