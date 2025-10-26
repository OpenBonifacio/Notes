package fr.openbonifacio.notes.notes.presentation.notes_list

import fr.openbonifacio.notes.core.presentation.UiText
import fr.openbonifacio.notes.notes.domain.Note

data class NotesListState(
    val notes: List<Note> = emptyList(),
    val isLoading: Boolean = true,
    val errorMessage: UiText? = null,
    val selectedIds: Set<Long> = emptySet()
)