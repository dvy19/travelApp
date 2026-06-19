package com.example.travelguide

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.travelguide.ui.theme.TravelGuideTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TravelGuideTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    RootNav(innerPadding)

                }
            }
        }
    }
}


sealed class Screens( var route:String){


    object HomeScreen :Screens("home")
    object SignupScreen:Screens("signup")
    object LoginScreen:Screens("login")
    data object MainScreen:Screens("main")

    data object PlaceDetailScreen:Screens("placeDetail/{id}")

    data object CityDetailScreen:Screens("cityDetail/{id}")
    data object AddReviewScreen:Screens("addReview/{id}")

    data object GetPlaceByCityScreen:Screens("placeByCity/{id}")

}