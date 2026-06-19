package com.example.travelguide


import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.travelguide.auth.LoginScreen
import com.example.travelguide.auth.SignupScreen


@Composable
fun RootNav(innerPadding: PaddingValues) {
    val rootNavController = rememberNavController()

    NavHost(
        navController = rootNavController,
        startDestination = "splash"
    ) {



        composable("splash"){
            SplashScreen(rootNavController)
        }



        composable(Screens.LoginScreen.route) {
            LoginScreen(rootNavController)
        }


        composable("signup") {
            SignupScreen(rootNavController)
        }

        composable(Screens.MainScreen.route){
            MainScreen(rootNavController)
        }

        /*
        composable(Screens.UserDetailScreen.route){
            UserDetailScreen(rootNavController)
        }



         */

    }
}