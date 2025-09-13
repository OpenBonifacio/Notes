package fr.bonifaciosoftwares.notes.notes.presentation.notes_list

import fr.bonifaciosoftwares.notes.notes.domain.Note

sealed interface NotesListAction {
    data class OnNoteClick(val note: Note) : NotesListAction
}