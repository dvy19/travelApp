package com.example.travelguide.city

import android.se.omapi.Session
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.travelguide.SessionManager

// Sharing the exact same clean, premium light palette
val TravelBgLight = Color(0xFFFAFAFA)
val CardSurfaceLight = Color(0xFFF3F4F6)
val TextPrimary = Color(0xFF1F2937)
val TextSecondary = Color(0xFF4B5563)
val AccentTeal = Color(0xFF0F766E)


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CityDetailScreen(
    mainNavController: NavController,
    cityId:Int?,
    //onCategoryClick: (String) -> Unit,
    //onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()

    val context=LocalContext.current

    val sessionManager= SessionManager(context)
    val repo= CityRepository(sessionManager)

    val viewModel:CityViewModel=viewModel(
        factory = CityViewModelFactory(repo)
    )

    LaunchedEffect(Unit){

        cityId?.let{
            viewModel.fetchSingleCity(cityId)
        }
    }


    val singleCityState by viewModel.getSingleCityState.collectAsState()

    when (val state=singleCityState){

        is GetSingleCityState.Idle->{

        }

        is GetSingleCityState.Loading->{
            Text("loading")
        }

        is GetSingleCityState.Success->{

            val city=state.city

            Scaffold(
                modifier = modifier.fillMaxSize(),
                containerColor = TravelBgLight
            ) { innerPadding ->
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                        .verticalScroll(scrollState)
                ) {
                    // 1. Hero Header Image Section
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(280.dp)
                    ) {
                        AsyncImage(
                            model = city.image,
                            contentDescription = "Header image of ${city.name}",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )

                        // Minimal Back Button Overlay
                        IconButton(
                            onClick = {  },
                            modifier = Modifier
                                .padding(16.dp)
                                .size(40.dp)
                                .background(
                                    Color.White.copy(alpha = 0.9f),
                                    RoundedCornerShape(20.dp)
                                )
                                .align(Alignment.TopStart)
                        ) {
                            Icon(
                                imageVector = Icons.Default.ArrowBack, // Ensure standard material icons are linked
                                contentDescription = "Back",
                                tint = TextPrimary,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }

                    // 2. Details Content Block
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 24.dp, vertical = 20.dp)
                    ) {
                        // Name and State tag
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.Bottom
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = city.name,
                                    style = MaterialTheme.typography.headlineMedium.copy(
                                        fontWeight = FontWeight.Bold,
                                        letterSpacing = (-0.5).sp
                                    ),
                                    color = TextPrimary
                                )
                                Spacer(modifier = Modifier.height(2.dp))
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(
                                        imageVector = Icons.Default.LocationOn,
                                        contentDescription = null,
                                        tint = AccentTeal,
                                        modifier = Modifier.size(14.dp)
                                    )
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text(
                                        text = city.state,
                                        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Medium),
                                        color = TextSecondary
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        // Quick coordinates chip layout (Modern apps map display element)
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            CoordinateChip(label = "Lat: ${city.latitude}")
                            CoordinateChip(label = "Long: ${city.longitude}")
                        }

                        Spacer(modifier = Modifier.height(18.dp))

                        // Description
                        Text(
                            text = "About",
                            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                            color = TextPrimary
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = city.description,
                            style = MaterialTheme.typography.bodyMedium.copy(lineHeight = 22.sp),
                            color = TextSecondary
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        // Famous For Callout Banner
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(CardSurfaceLight, RoundedCornerShape(12.dp))
                                .padding(14.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.Star,
                                contentDescription = null,
                                tint = AccentTeal,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(10.dp))
                            Column {
                                Text(
                                    text = "Famous For",
                                    style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                                    color = TextPrimary
                                )
                                Text(
                                    text = city.famous_for?:" ",
                                    style = MaterialTheme.typography.labelSmall.copy(fontSize = 11.sp),
                                    color = TextSecondary
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(28.dp))

                        // 3. Category Explorers Header
                        Text(
                            text = "Discover More",
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                            color = TextPrimary
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        // 2x2 Grid Layout for quick category tapping
                        Column(
                            verticalArrangement = Arrangement.spacedBy(12.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(12.dp),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                CategoryDiscoveryCard(
                                    title = "Places",
                                    subtitle = "Must-visit spots",
                                    modifier = Modifier.weight(1f),
                                    onClick = {
                                        mainNavController.navigate("placeByCity/${city.id}")

                                    }
                                )
                                CategoryDiscoveryCard(
                                    title = "Food",
                                    subtitle = "Local cuisines",
                                    modifier = Modifier.weight(1f),
                                    onClick = {  }
                                )
                            }
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(12.dp),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                CategoryDiscoveryCard(
                                    title = "Hotels",
                                    subtitle = "Stay cozy",
                                    modifier = Modifier.weight(1f),
                                    onClick = {  }
                                )
                                CategoryDiscoveryCard(
                                    title = "Temples",
                                    subtitle = "Spiritual heritage",
                                    modifier = Modifier.weight(1f),
                                    onClick = {  }
                                )
                            }
                        }
                    }
                }
            }

        }

        is GetSingleCityState.Error->{

        }
    }


}

@Composable
fun CoordinateChip(label: String) {
    Box(
        modifier = Modifier
            .background(CardSurfaceLight, RoundedCornerShape(8.dp))
            .padding(horizontal = 10.dp, vertical = 6.dp)
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Medium),
            color = TextSecondary
        )
    }
}

@Composable
fun CategoryDiscoveryCard(
    title: String,
    subtitle: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = CardSurfaceLight),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        modifier = modifier
            .height(76.dp)
            .clickable { onClick() }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 16.dp, end = 12.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                color = TextPrimary
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = subtitle,
                style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.sp),
                color = AccentTeal,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Preview
@Composable
fun PreviewCity(){

}