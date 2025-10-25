package fr.openbonifacio.notes.core.presentation.theme

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import notes.composeapp.generated.resources.LobsterTwo_Bold
import notes.composeapp.generated.resources.LobsterTwo_BoldItalic
import notes.composeapp.generated.resources.LobsterTwo_Italic
import notes.composeapp.generated.resources.LobsterTwo_Regular
import notes.composeapp.generated.resources.Res
import org.jetbrains.compose.resources.Font

/**
 * Custom Fonts
 */
@Composable
fun lobsterTwoFamily() = FontFamily(
    Font(Res.font.LobsterTwo_Regular, weight = FontWeight.Normal),
    Font(Res.font.LobsterTwo_Bold, weight = FontWeight.Bold),
    Font(Res.font.LobsterTwo_BoldItalic, weight = FontWeight.Bold, style = FontStyle.Italic),
    Font(Res.font.LobsterTwo_Italic, style = FontStyle.Italic),
)