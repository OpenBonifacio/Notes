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

    @Query("UPDATE NoteEntity SET title = :title, content = :content WHERE id = :id")
    suspend fun updateNote(id: Long, title: String, content: String)

    @Upsert
    suspend fun upsert(noteEntity: NoteEntity) : Long
}

val allNotes = listOf(
    NoteEntity(0, "test", "Ceci est une super note sur la liberté d'expression"),
    NoteEntity(1, "mot de passe", "Ne jamais faire de notes commes ça !!!"),
    NoteEntity(2, "Coucou", "petit message d'amour d'un imposteur trop excentré"),
    NoteEntity(3, "Todo", "bah oui la todo se fait ici en attendant d'avoir un truc plus clean"),
    NoteEntity(4, "test", "Ceci est une super note sur la liberté d'expression"),
    NoteEntity(5, "mot de passe", "Ne jamais faire de notes commes ça !!!"),
    NoteEntity(6, "Coucou", "petit message d'amour d'un imposteur trop excentré"),
    NoteEntity(7, "Todo", "bah oui la todo se fait ici en attendant d'avoir un truc plus clean"),
    NoteEntity(8, "test", "Ceci est une super note sur la liberté d'expression"),
)