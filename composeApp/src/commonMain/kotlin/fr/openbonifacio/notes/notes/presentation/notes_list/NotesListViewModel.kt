package fr.openbonifacio.notes.notes.presentation.notes_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import fr.openbonifacio.notes.notes.domain.NoteRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

class NotesListViewModel(
    val noteRepository: NoteRepository
) : ViewModel() {

    private var observeNotesJob: Job? = null

    private val _state = MutableStateFlow(NotesListState())
    val state = _state
        .onStart {
            observeNotes()
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

    private fun observeNotes(){
        observeNotesJob?.cancel()
        observeNotesJob = noteRepository
            .getNotes()
            .onEach{ notes ->
                _state.update {
                    it.copy(
                        notes = notes
                    )
                }
            }.launchIn(viewModelScope)
    }
}