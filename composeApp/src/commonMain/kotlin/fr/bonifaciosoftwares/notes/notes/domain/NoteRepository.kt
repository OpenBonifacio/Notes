package fr.bonifaciosoftwares.notes.notes.domain

import kotlinx.coroutines.flow.Flow

interface NoteRepository {
    fun getNotes() : Flow<List<Note>>

    //fun getNote(noteId: Long) : Flow<Note>
}