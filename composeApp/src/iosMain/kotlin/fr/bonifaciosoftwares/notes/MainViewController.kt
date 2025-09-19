package fr.bonifaciosoftwares.notes

import androidx.compose.ui.window.ComposeUIViewController
import fr.bonifaciosoftwares.notes.app.App
import fr.bonifaciosoftwares.notes.di.initKoin

fun MainViewController() = ComposeUIViewController(
    configure = {
        initKoin()
    }
) { App() }