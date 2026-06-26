package com.example.travelguide.user.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.travelguide.Screens
import com.example.travelguide.SessionManager
import com.example.travelguide.place.FamousPlace
import com.example.travelguide.place.GetFamousPlaceState
import com.example.travelguide.place.PlaceRepository
import com.example.travelguide.place.PlaceViewModel
import com.example.travelguide.place.PlaceViewModelFactory


@Composable
fun ExploreScreen(
    mainNavController: NavController
){

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        item {
            WorldFamousPlacesCard(
                onClick = {
                    mainNavController.navigate(Screens.FamousPlaceScreen.route)
                }
            )
        }

    }
}
@Composable
fun WorldFamousPlacesCard(
    modifier: Modifier = Modifier,
    onClick : () -> Unit,
) {
    Card(
        modifier = modifier.fillMaxWidth()
            .clickable(onClick=onClick),

        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        )
    ) {
        Column(
            modifier = Modifier.padding(24.dp)
        ) {

            Text(
                text = "🌍 World Famous Places",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Explore the most visited landmarks, monuments, natural wonders, and cultural treasures across the globe.",
                style = MaterialTheme.typography.bodyLarge
            )

            Spacer(modifier = Modifier.height(12.dp))

            AssistChip(
                onClick = {

                },
                label = {
                    Text("30+ Destinations")
                }
            )
        }
    }
}

@Preview
@Composable
fun PreviewWorldFamousPlacesCard() {

    ExploreScreen(mainNavController = rememberNavController())

}