package fr.bonifaciosoftwares.notes.notes.presentation.notes_list.components

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContent
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import fr.bonifaciosoftwares.notes.notes.domain.Note

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun NotesList(
    modifier: Modifier = Modifier,
    scrollState: LazyListState = rememberLazyListState(),
    notes: List<Note>,
    onNoteClick: (Note) -> Unit,
    sharedTransitionScope: SharedTransitionScope,
    animatedContentScope: AnimatedContentScope,
){
    LazyColumn(
        state = scrollState,
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(18.dp),
        contentPadding = PaddingValues(bottom = 120.dp)
    ){
        items(
            items = notes,
            key = {it.id}
        ){ note ->
            NotesListItem(
                note = note,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 18.dp),
                onNoteClick = { onNoteClick(note) },
                sharedTransitionScope,
                animatedContentScope
            )
        }


    }
}