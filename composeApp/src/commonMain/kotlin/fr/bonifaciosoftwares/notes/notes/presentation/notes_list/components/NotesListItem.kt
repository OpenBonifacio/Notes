package fr.bonifaciosoftwares.notes.notes.presentation.notes_list.components

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import fr.bonifaciosoftwares.notes.notes.domain.Note

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun NotesListItem(
    note: Note,
    modifier: Modifier = Modifier,
    onNoteClick: () -> Unit,
    sharedTransitionScope: SharedTransitionScope,
    animatedContentScope: AnimatedContentScope
){
    with(sharedTransitionScope) {

        ElevatedCard(
            modifier = modifier
                .shadow(
                    elevation = 5.dp,
                    shape = RoundedCornerShape(20.dp),
                    spotColor = Color.DarkGray.copy(alpha = 0.4f),
                    ambientColor = Color.DarkGray.copy(alpha = 0.4f),
                )
                .clickable(onClick = onNoteClick),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            ),
            shape = RoundedCornerShape(20.dp),
        ) {
            Column(
                modifier = Modifier.padding(
                    25.dp
                )
            ) {
                Text(
                    text = note.title,
                    modifier = Modifier.sharedElement(
                        sharedTransitionScope.rememberSharedContentState(key = "title-${note.id}"),
                        animatedVisibilityScope = animatedContentScope
                    ),
                    //fontWeight = FontWeight.SemiBold,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = note.content,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.sharedElement(
                        sharedTransitionScope.rememberSharedContentState(key = "content-${note.id}"),
                        animatedVisibilityScope = animatedContentScope
                    )
                )
            }
        }
    }
}