package fr.openbonifacio.notes

import androidx.compose.ui.window.ComposeUIViewController
import fr.openbonifacio.notes.app.App
import fr.openbonifacio.notes.di.initKoin

fun MainViewController() = ComposeUIViewController(
    configure = {
        initKoin()
    }
) { App() }