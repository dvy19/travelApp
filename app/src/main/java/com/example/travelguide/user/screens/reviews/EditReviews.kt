package com.example.travelguide.user.screens.reviews

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.travelguide.SessionManager
import com.example.travelguide.place.PlaceRepository
import com.example.travelguide.place.PlaceViewModel
import com.example.travelguide.place.PlaceViewModelFactory
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


private val TravelBackground = Color(0xFFFBFBFB)
private val TravelPrimary = Color(0xFFE05A47) // Sunset Terracotta
private val TravelTextPrimary = Color(0xFF1A1A1A) // Deep Black/Charcoal
private val TravelTextSecondary = Color(0xFF666666)
private val TravelSurface = Color(0xFFF3F3F3)
private val TravelRatingGold = Color(0xFFFFB300)
private val TravelError = Color(0xFFBA1A1A)


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditReviewScreen(
    mainNavController: NavController
){

    val context= LocalContext.current

    val sessionManager=SessionManager(context)

    val repo= PlaceRepository(sessionManager)

    val viewModel:PlaceViewModel=viewModel(
        factory = PlaceViewModelFactory(repo)
    )

    val userReviewState by viewModel.userReviewState.collectAsState()

    val userEmail = "traveler.explorer@gmail.com"
    val placeName = "Santorini Caldera View"
    val placeCity = "Cyclades, Greece"

    // Mutable states for editing
    var ratingInput by remember { mutableStateOf("8.5") }
    var reviewContent by remember { mutableStateOf("Absolutely breathtaking views! The sunset lives up to the hype completely. The crowds can get a bit intense near Oia, but finding a quiet spot along the caldera path makes it worth every moment. Highly recommend visiting in late September.") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Edit Your Review",
                        color = TravelTextPrimary,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                },
                navigationIcon = {
                    IconButton(onClick = {  }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = TravelTextPrimary
                        )
                    }
                },
                actions = {
                    IconButton(onClick = {  }) {
                        Icon(
                            imageVector = Icons.Filled.Delete,
                            contentDescription = "Delete Review",
                            tint = TravelError
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = TravelBackground)
            )
        },
        containerColor = TravelBackground
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 24.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {

            // --- User Identity Section ---
            Column(modifier = Modifier.padding(top = 8.dp)) {
                Text(
                    text = "Reviewing as",
                    style = MaterialTheme.typography.labelMedium,
                    color = TravelTextSecondary
                )
                Text(
                    text = userEmail,
                    style = MaterialTheme.typography.bodyMedium,
                    color = TravelTextPrimary,
                    fontWeight = FontWeight.Medium
                )
            }

            // --- Destination Place Card ---
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = TravelSurface),
                shape = RoundedCornerShape(16.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = placeName,
                            style = MaterialTheme.typography.titleLarge,
                            color = TravelTextPrimary,
                            fontWeight = FontWeight.Bold,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Filled.LocationOn,
                                contentDescription = "Location",
                                tint = TravelPrimary,
                                modifier = Modifier.size(16.dp)
                            )
                            Text(
                                text = placeCity,
                                style = MaterialTheme.typography.bodyMedium,
                                color = TravelTextSecondary
                            )
                        }
                    }
                }
            }

            // --- Rating Input Section (Out of 10) ---
            Column {
                Text(
                    text = "Your Rating (Out of 10)",
                    style = MaterialTheme.typography.titleMedium,
                    color = TravelTextPrimary,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                OutlinedTextField(
                    value = ratingInput,
                    onValueChange = { input ->
                        // Basic filtering to allow decimals up to 10
                        if (input.isEmpty() || input.toDoubleOrNull() != null && input.toDouble() <= 10.0) {
                            ratingInput = input
                        }
                    },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Filled.Star,
                            contentDescription = "Rating Star",
                            tint = TravelRatingGold
                        )
                    },
                    trailingIcon = {
                        Text(
                            text = "/ 10",
                            style = MaterialTheme.typography.bodyMedium,
                            color = TravelTextSecondary,
                            modifier = Modifier.padding(end = 12.dp)
                        )
                    },
                    modifier = Modifier.width(140.dp),
                    singleLine = true,
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = TravelTextPrimary,
                        unfocusedTextColor = TravelTextPrimary,
                        focusedBorderColor = TravelTextPrimary,
                        unfocusedBorderColor = Color.LightGray,
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent
                    )
                )
            }

            // --- Review Content Edit Section ---
            Column {
                Text(
                    text = "Edit your experience",
                    style = MaterialTheme.typography.titleMedium,
                    color = TravelTextPrimary,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                OutlinedTextField(
                    value = reviewContent,
                    onValueChange = { reviewContent = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(min = 160.dp, max = 280.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = TravelTextPrimary,
                        unfocusedTextColor = TravelTextPrimary,
                        focusedBorderColor = TravelTextPrimary,
                        unfocusedBorderColor = Color.LightGray,
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent
                    ),
                    placeholder = {
                        Text(
                            text = "Describe your time here, tips for other travelers...",
                            color = TravelTextSecondary
                        )
                    }
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            // --- Save Changes Button ---
            Button(
                onClick = {
                    val finalRating = ratingInput.toDoubleOrNull() ?: 0.0
                    //onSaveClick(finalRating, reviewContent)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp)
                    .padding(bottom = 8.dp),
                colors = ButtonDefaults.buttonColors(containerColor = TravelPrimary),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(
                    text = "Save Changes",
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }


}