package com.example.travelguide.user.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.travelguide.SessionManager
import com.example.travelguide.place.GetUserReviewsState
import com.example.travelguide.place.PlaceRepository
import com.example.travelguide.place.PlaceViewModel
import com.example.travelguide.place.PlaceViewModelFactory
import com.example.travelguide.place.ReviewCard
import kotlinx.coroutines.launch

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


    val userReviewState by viewModel.userReviewState.collectAsState()


    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Reviews Added by You")
                },

                actions = {
                    IconButton(
                        onClick = {

                        }
                    ) {
                        Icon(
                            Icons.Default.Message,
                            contentDescription = null
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {}
            ) {
                Icon(
                    Icons.Default.Add,
                    contentDescription = null
                )
            }
        }
    ) { innerPadding ->

        LazyColumn(
            modifier = Modifier.fillMaxSize()
                .padding(
                    innerPadding
                ),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            // Featured Cities Section
            item {

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
                            contentPadding = PaddingValues(horizontal = 16.dp)
                        ) {
                            items(reviews) {
                                ReviewCard(it)
                            }
                        }
                    }

                    is GetUserReviewsState.Error->{
                        Text("Failed")
                    }




                }

            }


        }

    }


}

