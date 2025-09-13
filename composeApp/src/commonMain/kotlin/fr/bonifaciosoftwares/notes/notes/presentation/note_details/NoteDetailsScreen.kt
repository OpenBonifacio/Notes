package fr.bonifaciosoftwares.notes.notes.presentation.note_details


import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun NoteDetailsScreenRoot(
    viewModel: NoteDetailsViewModel,
    onBackClick: () -> Unit,
    sharedTransitionScope: SharedTransitionScope,
    animatedContentScope: AnimatedContentScope,
){
    val state by viewModel.state.collectAsStateWithLifecycle()

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
        animatedContentScope
    )
}

@OptIn(ExperimentalSharedTransitionApi::class, ExperimentalMaterial3Api::class)
@Composable
fun NoteDetailsScreen(
    state: NoteDetailsState,
    onAction: (NoteDetailsAction) -> Unit,
    sharedTransitionScope: SharedTransitionScope,
    animatedContentScope: AnimatedContentScope
) {
    with(sharedTransitionScope){
        Column(

        ){
            TopAppBar(
                title = {},
                navigationIcon = {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = null,
                        modifier = Modifier.clickable(onClick = { onAction(NoteDetailsAction.OnBackClick) })
                    )
                }
            )
            Text(state.note?.title ?: "", modifier = Modifier.sharedElement(
                sharedTransitionScope.rememberSharedContentState(key = "title-${state.note?.id}"),
                animatedVisibilityScope = animatedContentScope
            ))
            Text(state.note?.content ?: "",
                modifier = Modifier.sharedElement(
                    sharedTransitionScope.rememberSharedContentState(key = "content-${state.note?.id}"),
                    animatedVisibilityScope = animatedContentScope
                ))
        }
    }
}