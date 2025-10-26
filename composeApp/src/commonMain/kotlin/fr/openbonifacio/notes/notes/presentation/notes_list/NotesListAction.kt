package fr.openbonifacio.notes.notes.presentation.notes_list

import fr.openbonifacio.notes.notes.domain.Note

sealed interface NotesListAction {
    data class OnNoteClick(val note: Note) : NotesListAction
}

