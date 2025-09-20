package fr.bonifaciosoftwares.notes.notes.presentation.note_details

sealed interface NoteDetailsAction {
    data object OnBackClick : NoteDetailsAction

    data class OnSaveClick(val title:String="", val content:String ="") : NoteDetailsAction

    data object OnDeleteClick: NoteDetailsAction

    data object OnFavoriteClick: NoteDetailsAction
}