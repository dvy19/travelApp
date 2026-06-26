package com.example.travelguide.user.screens

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
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.travelguide.SessionManager
import com.example.travelguide.place.FamousPlace
import com.example.travelguide.place.GetFamousPlaceState
import com.example.travelguide.place.PlaceRepository
import com.example.travelguide.place.PlaceViewModel
import com.example.travelguide.place.PlaceViewModelFactory


@Composable
fun FamousPlacesScreen(
    mainNavController: NavController
){

    val context= LocalContext.current

    val sessionManager= SessionManager(context)

    val repo= PlaceRepository(sessionManager)

    val viewModel: PlaceViewModel= viewModel(
        factory = PlaceViewModelFactory(repo)
    )


    var searchText by remember {
        mutableStateOf("")
    }

    LaunchedEffect(Unit) {
        viewModel.fetchFamousPlaces()
    }

    val getFamousPlaceState by viewModel.getFamousPlaceState.collectAsState()


    when(val state=getFamousPlaceState){

        is GetFamousPlaceState.Idle->{
            Text("Idle")
        }
        is GetFamousPlaceState.Loading->{
            Text("Loading")
        }

        is GetFamousPlaceState.Success->{
            val places=state.places

            LazyColumn (

                modifier = Modifier.fillMaxSize()
                    .padding(24.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {

                items(
                    items = places,
                    key = { it.id }
                ) { place ->

                    FamousPlaceCard(
                        place = place,
                        onClick = {

                        }
                    )

                }




            }


        }

        is GetFamousPlaceState.Error->{
            Text("Error")
        }
    }

    }





@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FamousPlaceCard(
    place: FamousPlace,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    ElevatedCard(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .height(128.dp), // Fixed height for a balanced, premium row layout
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.elevatedCardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            // 1. Image Section with Aspect Ratio Block
            Box(
                modifier = Modifier
                    .weight(0.35f)
                    .fillMaxHeight()
            ) {

                // Subtle Category Badge overlaid on the top-left of the image
                Surface(
                    color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.9f),
                    shape = RoundedCornerShape(bottomEnd = 8.dp),
                    modifier = Modifier.align(Alignment.TopStart)
                ) {

                    Text(
                        text = place.city,
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )

                    Text(
                        text = place.name,
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }
            }

            // 2. Text Details Section
            Column(
                modifier = Modifier
                    .weight(0.65f)
                    .fillMaxHeight()
                    .padding(horizontal = 16.dp, vertical = 14.dp),
                verticalArrangement = Arrangement.Center
            ) {
                // Name Title
                Text(
                    text = place.country,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(4.dp))

                // City / Location Subtitle
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Place,
                        contentDescription = "Location Pin",
                        tint = MaterialTheme.colorScheme.secondary,
                        modifier = Modifier.size(16.dp)
                    )
                    Text(
                        text = place.famous_for,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.secondary
                    )
                }
            }
        }
    }
}