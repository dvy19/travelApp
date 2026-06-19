package com.example.travelguide.city

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
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

    var searchText by remember {
        mutableStateOf("")
    }

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

                modifier = Modifier.fillMaxSize()
                    .padding(24.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ){

                item {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {

                        OutlinedTextField(
                            value = searchText,
                            onValueChange = {
                                searchText = it
                            },
                            modifier = Modifier.weight(1f),
                            placeholder = {
                                Text("Search places")
                            },
                            singleLine = true
                        )

                        Button(
                            onClick = {
                                viewModel.fetchPlaceByCity(cityId!!, searchText)
                            }
                        ) {
                            Text("Search")
                        }
                    }
                }


                items(
                    items = places,
                    key = { it.id }
                ) { place ->

                    PlaceCardItem(
                        place = place,
                        onClick = {
                            mainNavController.navigate(
                                "placeDetail/${place.id}"
                            )
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





