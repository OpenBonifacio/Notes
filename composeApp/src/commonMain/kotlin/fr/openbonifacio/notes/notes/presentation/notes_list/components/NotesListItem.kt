package fr.openbonifacio.notes.notes.presentation.notes_list.components

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.foundation.border
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import fr.openbonifacio.notes.notes.domain.Note

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun NotesListItem(
    note: Note,
    modifier: Modifier = Modifier,
    isSelected: Boolean = false,
    onNoteClick: () -> Unit,
    onNoteLongClick: () -> Unit,
){
    val shape = RoundedCornerShape(20.dp)

    Card(
        modifier = modifier
            .clip(shape)
            .then(
                if (isSelected) Modifier.border(2.dp, MaterialTheme.colorScheme.primary, shape) else Modifier
            )
            .combinedClickable(
                onClick = onNoteClick,
                onLongClick = onNoteLongClick
            ),
        shape = shape,
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) MaterialTheme.colorScheme.primary.copy(alpha = 0.12f) else MaterialTheme.colorScheme.surfaceContainerLow
        )
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