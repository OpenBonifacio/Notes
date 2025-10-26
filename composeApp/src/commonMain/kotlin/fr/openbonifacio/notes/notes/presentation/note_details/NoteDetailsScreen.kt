package fr.openbonifacio.notes.notes.presentation.note_details


import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import fr.openbonifacio.notes.core.presentation.components.ConfirmationAlertDialog
import fr.openbonifacio.notes.core.util.TimeProvider
import fr.openbonifacio.notes.notes.presentation.note_details.components.NoteDetailsTopAppBar

@OptIn(ExperimentalSharedTransitionApi::class, ExperimentalMaterial3Api::class)
@Composable
fun NoteDetailsScreenRoot(
    viewModel: NoteDetailsViewModel,
    onBackClick: () -> Unit,
){
    val state by viewModel.state.collectAsStateWithLifecycle()

    val topAppBarState = rememberTopAppBarState()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(topAppBarState)

    NoteDetailsScreen(
        state = state,
        onAction = { action ->
            when(action){
                is NoteDetailsAction.OnBackClick -> onBackClick()
                else -> Unit
            }
            viewModel.onAction(action)
        },
        scrollBehavior
    )
}

@OptIn(ExperimentalSharedTransitionApi::class, ExperimentalMaterial3Api::class)
@Composable
fun NoteDetailsScreen(
    state: NoteDetailsState,
    onAction: (NoteDetailsAction) -> Unit,
    scrollBehavior: TopAppBarScrollBehavior
) {


    var contentValue by remember { mutableStateOf(TextFieldValue(
        state.note?.title ?: ""
    )) }
    var titleValue by remember { mutableStateOf(TextFieldValue(
        state.note?.content ?: ""
    )) }

    LaunchedEffect(state.note?.id) {

        state.note?.content?.let {
            contentValue = TextFieldValue(
                text = it,
                selection = TextRange(it.length)
            )
        }
        state.note?.title?.let {
            titleValue = TextFieldValue(
                text = it,
                selection = TextRange(it.length)
            )
        }
    }

    LaunchedEffect(titleValue.text, contentValue.text) {
        onAction(NoteDetailsAction.OnTextChange(
            title = titleValue.text,
            content = contentValue.text
        ))
    }

    val scrollState = rememberScrollState()

    val focusRequester = remember { FocusRequester() }

    var showDeleteDialog by remember { mutableStateOf(false) }
    var showInfoDialog by remember { mutableStateOf(false) }

    LaunchedEffect(contentValue.selection) {
        scrollState.animateScrollTo(scrollState.maxValue)
    }


        Scaffold(
            topBar = {
                NoteDetailsTopAppBar(
                    modifier = Modifier,
                    scrollBehavior = scrollBehavior,
                    onDeleteClick = {
                        showDeleteDialog = true
                    },
                    onBackClick = {
                        onAction(NoteDetailsAction.OnSave)
                        onAction(NoteDetailsAction.OnBackClick)
                    },
                    onFavoriteClick = {
                        onAction(NoteDetailsAction.OnFavoriteClick)
                    },
                    onInfoClick = {
                        showInfoDialog = true
                    },
                    scrollState = scrollState
                )
            },
            contentWindowInsets = WindowInsets.systemBars,
            contentColor = MaterialTheme.colorScheme.surfaceContainerHigh,
            containerColor = MaterialTheme.colorScheme.surfaceContainerHigh
        ){ innerPadding ->
            Column(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .padding(innerPadding)
                    .imePadding()
                    .verticalScroll(scrollState)
            ) {

                if (showDeleteDialog) {
                    ConfirmationAlertDialog(
                        dialogTitle = "Suppression",
                        dialogText = "Êtes vous sûr de vouloir supprimer cette note",
                        icon = Icons.Default.Delete,
                        onDismiss = {
                            showDeleteDialog = false
                        },
                        onConfirm = {
                            onAction(NoteDetailsAction.OnDeleteClick)
                            onAction(NoteDetailsAction.OnBackClick)
                            showDeleteDialog = false
                        }
                    )
                }

                if (showInfoDialog){
                    AlertDialog(
                        icon = {
                            Icon(Icons.Outlined.Info, contentDescription = "Info Icon")
                        },
                        title = { state.note?.title?.let { Text(text = it) } },
                        text = {
                            state.note?.let {
                                Column {
                                    if (it.createdAt != 0L)
                                        Text("Créé le ${TimeProvider.formatMillis(it.createdAt)}")

                                    if (it.updatedAt != 0L)
                                        Text("Modifié le ${TimeProvider.formatMillis(it.updatedAt)}")

                                    Text("Caractères: ${it.title.length + it.content.length}")
                                }
                            }
                        },
                        onDismissRequest = { showInfoDialog = false },
                        confirmButton = {
                            TextButton(onClick = { showInfoDialog = false }) { Text("Ok") }
                        },
                    )
                }

                BasicTextField(
                    value = titleValue,
                    onValueChange ={titleValue = it},
                    textStyle = MaterialTheme.typography.titleLarge.copy(color = MaterialTheme.colorScheme.onSurface),
                    singleLine = true,
                    decorationBox = @Composable { innerTextField ->
                        Box(
                            modifier = Modifier.fillMaxWidth(),
                            contentAlignment = Alignment.CenterStart
                        ) {
                            // PLACEHOLDER
                            if (titleValue.text.isEmpty()) {
                                Text(
                                    text = "Titre de la note...",
                                    style = MaterialTheme.typography.titleLarge,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(
                                        alpha = 0.6f
                                    )
                                )
                            }
                            innerTextField()
                        }
                    }
                )
                Spacer(Modifier.height(16.dp))
                BasicTextField(
                    value = contentValue,
                    onValueChange = { newTextValue ->
                        contentValue = newTextValue
                    },
                    modifier = Modifier
                        .fillMaxSize()
                        .focusRequester(focusRequester),
                    textStyle = TextStyle(
                        color = MaterialTheme.colorScheme.onBackground,
                        fontSize = 18.sp
                    ),
                    decorationBox = @Composable { innerTextField ->
                        Box(
                            modifier = Modifier.fillMaxWidth(),
                            contentAlignment = Alignment.CenterStart
                        ) {
                            if (contentValue.text.isEmpty()) {
                                Text(
                                    text = "Contenue de la note...",
                                    style = MaterialTheme.typography.bodyLarge,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(
                                        alpha = 0.6f
                                    )
                                )
                            }
                            innerTextField()
                        }
                    },
                    cursorBrush = SolidColor(MaterialTheme.colorScheme.primary)
                )
            }
        }
}