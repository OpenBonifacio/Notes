package fr.openbonifacio.notes.notes.presentation.note_details.components

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.ScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import kotlin.math.exp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteDetailsTopAppBar(
    modifier: Modifier = Modifier,
    scrollBehavior: TopAppBarScrollBehavior,
    onDeleteClick: () -> Unit,
    onBackClick: () -> Unit,
    onFavoriteClick: () -> Unit,
    onInfoClick: () -> Unit,
    scrollState: ScrollState
){

    val scrolled = scrollState.value > 0
    val targetElevation = if (scrolled) 6.dp else 0.dp
    val elevation by animateDpAsState(targetElevation)

    TopAppBar(
        modifier = modifier
            .shadow(elevation),
        title = {},
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
        colors = TopAppBarDefaults.topAppBarColors().copy(
            containerColor = MaterialTheme.colorScheme.surfaceContainerHigh,
        ),
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
                    text = {Text("Infos")},
                    onClick = {
                        expanded = false
                        onInfoClick()
                    },
                    leadingIcon = {Icon(Icons.Outlined.Info, "Infos")}
                )
                DropdownMenuItem(
                    text = { Text("Supprimer") },
                    onClick = {
                        expanded = false
                        onDeleteClick()
                    },
                    leadingIcon = { Icon(Icons.Outlined.Delete, "Delete") }
                )
            }
        },
        scrollBehavior = scrollBehavior
    )
}