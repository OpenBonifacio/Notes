package fr.bonifaciosoftwares.notes

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import fr.bonifaciosoftwares.notes.app.App
import fr.bonifaciosoftwares.notes.di.initKoin

fun main() = application {
    initKoin()
    Window(
        onCloseRequest = ::exitApplication,
        title = "Notes",
    ) {
        App()
    }
}