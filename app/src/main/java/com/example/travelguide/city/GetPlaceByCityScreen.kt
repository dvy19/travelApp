package com.example.travelguide.city

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.travelguide.SessionManager
import com.example.travelguide.user.screens.PlaceCardItem


@Composable
fun GetPlaceByCityScreen(
    mainNavController: NavController,
    cityId:Int?
){


    val context=LocalContext.current

    val sessionManager= SessionManager(context)
    val repo=CityRepository(sessionManager)

    val viewModel: CityViewModel=viewModel(
        factory = CityViewModelFactory(repo)
    )

    LaunchedEffect(Unit) {

        cityId?.let{
            viewModel.fetchPlaceByCity(it)
        }
    }

    val getPlaceByCityState by viewModel.getPlaceByCityState.collectAsState()

    when(val state=getPlaceByCityState){

        is GetPlaceByCityState.Idle->{
            Text("Idle")
        }
        is GetPlaceByCityState.Loading-> {
            Text("Loading")
        }

        is GetPlaceByCityState.Success->{
            val places=state.places

            LazyColumn (

                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ){

                items(
                    items = places,
                    key = { it.id }
                ) { place ->

                    PlaceCardItem(
                        place = place,
                        onClick = {
                            mainNavController.navigate("placeDetail/${place.id}")

                        }
                    )

                }
            }
        }

        is GetPlaceByCityState.Error->{
            Text("Error")
        }
    }

    }





