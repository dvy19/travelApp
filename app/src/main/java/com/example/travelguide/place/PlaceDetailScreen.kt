package com.example.travelguide.place

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Public
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.example.travelguide.SessionManager


@Composable
fun PlaceDetailScreen(
    mainNavController: NavController,
    modifier:Modifier,
    placeId: Int?
) {
    val scrollState = rememberScrollState()


    val context=LocalContext.current

    val sessionManager= SessionManager(context)

    val repo=PlaceRepository(sessionManager)

    val viewModel: PlaceViewModel=viewModel(
        factory = PlaceViewModelFactory(repo)
    )


    LaunchedEffect(Unit){

        placeId?.let {
            viewModel.fetchSinglePlace(it)
        }
    }

    val getSinglePlaceState by viewModel.getSinglePlaceState.collectAsState()

    when(val state=getSinglePlaceState){

        is GetSinglePlaceState.Idle->{

        }

        is  GetSinglePlaceState.Loading->{

        }

        is GetSinglePlaceState.Success->{

            val place=state.place

            Column(
                modifier = modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState)
            ) {
                // 1. Hero Image Header (Full width, structured height)
                AsyncImage(
                    model = place.image_url,
                    contentDescription = place.name,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                )

                // 2. Text Details Content Area
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    // Header Info: Title, City, and Category
                    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = place.name,
                                style = MaterialTheme.typography.headlineMedium,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface,
                                modifier = Modifier.weight(1f)
                            )
                        }

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Place,
                                contentDescription = "City Icon",
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.height(18.dp)
                            )
                            Text(
                                text = place.city_name,
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }

                        Spacer(modifier = Modifier.height(4.dp))

                        // Material 3 Tag Chip for Category
                        AssistChip(
                            onClick = { /* Handle filtering/actions if needed */ },
                            label = {
                                Text(
                                    text = place.category_name,
                                    style = MaterialTheme.typography.labelMedium
                                )
                            },
                            colors = AssistChipDefaults.assistChipColors(
                                labelColor = MaterialTheme.colorScheme.primary
                            )
                        )
                    }

                    HorizontalDivider(color = MaterialTheme.colorScheme.surfaceVariant)

                    // Core Description Section
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Text(
                            text = "About",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.SemiBold
                        )
                        Text(
                            text = place.description,
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            lineHeight = MaterialTheme.typography.bodyLarge.lineHeight * 1.25
                        )
                    }

                    // Historical Significance Section
                    if (place.historical_significance.isNotEmpty()) {
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
                            )
                        ) {
                            Column(
                                modifier = Modifier.padding(16.dp),
                                verticalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.History,
                                        contentDescription = "History Icon",
                                        tint = MaterialTheme.colorScheme.primary
                                    )
                                    Text(
                                        text = "Historical Significance",
                                        style = MaterialTheme.typography.titleMedium,
                                        fontWeight = FontWeight.SemiBold
                                    )
                                }
                                Text(
                                    text = place.historical_significance,
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        Button(
                            onClick = {

                                mainNavController.navigate("addReview/${place.id}")

                            },
                            modifier = Modifier.fillMaxWidth(),
                            shape = MaterialTheme.shapes.small
                        ) {
                            Text(
                                text = "Add Review",
                                style = MaterialTheme.typography.titleMedium,

                                )
                        }
                    }

                    // Logistical and Contact Details Grid Structure
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(14.dp)
                    ) {
                        Text(
                            text = "Information & Logistics",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.SemiBold
                        )

                        InfoRow(
                            icon = Icons.Default.Schedule,
                            label = "Timings",
                            value = "${place.opening_time} - ${place.closing_time}"
                        )

                        InfoRow(
                            icon = Icons.Default.Info,
                            label = "Entry Fee",
                            value = place.entry_fee
                        )

                        InfoRow(
                            icon = Icons.Default.LocationOn,
                            label = "Coordinates",
                            value = "Lat: ${place.latitude}, Long: ${place.longitude}"
                        )

                        InfoRow(
                            icon = Icons.Default.Call,
                            label = "Contact",
                            value = place.contact_number
                        )

                        InfoRow(
                            icon = Icons.Default.Public,
                            label = "Website",
                            value = place.website,
                            isLink = true
                        )
                    }
                }


            }

        }

        is GetSinglePlaceState.Error->{
            Text(text = "Failed")
        }
    }


}

// Reusable structural component for organized info key-value rows
@Composable
fun InfoRow(
    icon: ImageVector,
    label: String,
    value: String,
    isLink: Boolean = false,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.Top
    ) {
        Icon(
            imageVector = icon,
            contentDescription = "$label Icon",
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(top = 2.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column {
            Text(
                text = label,
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f)
            )
            Text(
                text = value,
                style = MaterialTheme.typography.bodyLarge,
                color = if (isLink) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface,
                fontWeight = if (isLink) FontWeight.Medium else FontWeight.Normal
            )
        }
    }
}


@Preview(name = "Light Mode", showBackground = true)
@Preview(name = "Dark Mode", uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun PlaceDetailScreenPreview() {
    // Structural mock data mapping all details required for the travel guide app
    val mockPlace = SinglePlaceData(
        id = 1,
        city = 1,
        city_name = "City A",
        category = 1,
        category_name = "Category X",
        name = "Place Name",
        description = "Description of the place",
        historical_significance = "Historical significance of the place",
        address = "Address of the place",
        latitude = 40.7128,
        longitude = -74.0060,
        opening_time = "09:00",
        closing_time = "18:00",
        entry_fee = "Free",
        contact_number = "123-456-7890",
        website = "https://example.com",
        image_url = "https://example.com/image.jpg",
        created_at = "2023-09-01T12:00:00Z",
        reviews = emptyList()

    )

    // Using MaterialTheme theme wrapper to ensure the preview renders background surfaces correctly
    MaterialTheme {
        Surface(
            color = MaterialTheme.colorScheme.background
        ) {
            PlaceDetailScreen( mainNavController = rememberNavController() , placeId = 2, modifier = Modifier)
        }
    }
}

