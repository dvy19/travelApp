package com.example.travelguide.user.screens.reviews

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Message
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.travelguide.SessionManager
import com.example.travelguide.place.GetUserReviewsState
import com.example.travelguide.place.PlaceRepository
import com.example.travelguide.place.PlaceViewModel
import com.example.travelguide.place.PlaceViewModelFactory
import com.example.travelguide.place.ReviewCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PersonalReviewScreen(
    mainNavController: NavController
){


    val context= LocalContext.current

    val sessionManager=SessionManager(context)

    val repo= PlaceRepository(sessionManager)

    val viewModel:PlaceViewModel=viewModel(
        factory = PlaceViewModelFactory(repo)
    )


    LaunchedEffect(Unit) {
        viewModel.get_user_reviews()
    }

    val userReviewState by viewModel.userReviewState.collectAsState()


    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Reviews Added by You",
                        color = Color.Black
                    )
                },


            )
        },
        contentColor = TravelBackground

    ) { innerPadding ->


            // Featured Cities Section


                when(val state=userReviewState){

                    is GetUserReviewsState.Idle->{
                        Text("Idle")

                    }

                    is GetUserReviewsState.Loading-> {
                        Text("Loading")
                    }

                    is GetUserReviewsState.Success->{

                        val reviews=state.reviews

                        LazyColumn(
                            modifier=Modifier.padding(innerPadding)
                                .fillMaxHeight()
                                .background(TravelBackground),
                            contentPadding = PaddingValues(horizontal = 12.dp),
                            verticalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            items(reviews) {
                                ReviewCard(
                                    it,
                                    onClick = {
                                        mainNavController.navigate("editReview/${it.id}")
                                    }
                                )
                            }
                        }
                    }

                    is GetUserReviewsState.Error->{
                        Text("Failed")
                    }




                }

            }


        }


@Preview
@Composable
fun PreviewPersonalReview()
{
    PersonalReviewScreen(mainNavController = rememberNavController())
}


