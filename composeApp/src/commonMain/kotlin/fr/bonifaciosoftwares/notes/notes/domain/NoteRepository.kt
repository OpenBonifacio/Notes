package fr.bonifaciosoftwares.notes.notes.domain

import fr.bonifaciosoftwares.notes.core.domain.EmptyResult
import fr.bonifaciosoftwares.notes.core.presentation.DataError
import kotlinx.coroutines.flow.Flow

interface NoteRepository {
    fun getNotes() : Flow<List<Note>>

    //fun getNote(noteId: Long) : Flow<Note>

    suspend fun upsertNote(note: Note) : EmptyResult<DataError.Local>
}