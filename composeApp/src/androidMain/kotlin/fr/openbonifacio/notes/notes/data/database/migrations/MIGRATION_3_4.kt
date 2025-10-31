package fr.openbonifacio.notes.notes.data.database.migrations

import androidx.room.migration.Migration
import androidx.sqlite.SQLiteConnection
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.sqlite.execSQL
import java.util.UUID

// Migration 3 -> 4: Id de type Long vers String pour être compatible UUID
val MIGRATION_3_4 = object: Migration(3, 4){
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL(
            "CREATE TABLE IF NOT EXISTS NoteEntity_new (" +
                    "id TEXT PRIMARY KEY NOT NULL," +
                    " title TEXT NOT NULL," +
                    " content TEXT NOT NULL," +
                    " createdAt INTEGER NOT NULL DEFAULT 0," +
                    " updatedAt INTEGER NOT NULL DEFAULT 0," +
                    " deleted INTEGER NOT NULL DEFAULT 0," +
                    " status TEXT NOT NULL DEFAULT 'SYNCED')")

        val cursor = db.query(
            "SELECT id, title, content, createdAt, updatedAt, deleted, status FROM NoteEntity"
        )

        val insertStmt = db.compileStatement(
            "INSERT INTO NoteEntity_new (id, title, content, createdAt, updatedAt, deleted, status) VALUES (?, ?, ?, ?, ?, ?, ?)"
        )

        try {
            while (cursor.moveToNext()) {
                val rawId = try {
                    cursor.getLong(0)
                } catch (_: Exception) {
                    cursor.getString(0)?.toLongOrNull() ?: 0L
                }

                val uuid = UUID.nameUUIDFromBytes(("note-$rawId").toByteArray()).toString()

                insertStmt.bindString(1, uuid)
                insertStmt.bindString(2, cursor.getString(1))
                insertStmt.bindString(3, cursor.getString(2))
                insertStmt.bindLong(4, cursor.getLong(3))
                insertStmt.bindLong(5, cursor.getLong(4))
                insertStmt.bindLong(6, cursor.getLong(5))
                val status = if (!cursor.isNull(6)) cursor.getString(6) else "SYNCED"
                insertStmt.bindString(7, status)

                insertStmt.executeInsert()
                insertStmt.clearBindings()
            }
        } finally {
            cursor.close()
            insertStmt.close()
        }

        db.execSQL("DROP TABLE NoteEntity")
        db.execSQL("ALTER TABLE NoteEntity_new RENAME TO NoteEntity")
    }

    override fun migrate(connection: SQLiteConnection) {
        connection.execSQL(
            "CREATE TABLE IF NOT EXISTS NoteEntity_new (" +
                    "id TEXT PRIMARY KEY NOT NULL," +
                    "title TEXT NOT NULL," +
                    "content TEXT NOT NULL," +
                    "createdAt INTEGER NOT NULL," +
                    "updatedAt INTEGER NOT NULL," +
                    "deleted INTEGER NOT NULL," +
                    "status TEXT NOT NULL)"
        )

        val selectStmt = connection.prepare(
            "SELECT id, title, content, createdAt, updatedAt, deleted, status FROM NoteEntity"
        )
        val insertStmt = connection.prepare(
            "INSERT INTO NoteEntity_new (id, title, content, createdAt, updatedAt, deleted, status) VALUES (?, ?, ?, ?, ?, ?, ?)"
        )

        try {
            while (selectStmt.step()) {
                val rawId = try {
                    selectStmt.getLong(0)
                } catch (_: Exception) {
                    selectStmt.getText(0).toLongOrNull() ?: 0L
                }

                val uuid = UUID.nameUUIDFromBytes(("note-$rawId").toByteArray()).toString()

                insertStmt.bindText(1, uuid)
                insertStmt.bindText(2, selectStmt.getText(1))
                insertStmt.bindText(3, selectStmt.getText(2))
                insertStmt.bindLong(4, selectStmt.getLong(3))
                insertStmt.bindLong(5, selectStmt.getLong(4))
                insertStmt.bindLong(6, selectStmt.getLong(5))
                val status = selectStmt.getText(6)
                insertStmt.bindText(7, status)

                insertStmt.step()
                insertStmt.clearBindings()
            }
        } finally {
            selectStmt.close()
            insertStmt.close()
        }

        connection.execSQL("DROP TABLE NoteEntity")
        connection.execSQL("ALTER TABLE NoteEntity_new RENAME TO NoteEntity")
    }

}