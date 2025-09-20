package fr.bonifaciosoftwares.notes.notes.presentation.note_details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import fr.bonifaciosoftwares.notes.notes.domain.Note
import fr.bonifaciosoftwares.notes.notes.domain.NoteRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class NoteDetailsViewModel(
    private val noteId: Long = 0,
    private val noteRepository: NoteRepository
) : ViewModel() {

    private val _state = MutableStateFlow(NoteDetailsState())
    val state = _state
        .onStart {
            fetchNoteDetails()
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(500L),
            _state.value
        )

    fun onAction(action: NoteDetailsAction){
        when(action){
            is NoteDetailsAction.OnBackClick -> Unit
            is NoteDetailsAction.OnSaveClick -> {
                _state.update { current ->
                    current.copy(
                        note = current.note?.copy(
                            title = action.title,
                            content = action.content
                        )
                    )
                }
               viewModelScope.launch {
                   state.value.note?.let {
                       if (it.id == 0L){
                             noteRepository.upsertNote(it)
                       }else{
                            noteRepository.updateNote(it)
                       }
                   }
               }
            }
            is NoteDetailsAction.OnDeleteClick -> {
                viewModelScope.launch {
                    state.value.note?.let {
                        noteRepository.deleteNote(it)
                    }
                }
            }
            is NoteDetailsAction.OnFavoriteClick -> {

            }
        }
    }

    private fun fetchNoteDetails(){
        viewModelScope.launch {
            if (noteId == 0L){
                _state.update {
                    it.copy(
                        note = Note(id = noteId),
                        isLoading = false
                    )
                }

            }else{
                noteRepository
                    .getNote(noteId).let { note ->
                        _state.update {
                            it.copy(
                                isLoading = false,
                                note = note
                            )
                        }
                    }
            }
        }
    }
}