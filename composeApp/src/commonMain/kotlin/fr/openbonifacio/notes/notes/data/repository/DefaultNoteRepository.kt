package fr.openbonifacio.notes.notes.data.repository

import androidx.sqlite.SQLiteException
import fr.openbonifacio.notes.core.domain.EmptyResult
import fr.openbonifacio.notes.core.domain.Error
import fr.openbonifacio.notes.core.domain.Result
import fr.openbonifacio.notes.core.presentation.DataError
import fr.openbonifacio.notes.notes.data.database.NotesDao
import fr.openbonifacio.notes.notes.data.mappers.toNote
import fr.openbonifacio.notes.notes.data.mappers.toNoteEntity
import fr.openbonifacio.notes.notes.domain.Note
import fr.openbonifacio.notes.notes.domain.NoteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DefaultNoteRepository(
    private val notesDao: NotesDao
): NoteRepository {

    override fun getNotes(): Flow<List<Note>> {
        return notesDao.getNotes()
            .map { entities ->
                entities.map {
                    it.toNote()
                }
            }
    }

    override suspend fun getNote(noteId: Long): Note? {
        return notesDao.getNote(noteId)?.toNote()
    }

    override suspend fun upsertNote(note: Note): Result<Long, DataError.Local>{
        return try {
            val id: Long = notesDao.upsert(note.toNoteEntity())
            Result.Success(id)
        }catch (e: SQLiteException){
            Result.Error(DataError.Local.DISK_FULL)
        }
    }

    override suspend fun deleteNote(note: Note): EmptyResult<DataError.Local> {
        return try {
            notesDao.deleteNote(note.id)
            Result.Success(Unit)
        } catch (e: SQLiteException){
            Result.Error(DataError.Local.UNKNOWN)
        }
    }

    override suspend fun updateNote(note: Note): EmptyResult<DataError.Local> {
        return try {
            notesDao.updateNote(note.id, note.title, note.content)
            Result.Success(Unit)
        }catch (e: SQLiteException){
            Result.Error(DataError.Local.UNKNOWN)
        }
    }


}