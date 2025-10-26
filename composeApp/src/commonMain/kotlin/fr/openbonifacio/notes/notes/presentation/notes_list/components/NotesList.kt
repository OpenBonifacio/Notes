package fr.openbonifacio.notes.notes.presentation.notes_list.components

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import fr.openbonifacio.notes.notes.domain.Note

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun NotesList(
    modifier: Modifier = Modifier,
    scrollState: LazyListState = rememberLazyListState(),
    notes: List<Note>,
    selectedIds: Set<Long> = emptySet(),
    onNoteClick: (Note) -> Unit,
    onNoteLongClick: (Note) -> Unit,
){
    LazyColumn(
        state = scrollState,
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        contentPadding = PaddingValues(bottom = 120.dp, top = 18.dp)
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
                isSelected = selectedIds.contains(note.id),
                onNoteClick = { onNoteClick(note) },
                onNoteLongClick = { onNoteLongClick(note) }
            )
        }


    }
}