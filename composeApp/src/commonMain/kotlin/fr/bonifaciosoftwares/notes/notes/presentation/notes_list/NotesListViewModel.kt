package fr.bonifaciosoftwares.notes.notes.presentation.notes_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn

class NotesListViewModel : ViewModel() {

    private val _state = MutableStateFlow(NotesListState())
    val state = _state
        .onStart {

        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(500L),
            _state.value
        )

    fun onAction(action: NotesListAction){
        when(action){
             is NotesListAction.OnNoteClick -> Unit
        }
    }
}