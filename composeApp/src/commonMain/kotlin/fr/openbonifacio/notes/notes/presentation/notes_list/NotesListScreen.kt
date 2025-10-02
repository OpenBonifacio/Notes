package fr.openbonifacio.notes.notes.presentation.notes_list

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import fr.openbonifacio.notes.notes.domain.Note
import fr.openbonifacio.notes.notes.presentation.notes_list.components.NotesList


@OptIn(ExperimentalMaterial3Api::class, ExperimentalSharedTransitionApi::class)
@Composable
fun NotesListScreenRoot(
    viewModel: NotesListViewModel, //= koinViewModel(),
    onNoteClick: (Note) -> Unit,
    onFabClick: () -> Unit,
    sharedTransitionScope: SharedTransitionScope,
    animatedContentScope: AnimatedContentScope,
    parrentPadding: PaddingValues
){

    val state by viewModel.state.collectAsStateWithLifecycle()

    val topAppBarState = rememberTopAppBarState()
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(topAppBarState)

    NotesListScreen(
        state = state,
        onAction = { action ->
            when(action){
                is NotesListAction.OnNoteClick -> onNoteClick(action.note)
            }
            viewModel.onAction(action)
        },
        scrollBehavior = scrollBehavior,
        onFabClick = onFabClick,
        sharedTransitionScope = sharedTransitionScope,
        animatedContentScope = animatedContentScope,
        parrentPadding = parrentPadding
    )

}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalSharedTransitionApi::class)
@Composable
fun NotesListScreen(
    state: NotesListState,
    onAction: (NotesListAction) -> Unit,
    onFabClick: () -> Unit,
    scrollBehavior: TopAppBarScrollBehavior,
    sharedTransitionScope: SharedTransitionScope,
    animatedContentScope: AnimatedContentScope,
    parrentPadding: PaddingValues
){

    val notesListState = rememberLazyListState()


   with(sharedTransitionScope){
       Scaffold(
           modifier = Modifier
               .fillMaxSize()
               .nestedScroll(scrollBehavior.nestedScrollConnection),
           topBar = {
               CenterAlignedTopAppBar(
                   title = {
                       Text("Notes")
                   },
                   scrollBehavior = scrollBehavior,
               )
           },
           floatingActionButton = {
               ExtendedFloatingActionButton(
                   text = {Text("Nouvelle")},
                   modifier = Modifier
                       .sharedBounds(
                           sharedContentState = rememberSharedContentState(
                               "new"
                           ),
                           animatedVisibilityScope = animatedContentScope
                       )
                       .padding(bottom = parrentPadding.calculateBottomPadding()),
                   onClick = {
                       onFabClick()
                   },
                   icon = {Icon(Icons.Default.Edit, null)}
               )
           }
       ){ innerPadding ->
           NotesList(
               modifier = Modifier
                   .padding(innerPadding),
               scrollState = notesListState,
               onNoteClick = { note ->
                   onAction(NotesListAction.OnNoteClick(note))
               },
               notes = state.notes,
               sharedTransitionScope = sharedTransitionScope,
               animatedContentScope = animatedContentScope,
           )
       }
   }
}