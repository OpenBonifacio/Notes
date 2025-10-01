package fr.bonifaciosoftwares.notes.notes.presentation.profile

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun ProfileScreenRoot(
    modifier: Modifier = Modifier,
    parrentPadding: PaddingValues
) {
    ProfileScreen(
        modifier = modifier,
        parrentPadding = parrentPadding
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
    parrentPadding: PaddingValues
){

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Profil") }
            )
        }
    ){
        innerPadding ->

        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(bottom = parrentPadding.calculateBottomPadding()),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Text("Rien à dire")
        }
    }
}