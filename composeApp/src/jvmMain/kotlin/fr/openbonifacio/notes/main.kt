package fr.openbonifacio.notes

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import fr.openbonifacio.notes.app.App
import fr.openbonifacio.notes.di.initKoin

fun main() = application {
    initKoin()
    Window(
        onCloseRequest = ::exitApplication,
        title = "Notes",
    ) {
        App()
    }
}