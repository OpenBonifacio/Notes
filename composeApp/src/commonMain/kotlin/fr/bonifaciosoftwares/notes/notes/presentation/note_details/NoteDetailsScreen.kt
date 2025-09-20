package fr.bonifaciosoftwares.notes.notes.presentation.note_details


import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.foundation.text.input.setTextAndPlaceCursorAtEnd
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import fr.bonifaciosoftwares.notes.notes.presentation.note_details.components.NoteDetailsTopAppBar

@OptIn(ExperimentalSharedTransitionApi::class, ExperimentalMaterial3Api::class)
@Composable
fun NoteDetailsScreenRoot(
    viewModel: NoteDetailsViewModel,
    onBackClick: () -> Unit,
    sharedTransitionScope: SharedTransitionScope,
    animatedContentScope: AnimatedContentScope,
){
    val state by viewModel.state.collectAsStateWithLifecycle()

    val topAppBarState = rememberTopAppBarState()
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(topAppBarState) // Ou le behavior désiré pour les détails


    NoteDetailsScreen(
        state = state,
        onAction = { action ->
            when(action){
                is NoteDetailsAction.OnBackClick -> onBackClick()
                else -> Unit
            }
            viewModel.onAction(action)
        },
        sharedTransitionScope,
        animatedContentScope,
        scrollBehavior
    )
}

@OptIn(ExperimentalSharedTransitionApi::class, ExperimentalMaterial3Api::class)
@Composable
fun NoteDetailsScreen(
    state: NoteDetailsState,
    onAction: (NoteDetailsAction) -> Unit,
    sharedTransitionScope: SharedTransitionScope,
    animatedContentScope: AnimatedContentScope,
    scrollBehavior: TopAppBarScrollBehavior
) {

    val titleTextState: TextFieldState = rememberTextFieldState(
        state.note?.title ?: ""
    )

    val contentTextState = rememberTextFieldState(
        state.note?.content ?: ""
    )

    LaunchedEffect(state.note) {
        state.note?.let { note ->
            titleTextState.setTextAndPlaceCursorAtEnd(note.title)
            contentTextState.setTextAndPlaceCursorAtEnd(note.content)

        }
    }

    val columnScrollableState = rememberScrollState()

    //Réinitialiser la page en haut
    LaunchedEffect(Unit) {
        columnScrollableState.scrollTo(0)
    }

    with(sharedTransitionScope){
        Scaffold(
            modifier = Modifier
                .fillMaxSize()
                .nestedScroll(scrollBehavior.nestedScrollConnection)
                .sharedBounds(
                    sharedContentState = rememberSharedContentState(
                        key = if (state.note?.id == 0L) "new" else ""
                    ),
                    animatedVisibilityScope = animatedContentScope
                ),
            topBar = {
                NoteDetailsTopAppBar(
                    modifier = Modifier,
                    titleTextState = titleTextState,
                    scrollBehavior = scrollBehavior,
                    onSaveClick = {
                        onAction(
                            NoteDetailsAction.OnSaveClick(
                                title = titleTextState.text.toString(),
                                content = contentTextState.text.toString()
                            )
                        )
                        onAction(NoteDetailsAction.OnBackClick)
                    },
                    onDeleteClick = {
                        onAction(NoteDetailsAction.OnDeleteClick)
                        onAction(NoteDetailsAction.OnBackClick)
                    },
                    onBackClick = {
                        onAction(NoteDetailsAction.OnBackClick)
                    },
                    onFavoriteClick = {
                        onAction(NoteDetailsAction.OnFavoriteClick)
                    }
                )
            }
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .verticalScroll(columnScrollableState)
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                BasicTextField(
                    state = contentTextState,
                    modifier = Modifier
                        .fillMaxWidth()
                        .sharedElement(
                            sharedContentState = sharedTransitionScope.rememberSharedContentState(key = "content-${state.note?.id ?: "new"}"),
                            animatedVisibilityScope = animatedContentScope
                        ),
                    textStyle = MaterialTheme.typography.bodyLarge.copy(color = MaterialTheme.colorScheme.onSurface),
                    decorator = @Composable { innerTextField ->
                        Box(
                            modifier = Modifier.fillMaxWidth(),
                            contentAlignment = Alignment.CenterStart
                        ) {
                            if (contentTextState.text.isEmpty()) {
                                Text(
                                    text = "Contenue de la note...",
                                    style = MaterialTheme.typography.bodyLarge,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f)
                                )
                            }
                            innerTextField()
                        }
                    }
                )

                Spacer(Modifier.height(250.dp))
            }
        }
    }
}