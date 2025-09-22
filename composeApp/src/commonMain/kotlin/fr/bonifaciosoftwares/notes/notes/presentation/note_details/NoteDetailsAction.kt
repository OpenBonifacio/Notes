package fr.bonifaciosoftwares.notes.notes.presentation.note_details

sealed interface NoteDetailsAction {
    data object OnBackClick : NoteDetailsAction

    data object OnSave : NoteDetailsAction

    data object OnDeleteClick: NoteDetailsAction

    data object OnFavoriteClick: NoteDetailsAction

    data class OnTextChange(val title: String = "", val content: String = ""): NoteDetailsAction
}