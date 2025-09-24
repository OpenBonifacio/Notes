package fr.bonifaciosoftwares.notes.notes.presentation.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.graphics.shapes.CornerRounding
import androidx.graphics.shapes.RoundedPolygon

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
                .padding(innerPadding)
                .padding(bottom = parrentPadding.calculateBottomPadding())
        ){
            Text("Bienvenue sur la page de profil !")
        }
    }
}