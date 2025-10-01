package fr.bonifaciosoftwares.notes.notes.presentation.note_details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import fr.bonifaciosoftwares.notes.core.domain.onError
import fr.bonifaciosoftwares.notes.core.domain.onSuccess
import fr.bonifaciosoftwares.notes.notes.domain.Note
import fr.bonifaciosoftwares.notes.notes.domain.NoteRepository
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class NoteDetailsViewModel(
    private val noteId: Long = 0,
    private val noteRepository: NoteRepository
) : ViewModel() {

    private val _state = MutableStateFlow(NoteDetailsState())
    private val editState = MutableStateFlow(Pair("", ""))
    val state = _state
        .onStart {
            fetchNoteDetails()
            //observeEditState()
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(500L),
            _state.value
        )

    fun onAction(action: NoteDetailsAction){
        when(action){
            is NoteDetailsAction.OnBackClick -> Unit
            is NoteDetailsAction.OnSave -> {
               saveNote()
            }
            is NoteDetailsAction.OnDeleteClick -> {
                viewModelScope.launch {
                    state.value.note?.let {
                        noteRepository.deleteNote(it)
                    }
                }
            }

            is NoteDetailsAction.OnFavoriteClick -> {
                markAsFavorite()
            }

            is NoteDetailsAction.OnTextChange -> {
                _state.update { current ->
                    current.copy(
                        note = current.note?.copy(
                            title = action.title,
                            content = action.content
                        )
                    )
                }

                println("noteeeee ${state.value.note?.content}")
            }
        }
    }

    private fun markAsFavorite(){
        viewModelScope.launch {
            state.value.note?.let {
                //noteRepository.updateNote(it.copy(isFavorite = !it.isFavorite))
            }
        }
    }

    @OptIn(FlowPreview::class)
    private fun observeEditState(){
        editState
            .debounce(1000L)
            .onEach {
                saveNote()
            }
            .distinctUntilChanged { old, new ->
                old.first == new.first && old.second == new.second
            }
            .launchIn(viewModelScope)
    }

    private fun saveNote(){
        /*if (
            (state.value.note?.title.equals(editState.value.first)
            && state.value.note?.content.equals(editState.value.second))
            ) return*/


        /*if (
            editState.value.first.isEmpty() && editState.value.second.isEmpty()
        ){
            onAction(NoteDetailsAction.OnDeleteClick)
        }*/

        /*_state.update { current ->
            current.copy(
                note = current.note?.copy(
                    title = editState.value.first,
                    content = editState.value.second
                )
            )
        }*/

        viewModelScope.launch {
            state.value.note?.let { it ->
                if (it.id == 0L){
                    /*if (editState.value.first.isNotEmpty() || editState.value.second.isNotEmpty())*/
                        noteRepository.upsertNote(it).onSuccess { newId ->
                            _state.update { current ->
                                current.copy(
                                    note = current.note?.copy(
                                        id = newId
                                    )
                                )
                            }
                            println("je suis iciiiiiii avec l'id $newId")
                        }.onError {
                            println("biggggg erreur")
                        }
                }else{
                    noteRepository.updateNote(it)
                }
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