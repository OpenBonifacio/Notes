package fr.openbonifacio.notes.notes.data.database

import androidx.room.RoomDatabase

expect class DatabaseFactory {
    fun create(): RoomDatabase.Builder<NotesDatabase>
}