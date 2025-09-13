package fr.bonifaciosoftwares.notes.app

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import fr.bonifaciosoftwares.notes.notes.presentation.note_details.NoteDetailsScreenRoot
import fr.bonifaciosoftwares.notes.notes.presentation.note_details.NoteDetailsViewModel
import fr.bonifaciosoftwares.notes.notes.presentation.notes_list.NotesListScreenRoot
import fr.bonifaciosoftwares.notes.notes.presentation.notes_list.NotesListViewModel
import org.jetbrains.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterial3Api::class, ExperimentalSharedTransitionApi::class)
@Composable
@Preview
fun App() {
    MaterialTheme(
        colorScheme = MaterialTheme.colorScheme.copy(
            //background = SandYellowLight
        )
    ) {

        val navController = rememberNavController()

        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination

        val currentRouteIsBottomBarRoute = allBottomRoutes.any { bottomRoute ->
            currentDestination?.hierarchy?.any {
                it.hasRoute(bottomRoute.route::class)
            } == true
        }

        val currentRouteIsNotesList = currentDestination?.hasRoute(Route.NotesList::class) == true

        val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())

        Scaffold(
            modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
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
            floatingActionButton = {
                if (currentRouteIsNotesList){
                    FloatingActionButton(
                        onClick = {
                            navController.navigateSingleTopTo(Route.NoteDetails())
                        },
                    ){
                        Icon(Icons.Default.Edit, null)
                    }
                }
            },
        ){ innerPadding ->
            SharedTransitionLayout {
                NavHost(
                    navController = navController,
                    startDestination = Route.NotesList,
                    modifier = Modifier.padding(
                        bottom = innerPadding.calculateBottomPadding(),
                        start = 12.dp,
                        end = 12.dp
                    )
                ){
                    composable<Route.NotesList>{
                        NotesListScreenRoot(
                            viewModel = NotesListViewModel(),
                            onNoteClick = { note ->
                                navController.currentBackStackEntry?.savedStateHandle?.set("noteId", note.id)
                                navController.navigateSingleTopTo(Route.NoteDetails(noteId = note.id))
                            },
                            scrollBehavior = scrollBehavior,
                            this@SharedTransitionLayout,
                            this@composable
                        )
                    }
                    composable<Route.Profile> {
                        Text("Coucou je suis la page de profil")
                    }

                    composable<Route.NoteDetails>{ backStackEntry ->
                        val noteId = navController.previousBackStackEntry?.savedStateHandle?.get<Long>("noteId")
                        NoteDetailsScreenRoot(
                            viewModel = NoteDetailsViewModel(
                                noteId = noteId
                            ),
                            onBackClick = { navController.popBackStack() },
                            this@SharedTransitionLayout,
                            this@composable
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
        launchSingleTop = true
        restoreState = true
    }

