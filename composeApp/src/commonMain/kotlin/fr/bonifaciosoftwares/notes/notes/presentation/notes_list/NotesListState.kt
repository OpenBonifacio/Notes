package fr.bonifaciosoftwares.notes.notes.presentation.notes_list

import fr.bonifaciosoftwares.notes.core.presentation.UiText
import fr.bonifaciosoftwares.notes.notes.domain.Note

data class NotesListState(
    val notes: List<Note> = allNotes,
    val isLoading: Boolean = true,
    //val selectedTabIndex: Int = 0,
    val errorMessage: UiText? = null
)

val allNotes = listOf(
    Note(0, "test", "Ceci est une super note sur la liberté d'expression"),
    Note(1, "mot de passe", "Ne jamais faire de notes commes ça !!!"),
    Note(2, "Coucou", "petit message d'amour d'un imposteur trop excentré"),
    Note(3, "Todo", "bah oui la todo se fait ici en attendant d'avoir un truc plus clean"),
    Note(4, "test", "Ceci est une super note sur la liberté d'expression"),
    Note(5, "mot de passe", "Ne jamais faire de notes commes ça !!!"),
    Note(6, "Coucou", "petit message d'amour d'un imposteur trop excentré"),
    Note(7, "Todo", "bah oui la todo se fait ici en attendant d'avoir un truc plus clean"),
    Note(8, "test", "Ceci est une super note sur la liberté d'expression"),
    Note(9, "mot de passe", "Ne jamais faire de notes commes ça !!!"),
    Note(10, "Coucou", "petit message d'amour d'un imposteur trop excentré"),
    Note(11, "Todo", "bah oui la todo se fait ici en attendant d'avoir un truc plus clean"),
    Note(12, "test", "Ceci est une super note sur la liberté d'expression"),
    Note(13, "mot de passe", "Ne jamais faire de notes commes ça !!!"),
    Note(14, "Coucou", "petit message d'amour d'un imposteur trop excentré"),
    Note(15, "Todo", "bah oui la todo se fait ici en attendant d'avoir un truc plus clean"),
    Note(16, "test", "Ceci est une super note sur la liberté d'expression"),
    Note(17, "mot de passe", "Ne jamais faire de notes commes ça !!!"),
    Note(18, "Coucou", "petit message d'amour d'un imposteur trop excentré"),
    Note(19, "Todo", "bah oui la todo se fait ici en attendant d'avoir un truc plus clean")
)