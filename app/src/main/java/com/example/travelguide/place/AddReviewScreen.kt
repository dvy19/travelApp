package com.example.travelguide.place

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.travelguide.SessionManager

// Shared Travel Light Palette
val TravelBgLight = Color(0xFFFAFAFA)
val CardSurfaceLight = Color(0xFFF3F4F6)
val TextPrimary = Color(0xFF1F2937)
val TextSecondary = Color(0xFF6B7280)
val AccentTeal = Color(0xFF0F766E)
val AmberRating = Color(0xFFF59E0B) // Premium warm amber for active stars

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddReviewScreen(
    placeId:Int?,
    mainNavController: NavController,
    modifier: Modifier = Modifier
) {
    var rating by remember { mutableStateOf(0) }
    var reviewText by remember { mutableStateOf("") }
    val maxCharLimit = 500

    val context=LocalContext.current
    val sessionManager= SessionManager(context)
    val repo=PlaceRepository(sessionManager)

    val viewModel:PlaceViewModel=viewModel(
        factory = PlaceViewModelFactory(repo)
    )


    LaunchedEffect(Unit){

        placeId?.let {
            viewModel.fetchSinglePlace(it)
        }
    }

    val getSinglePlaceState by viewModel.getSinglePlaceState.collectAsState()

    val reviewState by viewModel.reviewState.collectAsState()

    when(val state=getSinglePlaceState){

        is GetSinglePlaceState.Idle->{

        }

        is GetSinglePlaceState.Loading->{

            Text("loading")
        }

        is GetSinglePlaceState.Success->{

            val place=state.place

            Scaffold(
                topBar = {
                    TopAppBar(
                        title = {
                            Text(
                                text = "Write a Review",
                                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                                color = TextPrimary
                            )
                        },
                        navigationIcon = {
                            IconButton(onClick = {  }) {
                                Icon(
                                    imageVector = Icons.Default.ArrowBack,
                                    contentDescription = "Back",
                                    tint = TextPrimary
                                )
                            }
                        },
                        colors = TopAppBarDefaults.topAppBarColors(containerColor = TravelBgLight)
                    )
                },
                containerColor = TravelBgLight,
                modifier = modifier.fillMaxSize()
            ) { innerPadding ->
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                        .padding(horizontal = 24.dp)
                        .verticalScroll(rememberScrollState()),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(modifier = Modifier.height(16.dp))

                    // Context Header
                    Text(
                        text = "How was your experience in",
                        style = MaterialTheme.typography.bodyLarge,
                        color = TextSecondary,
                        textAlign = TextAlign.Center
                    )
                    Text(
                        text = place.name,
                        style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                        color = TextPrimary,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(top = 4.dp, bottom = 24.dp)
                    )

                    // 1. Interactive Star Rating Picker
                    Text(
                        text = "Tap to Rate",
                        style = MaterialTheme.typography.labelMedium.copy(
                            fontWeight = FontWeight.SemiBold,
                            letterSpacing = 0.5.sp
                        ),
                        color = TextSecondary
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    InteractiveStarRatingBar(
                        rating = rating,
                        onRatingChanged = { rating = it },
                        modifier = Modifier.padding(bottom = 32.dp)
                    )

                    // 2. Review Content Input Field
                    Text(
                        text = "Share your thoughts",
                        style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                        color = TextPrimary,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    OutlinedTextField(
                        value = reviewText,
                        onValueChange = { if (it.length <= maxCharLimit) reviewText = it },
                        placeholder = {
                            Text(
                                text = "Describe the local food, places you visited, or cultural highlights...",
                                style = MaterialTheme.typography.bodyMedium,
                                color = TextSecondary.copy(alpha = 0.7f)
                            )
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(180.dp),
                        shape = RoundedCornerShape(16.dp),
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = CardSurfaceLight,
                            unfocusedContainerColor = CardSurfaceLight,
                            disabledContainerColor = CardSurfaceLight,
                            focusedIndicatorColor = AccentTeal,
                            unfocusedIndicatorColor = Color.Transparent,
                            cursorColor = AccentTeal
                        ),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Text,
                            imeAction = ImeAction.Done
                        )
                    )

                    // Character Counter Row
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 6.dp, start = 4.dp, end = 4.dp),
                        horizontalArrangement = Arrangement.End
                    ) {
                        Text(
                            text = "${reviewText.length} / $maxCharLimit",
                            style = MaterialTheme.typography.bodySmall,
                            color = if (reviewText.length == maxCharLimit) Color.Red else TextSecondary
                        )
                    }

                    Spacer(modifier = Modifier.weight(1f))
                    Spacer(modifier = Modifier.height(32.dp))

                    // 3. Modern Sticky Submit Button
                    Button(
                        onClick = {
                            val request=ReviewRequest(
                                place=place.id,
                                rating=rating,
                                content=reviewText
                            )

                            viewModel.createReview(request)
                        },
                        enabled = rating > 0 && reviewText.isNotBlank(),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(54.dp)
                            .padding(bottom = 8.dp),
                        shape = RoundedCornerShape(16.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = AccentTeal,
                            contentColor = Color.White,
                            disabledContainerColor = CardSurfaceLight,
                            disabledContentColor = TextSecondary.copy(alpha = 0.5f)
                        ),
                        elevation = ButtonDefaults.buttonElevation(defaultElevation = 0.dp)
                    ) {
                        Text(
                            text = "Submit Review",
                            style = MaterialTheme.typography.titleSmall.copy(
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 0.3.sp
                            )
                        )
                    }
                }
            }

            LaunchedEffect(reviewState) {

                when(reviewState) {

                    is CreateReviewState.Success -> {

                        Log.d("m", "success create review")

                        mainNavController.navigate(
                            "placeDetail/${place.id}"
                        )
                    }

                    is CreateReviewState.Error -> {

                        Log.d("m", "review creation failed")
                    }

                    else -> {}
                }
            }
        }


        is GetSinglePlaceState.Error->{
            Text(text = "Failed")
        }
    }

}

@Composable
fun InteractiveStarRatingBar(
    rating: Int,
    onRatingChanged: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        for (i in 1..5) {
            val isSelected = i <= rating
            Icon(
                imageVector = if (isSelected) Icons.Filled.Star else Icons.Outlined.Star,
                contentDescription = "Rate $i Stars",
                tint = if (isSelected) AmberRating else TextSecondary.copy(alpha = 0.4f),
                modifier = Modifier
                    .size(40.dp)
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null // Disables ugly blocky touch ripple to keep it elegant
                    ) {
                        onRatingChanged(i)
                    }
            )
        }
    }
}