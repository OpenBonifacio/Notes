package fr.bonifaciosoftwares.notes.notes.presentation.note_details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import fr.bonifaciosoftwares.notes.notes.domain.Note
import fr.bonifaciosoftwares.notes.notes.presentation.notes_list.allNotes
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn

class NoteDetailsViewModel(
    noteId: Long? = null
) : ViewModel() {

    private val _state = MutableStateFlow(NoteDetailsState(
        if (noteId == -1L || noteId == null){
            Note(-1, "", "")
        }else{
            allNotes.find { it.id == noteId }
        }
    ))
    val state = _state
        .onStart {
            
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
}