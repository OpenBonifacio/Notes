package fr.bonifaciosoftwares.notes.notes.data.mappers

import fr.bonifaciosoftwares.notes.notes.data.database.NoteEntity
import fr.bonifaciosoftwares.notes.notes.domain.Note

fun NoteEntity.toNote(): Note {
    return Note(
        id = id,
        title = title,
        content = content
    )
}

fun Note.toNoteEntity() : NoteEntity{
    return NoteEntity(
        id = id,
        title = title,
        content = content
    )
}