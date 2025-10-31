package fr.openbonifacio.notes.notes.domain

import fr.openbonifacio.notes.core.domain.EmptyResult
import fr.openbonifacio.notes.core.domain.Result
import fr.openbonifacio.notes.core.domain.DataError
import kotlinx.coroutines.flow.Flow

interface NoteRepository {
    fun getNotes() : Flow<List<Note>>

    suspend fun getNote(noteId: String) : Note?

    suspend fun upsertNote(note: Note) : Result<String, DataError.Local>

    suspend fun deleteNote(note: Note) : EmptyResult<DataError.Local>

    suspend fun updateNote(note: Note): EmptyResult<DataError.Local>
}