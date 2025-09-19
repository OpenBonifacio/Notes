package fr.bonifaciosoftwares.notes.notes.data.repository

import fr.bonifaciosoftwares.notes.notes.data.database.NotesDao
import fr.bonifaciosoftwares.notes.notes.data.database.toNote
import fr.bonifaciosoftwares.notes.notes.domain.Note
import fr.bonifaciosoftwares.notes.notes.domain.NoteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DefaultNoteRepository(
    //private val notesDao: NotesDao
): NoteRepository {


    /*override fun getNotes(): Flow<List<Note>> {
        return notesDao.getNotes()
            .map { notesEntities -> notesEntities.map { it.toNote() } }
    }*/

    /*override fun getNote(noteId: Long): Flow<Note> {
        return notesDao.getNote(noteId)
            .map { it.toNote() }
    }*/
    override fun getNotes(): Flow<List<Note>> {
        TODO("Not yet implemented")
    }
}