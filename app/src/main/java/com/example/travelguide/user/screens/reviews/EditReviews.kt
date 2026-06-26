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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import com.example.travelguide.place.GetSinglePlaceState
import com.example.travelguide.place.SingleReviewState
import com.example.travelguide.place.UpdateReviewState


val TravelBackground = Color(0xFFFBFBFB)
 val TravelPrimary = Color(0xFFE05A47) // Sunset Terracotta
 val TravelTextPrimary = Color(0xFF1A1A1A) // Deep Black/Charcoal
 val TravelTextSecondary = Color(0xFF666666)
 val TravelSurface = Color(0xFFF3F3F3)
 val TravelRatingGold = Color(0xFFFFB300)
val TravelError = Color(0xFFBA1A1A)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditReviews(
    mainNavController: NavController,
    reviewId:Int?
){

    val context= LocalContext.current

    val sessionManager=SessionManager(context)

    val repo= PlaceRepository(sessionManager)

    val viewModel:PlaceViewModel=viewModel(
        factory = PlaceViewModelFactory(repo)
    )

    LaunchedEffect(Unit){

        reviewId?.let{
            viewModel.get_single_review(reviewId)
        }
    }

    var showSuccessDialog by remember { mutableStateOf(false) }

    val singleReviewState by viewModel.singleReviewState.collectAsState()

    val updateReviewState by viewModel.updateReviewState.collectAsState()
    val singlePlaceState by viewModel.getSinglePlaceState.collectAsState()

    // Mutable states for editing
    var ratingInput by remember { mutableStateOf("") }
    var reviewContent by remember { mutableStateOf("") }

    when(val state=singleReviewState){

        is SingleReviewState.Idle->{

        }

        is SingleReviewState.Loading->{

        }

        is SingleReviewState.Success->{

            val review=state.review

            LaunchedEffect(Unit) {

                review.place?.let{
                    viewModel.fetchSinglePlace(it)
                }
            }

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
                            text = review.user_email,
                            style = MaterialTheme.typography.bodyMedium,
                            color = TravelTextPrimary,
                            fontWeight = FontWeight.Medium
                        )
                    }

                    // --- Destination Place Card ---
                  when(val  placeState=singlePlaceState){

                      is GetSinglePlaceState.Idle->{

                      }

                      is GetSinglePlaceState.Loading->{

                      }

                      is GetSinglePlaceState.Success->{

                          PlaceCard(
                              placeName = placeState.place.name,
                              placeCity = placeState.place.city_name
                          )


                  }

                      is GetSinglePlaceState.Error->{

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
                            val finalRating = ratingInput.toIntOrNull() ?: 0

                            viewModel.updateReview(reviewId?:0, finalRating,reviewContent )

                            when(val state=updateReviewState){

                                is UpdateReviewState.Idle->{

                                }

                                is UpdateReviewState.Loading->{

                                }

                                is UpdateReviewState.Success->{

                                    showSuccessDialog=true

                                }

                                is UpdateReviewState.Error->{

                                }



                            }
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

        is SingleReviewState.Error->{

        }


    }

    if (showSuccessDialog) {
        SuccessDialog(
            onDismiss = {
                showSuccessDialog = false
                // Optional: navigate back to the previous screen here
                //onBackClick()
            }
        )
    }


}

@Composable
fun PlaceCard(
    placeName:String,
    placeCity:String
){

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


}



@Composable
fun SuccessDialog(onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        containerColor = TravelSurface,
        shape = RoundedCornerShape(16.dp),
        title = {
            Text(
                text = "Success!",
                color = TravelTextPrimary,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        },
        text = {
            Text(
                text = "Review edited successfully.",
                color = TravelTextSecondary,
                fontSize = 15.sp
            )
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text(
                    text = "OK",
                    color = TravelPrimary,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
            }
        }
    )
}

@Preview
@Composable
fun EditReviewsPreview() {
    EditReviews(mainNavController = rememberNavController(), reviewId = 1)
}