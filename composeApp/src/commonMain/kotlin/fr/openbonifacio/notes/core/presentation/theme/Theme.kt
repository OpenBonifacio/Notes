package fr.openbonifacio.notes.core.presentation.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable

@Composable
fun NotesTheme(darkTheme: Boolean = false, content: @Composable () -> Unit) {

    val colorScheme = if (darkTheme) {
        DarkNotesColorsScheme
    } else {
        LightNotesColorsScheme
    }

    MaterialTheme(
        content = content,
        colorScheme = colorScheme,
        typography = notesTypography(),
    )
}