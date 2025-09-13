package fr.bonifaciosoftwares.notes.notes.presentation.notes_list

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import fr.bonifaciosoftwares.notes.notes.domain.Note
import fr.bonifaciosoftwares.notes.notes.presentation.notes_list.components.NotesList


@OptIn(ExperimentalMaterial3Api::class, ExperimentalSharedTransitionApi::class)
@Composable
fun NotesListScreenRoot(
    viewModel: NotesListViewModel,
    onNoteClick: (Note) -> Unit,
    scrollBehavior: TopAppBarScrollBehavior,
    sharedTransitionScope: SharedTransitionScope,
    animatedContentScope: AnimatedContentScope
){

    val state by viewModel.state.collectAsStateWithLifecycle()

    NotesListScreen(
        state = state,
        onAction = { action ->
            when(action){
                is NotesListAction.OnNoteClick -> onNoteClick(action.note)
                else -> Unit
            }
            viewModel.onAction(action)
        },
        scrollBehavior = scrollBehavior,
        sharedTransitionScope,
        animatedContentScope
    )

}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalSharedTransitionApi::class)
@Composable
fun NotesListScreen(
    state: NotesListState,
    onAction: (NotesListAction) -> Unit,
    scrollBehavior: TopAppBarScrollBehavior,
    sharedTransitionScope: SharedTransitionScope,
    animatedContentScope: AnimatedContentScope
){

    val notesListState = rememberLazyListState()

    Column(
        modifier = Modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        MediumTopAppBar(
            title = {Text("Notes")},
            scrollBehavior = scrollBehavior,
            colors = TopAppBarDefaults.topAppBarColors(
                //containerColor = SandYellowLight
            )
        )

        NotesList(
            modifier = Modifier.fillMaxSize(),
            scrollState = notesListState,
            onNoteClick = { note ->
                onAction(NotesListAction.OnNoteClick(note))
            },
            notes = state.notes,
            sharedTransitionScope = sharedTransitionScope,
            animatedContentScope = animatedContentScope
        )
    }
}