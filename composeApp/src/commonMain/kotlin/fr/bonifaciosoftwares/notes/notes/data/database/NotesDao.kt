package fr.bonifaciosoftwares.notes.notes.data.database

import fr.bonifaciosoftwares.notes.notes.domain.Note
import kotlinx.coroutines.flow.Flow


interface NotesDao {

    fun getNotes() : Flow<List<NoteEntity>>{
        return allNotes as Flow<List<NoteEntity>>
    }

    /*fun getNote(noteId: Long) : Flow<NoteEntity>{
        for (note in allNotes){
            if (note.id == noteId){
                return note as Flow<NoteEntity>
            }
        }
    }*/
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