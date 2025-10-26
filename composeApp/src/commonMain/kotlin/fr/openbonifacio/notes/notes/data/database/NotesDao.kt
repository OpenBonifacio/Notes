package fr.openbonifacio.notes.notes.data.database

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow


@Dao
interface NotesDao {

    @Query("SELECT * FROM NoteEntity")
    fun getNotes() : Flow<List<NoteEntity>>

    @Query("SELECT * FROM NoteEntity WHERE id = :id")
    suspend fun getNote(id: Long) : NoteEntity?

    @Query("DELETE FROM NoteEntity WHERE id = :id")
    suspend fun deleteNote(id: Long)

    @Query("UPDATE NoteEntity SET title = :title, content = :content, updatedAt = :updatedAt WHERE id = :id")
    suspend fun updateNote(id: Long, title: String, content: String, updatedAt: Long)

    @Upsert
    suspend fun upsert(noteEntity: NoteEntity) : Long
}