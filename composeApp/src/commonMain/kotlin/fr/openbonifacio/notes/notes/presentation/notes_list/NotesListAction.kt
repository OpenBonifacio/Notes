package fr.openbonifacio.notes.notes.presentation.notes_list

import fr.openbonifacio.notes.notes.domain.Note

sealed interface NotesListAction {
    data class OnNoteClick(val note: Note) : NotesListAction
    data class OnNoteLongClick(val note: Note) : NotesListAction
    data class ToggleSelection(val noteId: String) : NotesListAction
    object ClearSelection : NotesListAction
    object DeleteSelected : NotesListAction
    object ClearError : NotesListAction
}
