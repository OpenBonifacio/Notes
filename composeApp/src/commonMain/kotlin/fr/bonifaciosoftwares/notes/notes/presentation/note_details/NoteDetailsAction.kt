package fr.bonifaciosoftwares.notes.notes.presentation.note_details

sealed interface NoteDetailsAction {
    data object OnBackClick : NoteDetailsAction

    data object OnSaveClick : NoteDetailsAction
}