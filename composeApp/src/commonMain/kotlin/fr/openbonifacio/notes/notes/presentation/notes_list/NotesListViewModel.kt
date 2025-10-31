package fr.openbonifacio.notes.notes.presentation.notes_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import fr.openbonifacio.notes.core.presentation.UiText
import fr.openbonifacio.notes.notes.domain.NoteRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

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
            is NotesListAction.OnNoteClick -> {
                val selected = _state.value.selectedIds
                if (selected.isNotEmpty()) {
                    toggleSelection(action.note.id)
                }
            }
            is NotesListAction.OnNoteLongClick -> {
                toggleSelection(action.note.id)
            }
            is NotesListAction.ToggleSelection -> {
                toggleSelection(action.noteId)
            }
            NotesListAction.ClearSelection -> {
                _state.update { it.copy(selectedIds = emptySet()) }
            }
            NotesListAction.DeleteSelected -> {
                val idsToDelete = _state.value.selectedIds
                if (idsToDelete.isEmpty()) return
                deleteNotes(idsToDelete)
            }
            NotesListAction.ClearError -> {
                _state.update { it.copy(errorMessage = null) }
            }
        }
    }

    private fun toggleSelection(noteId: String) {
        _state.update { state ->
            val current = state.selectedIds
            if (current.contains(noteId)) {
                state.copy(selectedIds = current - noteId)
            } else {
                state.copy(selectedIds = current + noteId)
            }
        }
    }

    private fun deleteNotes(ids: Set<String>){
        viewModelScope.launch {
            val notes = _state.value.notes.filter { it.id in ids }
            notes.forEach { note ->
                try {
                    noteRepository.deleteNote(note)
                } catch (_: Exception) {
                    _state.update {
                        it.copy(
                            errorMessage = UiText.DynamicString("Erreur lors de la suppression des notes")
                        )
                    }
                }
            }
            _state.update { it.copy(selectedIds = emptySet()) }
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