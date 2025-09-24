package fr.bonifaciosoftwares.notes.notes.domain

data class Note(
    val id: Long,
    var title: String = "",
    var content: String = "",
    var isFavorite: Boolean = false
)
