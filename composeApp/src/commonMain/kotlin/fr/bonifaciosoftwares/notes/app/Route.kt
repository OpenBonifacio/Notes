package fr.bonifaciosoftwares.notes.app

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector
import kotlinx.serialization.Serializable

sealed interface Route {

    @Serializable
    data object NotesList: Route

    @Serializable
    data object Profile: Route

    @Serializable
    data class NoteDetails(val noteId: Long? = null): Route
}

data class BottomBarRoute<T: Route>(val label:String, val route:T, val icon: ImageVector)

val allBottomRoutes = listOf(
    BottomBarRoute("Notes", Route.NotesList, Icons.Default.Home),
    BottomBarRoute("Profile", Route.Profile, Icons.Default.Settings)
)
