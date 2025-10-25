package fr.openbonifacio.notes.core.presentation.theme

import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable

@Composable
fun notesTypography() = Typography().run {
    copy(
        headlineLarge = headlineLarge.copy(
            fontFamily = lobsterTwoFamily()
        )
    )
}