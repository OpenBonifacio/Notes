package fr.openbonifacio.notes.notes.presentation.note_details

import fr.openbonifacio.notes.core.presentation.UiText
import fr.openbonifacio.notes.notes.domain.Note

data class NoteDetailsState(
    val note: Note? = null,
    val isLoading: Boolean = true,
    val errorMessage: UiText? = null,
)