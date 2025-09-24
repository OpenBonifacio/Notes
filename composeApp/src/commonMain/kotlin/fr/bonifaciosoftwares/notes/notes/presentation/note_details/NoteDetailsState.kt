package fr.bonifaciosoftwares.notes.notes.presentation.note_details

import fr.bonifaciosoftwares.notes.core.presentation.UiText
import fr.bonifaciosoftwares.notes.notes.domain.Note

data class NoteDetailsState(
    val note: Note? = null,
    val isLoading: Boolean = true,
    val errorMessage: UiText? = null,
)