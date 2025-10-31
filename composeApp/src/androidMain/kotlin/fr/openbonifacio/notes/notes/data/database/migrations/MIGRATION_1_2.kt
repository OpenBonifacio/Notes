package fr.openbonifacio.notes.notes.data.database.migrations

import androidx.room.migration.Migration
import androidx.sqlite.SQLiteConnection
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.sqlite.execSQL

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