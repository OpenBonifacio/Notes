package fr.openbonifacio.notes.notes.data.repository

import androidx.sqlite.SQLiteException
import fr.openbonifacio.notes.core.domain.EmptyResult
import fr.openbonifacio.notes.core.domain.Result
import fr.openbonifacio.notes.core.domain.DataError
import fr.openbonifacio.notes.notes.data.database.NotesDao
import fr.openbonifacio.notes.notes.data.mappers.toNote
import fr.openbonifacio.notes.notes.data.mappers.toNoteEntity
import fr.openbonifacio.notes.notes.domain.Note
import fr.openbonifacio.notes.notes.domain.NoteRepository
import fr.openbonifacio.notes.core.util.TimeProvider
import fr.openbonifacio.notes.core.util.generatedUUID
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
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

    override suspend fun getNote(noteId: String): Note? {
        return notesDao.getNote(noteId)?.toNote()
    }

    override suspend fun upsertNote(note: Note): Result<String, DataError.Local>{
        return try {
            val now = TimeProvider.nowMillis()
            if (note.createdAt == 0L) note.createdAt = now
            note.updatedAt = now

            val id = generatedUUID()
            note.id = id

            notesDao.upsert(note.toNoteEntity())
            Result.Success(id)
        }catch (_: SQLiteException){
            Result.Error(DataError.Local.DISK_FULL)
        }
    }

    override suspend fun deleteNote(note: Note): EmptyResult<DataError.Local> {
        return try {
            notesDao.deleteNote(note.id)
            Result.Success(Unit)
        } catch (_: SQLiteException){
            Result.Error(DataError.Local.UNKNOWN)
        }
    }

    override suspend fun updateNote(note: Note): EmptyResult<DataError.Local> {
        return try {
            val now = TimeProvider.nowMillis()
            note.updatedAt = now
            notesDao.updateNote(note.id, note.title, note.content, note.updatedAt)
            Result.Success(Unit)
        }catch (_: SQLiteException){
            Result.Error(DataError.Local.UNKNOWN)
        }
    }


}