package fr.openbonifacio.notes.notes.data.mappers

import fr.openbonifacio.notes.notes.data.database.NoteEntity
import fr.openbonifacio.notes.notes.domain.Note

fun NoteEntity.toNote(): Note {
    return Note(
        id = id,
        title = title,
        content = content,
        createdAt = createdAt,
        updatedAt = updatedAt
    )
}

fun Note.toNoteEntity() : NoteEntity{
    return NoteEntity(
        id = id,
        title = title,
        content = content,
        createdAt = createdAt,
        updatedAt = updatedAt
    )
}