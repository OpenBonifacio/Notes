package fr.bonifaciosoftwares.notes.notes.data.database

import androidx.room.RoomDatabase

expect class DatabaseFactory {
    fun create(): RoomDatabase.Builder<NotesDatabase>
}