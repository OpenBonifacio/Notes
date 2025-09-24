package fr.bonifaciosoftwares.notes.notes.presentation.note_details.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.outlined.More
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.More
import androidx.compose.material3.Divider
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import fr.bonifaciosoftwares.notes.notes.presentation.note_details.NoteDetailsState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteDetailsTopAppBar(
    modifier: Modifier = Modifier,
    titleTextState: TextFieldState,
    scrollBehavior: TopAppBarScrollBehavior,
    onDeleteClick: () -> Unit,
    onBackClick: () -> Unit,
    onFavoriteClick: () -> Unit,
    state: NoteDetailsState
){
    MediumTopAppBar(
        modifier = modifier,
        title = {
            BasicTextField(
                state = titleTextState,
                modifier = Modifier
                    .fillMaxWidth()
                    .nestedScroll(scrollBehavior.nestedScrollConnection),
                textStyle = MaterialTheme.typography.titleLarge.copy(color = MaterialTheme.colorScheme.onSurface),
                lineLimits = TextFieldLineLimits.MultiLine(
                    minHeightInLines = 1,
                    maxHeightInLines = 1
                ),
                decorator = @Composable { innerTextField ->
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.CenterStart
                    ) {
                        // PLACEHOLDER
                        if (titleTextState.text.isEmpty()) {
                            Text(
                                text = "Titre de la note...",
                                style = MaterialTheme.typography.titleLarge,
                                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f)
                            )
                        }
                        innerTextField()
                    }
                }
            )

        },
        navigationIcon = {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Back",
                modifier = Modifier
                    .clickable(onClick = { onBackClick() })
                    .minimumInteractiveComponentSize()
            )
        },
        actions = {

            var expanded by remember { mutableStateOf(false) }

            IconButton(
                modifier = Modifier
                    .minimumInteractiveComponentSize(),
                onClick = { expanded = true }
            ){
                Icon(
                    Icons.Default.MoreVert,
                    contentDescription = "More"
                )
            }

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier
            ) {
                DropdownMenuItem(
                    text = { Text("Supprimer") },
                    onClick = {
                        expanded = false
                        onDeleteClick()
                    },
                    leadingIcon = { Icon(Icons.Outlined.Delete, "Delete") }
                )
            }

            /*Icon(
                imageVector = if (state.note?.isFavorite == true) Icons.Outlined.Favorite else Icons.Outlined.FavoriteBorder,
                contentDescription = "Favorite",
                modifier = Modifier
                    .minimumInteractiveComponentSize()
                    .clickable(onClick = { onFavoriteClick() })
            )*/
        },
        scrollBehavior = scrollBehavior
    )
}