package fr.openbonifacio.notes.notes.presentation.notes_list

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import fr.openbonifacio.notes.core.presentation.components.ConfirmationAlertDialog
import fr.openbonifacio.notes.notes.domain.Note
import fr.openbonifacio.notes.notes.presentation.notes_list.components.NotesList


@OptIn(ExperimentalMaterial3Api::class, ExperimentalSharedTransitionApi::class)
@Composable
fun NotesListScreenRoot(
    viewModel: NotesListViewModel, //= koinViewModel(),
    onNoteClick: (Note) -> Unit,
    onFabClick: () -> Unit,
    parrentPadding: PaddingValues
){

    val state by viewModel.state.collectAsStateWithLifecycle()

    val topAppBarState = rememberTopAppBarState()
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(topAppBarState)

    NotesListScreen(
        state = state,
        onAction = { action ->
            when(action){
                is NotesListAction.OnNoteClick -> {
                    if (state.selectedIds.isEmpty()) {
                        onNoteClick(action.note)
                    }
                }
                else -> Unit
            }
            viewModel.onAction(action)
        },
        scrollBehavior = scrollBehavior,
        onFabClick = onFabClick,
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
    parrentPadding: PaddingValues
){

    val notesListState = rememberLazyListState()
    var showDeleteDialog by remember { mutableStateOf(false) }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            if (state.selectedIds.isNotEmpty()) {
                CenterAlignedTopAppBar(
                    title = {
                        Text(
                            text = "${state.selectedIds.size} sélectionnée(s)",
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = { onAction(NotesListAction.ClearSelection) }) {
                            Icon(Icons.Default.Close, contentDescription = "Annuler la sélection")
                        }
                    },
                    actions = {
                        IconButton(onClick = { showDeleteDialog = true }) {
                            Icon(Icons.Default.Delete, contentDescription = "Supprimer les notes sélectionnées")
                        }
                    },
                    scrollBehavior = scrollBehavior,
                )
            } else {
                CenterAlignedTopAppBar(
                    title = {
                        Text(
                            text = "Notes",
                            style = MaterialTheme.typography.headlineLarge
                        )
                    },
                    scrollBehavior = scrollBehavior,
                )
            }
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                text = {Text("Nouvelle")},
                modifier = Modifier
                    .padding(bottom = parrentPadding.calculateBottomPadding()),
                onClick = {
                    onFabClick()
                },
                icon = {Icon(Icons.Default.Edit, null)}
            )
        }
    ){ innerPadding ->

        if (showDeleteDialog){
            ConfirmationAlertDialog(
                dialogTitle = "Supression",
                dialogText = "Voulez-vous supprimer ${state.selectedIds.size} note.s ?",
                onDismiss = {
                    showDeleteDialog = false
                },
                onConfirm = {
                    onAction(NotesListAction.DeleteSelected)
                    showDeleteDialog = false
                },
                icon = Icons.Outlined.Delete
            )
        }

        if (state.errorMessage != null) {
            AlertDialog(
                onDismissRequest = { onAction(NotesListAction.ClearError) },
                title = {
                    Text(text = "Erreur")
                },
                text = {
                    Text(text = state.errorMessage.asString())
                },
                confirmButton = {
                    TextButton(onClick = { onAction(NotesListAction.ClearError) }) {
                        Text("OK")
                    }
                }
            )
        }

        NotesList(
            modifier = Modifier
                .padding(innerPadding),
            scrollState = notesListState,
            onNoteClick = { note ->
                onAction(NotesListAction.OnNoteClick(note))
            },
            onNoteLongClick = { note ->
                onAction(NotesListAction.OnNoteLongClick(note))
            },
            notes = state.notes,
            selectedIds = state.selectedIds
        )
    }
}