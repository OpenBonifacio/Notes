package fr.openbonifacio.notes.notes.presentation.note_details


import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.relocation.bringIntoViewRequester
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.focus.requestFocus
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInRoot
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import fr.openbonifacio.notes.core.presentation.components.ConfirmationAlertDialog
import fr.openbonifacio.notes.notes.presentation.note_details.components.NoteDetailsTopAppBar
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalSharedTransitionApi::class, ExperimentalMaterial3Api::class)
@Composable
fun NoteDetailsScreenRoot(
    viewModel: NoteDetailsViewModel,
    onBackClick: () -> Unit,
    sharedTransitionScope: SharedTransitionScope,
    animatedContentScope: AnimatedContentScope,
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
        sharedTransitionScope,
        animatedContentScope,
        scrollBehavior
    )
}

@OptIn(ExperimentalSharedTransitionApi::class, ExperimentalMaterial3Api::class)
@Composable
fun NoteDetailsScreen(
    state: NoteDetailsState,
    onAction: (NoteDetailsAction) -> Unit,
    sharedTransitionScope: SharedTransitionScope,
    animatedContentScope: AnimatedContentScope,
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

    var showDialog by remember { mutableStateOf(false) }

    LaunchedEffect(contentValue.selection) {
        scrollState.animateScrollTo(scrollState.maxValue)
    }

    with(sharedTransitionScope){
        Scaffold(
            topBar = {
                NoteDetailsTopAppBar(
                    modifier = Modifier,
                    scrollBehavior = scrollBehavior,
                    onDeleteClick = {
                        /*onAction(NoteDetailsAction.OnDeleteClick)
                        onAction(NoteDetailsAction.OnBackClick)*/
                        showDialog = true
                    },
                    onBackClick = {
                        onAction(NoteDetailsAction.OnSave)
                        onAction(NoteDetailsAction.OnBackClick)
                    },
                    onFavoriteClick = {
                        onAction(NoteDetailsAction.OnFavoriteClick)
                    },
                    scrollState = scrollState            )
            },
            contentWindowInsets = WindowInsets.systemBars,
        ){ innerPadding ->
            Column(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .padding(innerPadding)
                    .imePadding()
                    .verticalScroll(scrollState)
            ) {

                if (showDialog) {
                    ConfirmationAlertDialog(
                        dialogTitle = "Suppression",
                        dialogText = "Êtes vous sûr de vouloir supprimer cette note",
                        icon = Icons.Default.Delete,
                        onDismiss = {
                            showDialog = false
                        },
                        onConfirm = {
                            onAction(NoteDetailsAction.OnDeleteClick)
                            onAction(NoteDetailsAction.OnBackClick)
                            showDialog = false
                        }
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
}

    /*var titleText by remember { mutableStateOf(
        state.note?.title ?: ""
    )}

    var contentText by remember { mutableStateOf(
        state.note?.content ?: ""
    ) }

    LaunchedEffect(state.note?.id) {
        contentText = state.note?.content ?: ""
        titleText = state.note?.title ?: ""
    }

    LaunchedEffect(titleText, contentText) {
        onAction(NoteDetailsAction.OnTextChange(
            title = titleText,
            content = contentText
        ))
    }


    val columnScrollableState = rememberScrollState()

    var showDialog by remember { mutableStateOf(false) }


    //Réinitialiser la page en haut
    LaunchedEffect(Unit) {
        columnScrollableState.scrollTo(0)
    }

    val bringIntoViewRequester = remember { BringIntoViewRequester() }
    val coroutineScope = rememberCoroutineScope()

    with(sharedTransitionScope){
        Scaffold(
            modifier = Modifier
                .fillMaxSize()
                //.nestedScroll(scrollBehavior.nestedScrollConnection)
                .sharedElement(
                    sharedContentState = rememberSharedContentState(
                        key = if (state.note?.id == 0L) "new" else "content-${state.note?.id}"
                    ),
                    animatedVisibilityScope = animatedContentScope
                ),
                contentWindowInsets = WindowInsets.systemBars,
            topBar = {
                NoteDetailsTopAppBar(
                    modifier = Modifier,
                    scrollBehavior = scrollBehavior,
                    onDeleteClick = {
                        /*onAction(NoteDetailsAction.OnDeleteClick)
                        onAction(NoteDetailsAction.OnBackClick)*/
                        showDialog = true
                    },
                    onBackClick = {
                        onAction(NoteDetailsAction.OnSave)
                        onAction(NoteDetailsAction.OnBackClick)
                    },
                    onFavoriteClick = {
                        onAction(NoteDetailsAction.OnFavoriteClick)
                    },
                    scrollState = columnScrollableState
                )
            }
        ) { innerPadding ->
            Box (
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
                    .imePadding()
                    //.verticalScroll(columnScrollableState)
            ){
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                ) {

                    if (showDialog) {
                        ConfirmationAlertDialog(
                            dialogTitle = "Suppression",
                            dialogText = "Êtes vous sûr de vouloir supprimer cette note",
                            icon = Icons.Default.Delete,
                            onDismiss = {
                                showDialog = false
                            },
                            onConfirm = {
                                onAction(NoteDetailsAction.OnDeleteClick)
                                onAction(NoteDetailsAction.OnBackClick)
                                showDialog = false
                            }
                        )
                    }

                    BasicTextField(
                        value = titleText,
                        onValueChange = {
                            titleText = it
                        },
                        modifier = Modifier
                            .fillMaxWidth(),
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
                        value = contentText,
                        onValueChange = {
                            contentText = it
                        },
                        modifier = Modifier
                            .fillMaxWidth(),
                        textStyle = MaterialTheme.typography.bodyLarge.copy(color = MaterialTheme.colorScheme.onSurface),
                        decorationBox = @Composable { innerTextField ->
                            Box(
                                modifier = Modifier.fillMaxWidth(),
                                contentAlignment = Alignment.CenterStart
                            ) {
                                if (contentText.isEmpty()) {
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
                        }
                    )
                }
            }
        }
    }*/