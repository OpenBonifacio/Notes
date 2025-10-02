package fr.openbonifacio.notes.notes.presentation.notes_list

import fr.openbonifacio.notes.core.presentation.UiText
import fr.openbonifacio.notes.notes.domain.Note

data class NotesListState(
    val notes: List<Note> = emptyList<Note>(),
    val isLoading: Boolean = true,
    //val selectedTabIndex: Int = 0,
    val errorMessage: UiText? = null
)