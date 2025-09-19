package fr.bonifaciosoftwares.notes.notes.data.database

data class NoteEntity(
    val id: Long,
    val title: String,
    val content: String,
)