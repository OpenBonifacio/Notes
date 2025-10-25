package fr.openbonifacio.notes.notes.presentation.notes_list.components

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import fr.openbonifacio.notes.notes.domain.Note

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun NotesListItem(
    note: Note,
    modifier: Modifier = Modifier,
    onNoteClick: () -> Unit,
){
    Card(
        modifier = modifier
            /*.shadow(
                elevation = 3.dp,
                shape = RoundedCornerShape(20.dp),
                spotColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4f),
                ambientColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4f),
            )*/
            .clickable(onClick = onNoteClick),
        shape = RoundedCornerShape(20.dp),
    ) {
        Column(
            modifier = Modifier.padding(18.dp)
        ) {
            Text(
                text = note.title,
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = note.content,
                maxLines = 2,
                style = MaterialTheme.typography.bodyMedium,
                overflow = TextOverflow.Ellipsis,
            )
        }
    }
}