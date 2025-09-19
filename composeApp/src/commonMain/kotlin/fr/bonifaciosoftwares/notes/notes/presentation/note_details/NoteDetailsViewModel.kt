package fr.bonifaciosoftwares.notes.notes.presentation.note_details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import fr.bonifaciosoftwares.notes.notes.data.database.allNotes
import fr.bonifaciosoftwares.notes.notes.data.database.toNote
import fr.bonifaciosoftwares.notes.notes.domain.Note
import fr.bonifaciosoftwares.notes.notes.domain.NoteRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class NoteDetailsViewModel(
    private val noteId: Long? = -1,
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
        }
    }

    private fun fetchNoteDetails(){
        viewModelScope.launch {
            /*noteRepository
                .getNote(noteId)
                .onSuccess { description ->
                    _state.update {
                        it.copy(
                            book = it.book?.copy(
                                description = description
                            ),
                            isLoading = false
                        )
                    }
                }*/

            allNotes.find { it.id == noteId }?.let { note ->
                _state.update {
                    it.copy(
                        note = note.toNote(),
                        isLoading = false
                    )
                }
            }
        }
    }
}