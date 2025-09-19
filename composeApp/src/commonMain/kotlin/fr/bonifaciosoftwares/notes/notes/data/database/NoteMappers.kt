package fr.bonifaciosoftwares.notes.notes.data.database

import fr.bonifaciosoftwares.notes.notes.domain.Note

fun NoteEntity.toNote(): Note {
    return Note(
        id = id,
        title = title,
        content = content
    )
}