package fr.openbonifacio.notes.notes.presentation.note_details.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteDetailsTopAppBar(
    modifier: Modifier = Modifier,
    titleText: String,
    scrollBehavior: TopAppBarScrollBehavior,
    onDeleteClick: () -> Unit,
    onBackClick: () -> Unit,
    onFavoriteClick: () -> Unit,
    onTitleChange: (String) -> Unit,
){
    MediumTopAppBar(
        modifier = modifier,
        title = {
            BasicTextField(
                value = titleText,
                onValueChange = {
                    onTitleChange(it)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .nestedScroll(scrollBehavior.nestedScrollConnection),
                textStyle = MaterialTheme.typography.titleLarge.copy(color = MaterialTheme.colorScheme.onSurface),
                singleLine = true,
                decorationBox = @Composable { innerTextField ->
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.CenterStart
                    ) {
                        // PLACEHOLDER
                        if (titleText.isEmpty()) {
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
            IconButton(
                modifier = modifier.minimumInteractiveComponentSize(),
                onClick = {onBackClick()}
            ){
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                )
            }
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