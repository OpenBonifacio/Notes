package fr.openbonifacio.notes.app

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
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
import fr.openbonifacio.notes.core.presentation.theme.AppTheme
import fr.openbonifacio.notes.notes.presentation.note_details.NoteDetailsScreenRoot
import fr.openbonifacio.notes.notes.presentation.note_details.NoteDetailsViewModel
import fr.openbonifacio.notes.notes.presentation.notes_list.NotesListScreenRoot
import fr.openbonifacio.notes.notes.presentation.notes_list.NotesListViewModel
import fr.openbonifacio.notes.notes.presentation.profile.ProfileScreenRoot
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf

@OptIn(ExperimentalMaterial3Api::class, ExperimentalSharedTransitionApi::class)
@Composable
@Preview
fun App() {
    AppTheme{
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
                AnimatedVisibility(
                    visible = currentRouteIsBottomBarRoute,
                    enter = fadeIn(animationSpec = tween(220)) + slideInVertically(
                        animationSpec = tween(220),
                        initialOffsetY = { fullHeight -> fullHeight }
                    ),
                    exit = fadeOut(animationSpec = tween(180)) + slideOutVertically(
                        animationSpec = tween(180),
                        targetOffsetY = { fullHeight -> fullHeight }
                    )
                ) {
                    NavigationBar {
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
                                navController.navigateSingleTopTo(Route.NoteDetails("0"))
                            },
                            parrentPadding = innerPadding
                        )
                    }
                    composable<Route.Profile> {
                        ProfileScreenRoot(
                            parrentPadding = innerPadding
                        )
                    }
                    composable<Route.NoteDetails>(
                        enterTransition = { scaleIn() },
                        exitTransition = { scaleOut() }
                    ){ backStackEntry ->
                        val routeArgs: Route.NoteDetails = backStackEntry.toRoute() // Extension de la lib navigation-compose
                        val noteIdFromRoute = routeArgs.noteId

                        NoteDetailsScreenRoot(
                            viewModel = koinViewModel<NoteDetailsViewModel>(
                                parameters = { parametersOf(noteIdFromRoute) }
                            ),
                            onBackClick = { navController.popBackStack() },
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