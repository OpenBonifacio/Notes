package fr.bonifaciosoftwares.notes.app

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import fr.bonifaciosoftwares.notes.notes.presentation.note_details.NoteDetailsScreenRoot
import fr.bonifaciosoftwares.notes.notes.presentation.note_details.NoteDetailsViewModel
import fr.bonifaciosoftwares.notes.notes.presentation.notes_list.NotesListScreenRoot
import fr.bonifaciosoftwares.notes.notes.presentation.notes_list.NotesListViewModel
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf

@OptIn(ExperimentalMaterial3Api::class, ExperimentalSharedTransitionApi::class)
@Composable
@Preview
fun App() {
    MaterialTheme{

        val navController = rememberNavController()

        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination

        val currentRouteIsBottomBarRoute = allBottomRoutes.any { bottomRoute ->
            currentDestination?.hierarchy?.any {
                it.hasRoute(bottomRoute.route::class)
            } == true
        }

        Scaffold(
            bottomBar = {
                if (currentRouteIsBottomBarRoute) {
                    NavigationBar(

                    ) {
                        allBottomRoutes.forEach { bottomRoute ->
                            NavigationBarItem(
                                label = { Text(bottomRoute.label) },
                                icon = { Icon(bottomRoute.icon, null) },
                                onClick = {
                                    navController.navigateSingleTopTo(bottomRoute.route)
                                },
                                selected = currentDestination?.hierarchy?.any {
                                    it.hasRoute(bottomRoute.route::class)
                                } == true
                            )

                        }
                    }
                }
            },
        ){ innerPadding ->
            SharedTransitionLayout {
                NavHost(
                    navController = navController,
                    startDestination = Route.NotesList,
                ){
                    composable<Route.NotesList>{
                        val viewModel = koinViewModel<NotesListViewModel>()
                        NotesListScreenRoot(
                            viewModel = viewModel,
                            onNoteClick = { note ->
                                navController.navigateSingleTopTo(Route.NoteDetails(noteId = note.id))
                            },
                            onFabClick = {
                                navController.navigateSingleTopTo(Route.NoteDetails(0L))
                            },
                            this@SharedTransitionLayout,
                            this@composable,
                            innerPadding
                        )
                    }
                    composable<Route.Profile> {
                        Text("Coucou je suis la page de profil")
                    }

                    composable<Route.NoteDetails>{ backStackEntry ->
                        val routeArgs: Route.NoteDetails = backStackEntry.toRoute() // Extension de la lib navigation-compose
                        val noteIdFromRoute = routeArgs.noteId

                        NoteDetailsScreenRoot(
                            viewModel = koinViewModel<NoteDetailsViewModel>(
                                parameters = { parametersOf(noteIdFromRoute) }
                            ),
                            onBackClick = { navController.popBackStack() },
                            this@SharedTransitionLayout,
                            this@composable,
                        )
                    }
                }
            }
        }
    }
}

fun <T: Route> NavHostController.navigateSingleTopTo(route: T) =
    this.navigate(route) {
        popUpTo(
            this@navigateSingleTopTo.graph.findStartDestination().id
        ) {
            saveState = true
        }
    }