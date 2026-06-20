package com.example.travelguide



import androidx.compose.foundation.layout.padding
import androidx.navigation.compose.rememberNavController
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.travelguide.Screens.CityDetailScreen
import com.example.travelguide.Screens.HomeScreen
import com.example.travelguide.city.CityDetailScreen
import com.example.travelguide.city.GetPlaceByCityScreen
import com.example.travelguide.place.AddReviewScreen
import com.example.travelguide.place.PlaceDetailScreen
import com.example.travelguide.user.screens.ExploreScreen
import com.example.travelguide.user.screens.FamousPlacesScreen
import com.example.travelguide.user.screens.HomeScreen


@Composable
fun MainScreen(rootNavController: NavController) {

    val mainNavController = rememberNavController()

    Scaffold(
        bottomBar = {
            BottomNavBar(mainNavController)
        }
    ) { paddingValues ->

        NavHost(
            navController = mainNavController,
            startDestination = Screens.HomeScreen.route,
            modifier = Modifier.padding(paddingValues)
        ) {

            composable(Screens.HomeScreen.route) {
                HomeScreen(
                    mainNavController
                    /*
                    onAddPostClick = {  mainNavController.navigate(Screens.CreatePost.route)},
                    onNotificationClick = { },
                    onMessageClick = {  }

                             */
                )
            }

            /*
            composable(Screens.ProfileScreen.route){
                ProfileScreen(
                    mainNavController,
                    rootNavController

                )
            }

            composable(Screens.CreatePost.route){
                CreatePostScreen(mainNavController)
            }

            composable(Screens.SearchScreen.route){
                SearchScreen(mainNavController)
            }

            composable(Screens.FriendProfile.route){
                    backStackEntry ->

                val id =
                    backStackEntry.arguments
                        ?.getString("id")
                        ?.toIntOrNull()

                FriendProfile(
                    userId = id,
                    mainNavController=mainNavController
                )
            }

        }

             */

            composable(Screens.ExploreScreen.route){
                ExploreScreen(mainNavController)
            }

            composable(Screens.FamousPlaceScreen.route){
                FamousPlacesScreen(mainNavController)
            }



            composable(
                route = Screens.CityDetailScreen.route,
                arguments = listOf(
                    navArgument("id") {
                        type = NavType.IntType
                    }
                )
            ) { backStackEntry ->

                val id = backStackEntry.arguments?.getInt("id")

                CityDetailScreen(
                    mainNavController = mainNavController,
                    cityId = id,
                    modifier = Modifier
                )
            }

            composable(
                route = Screens.AddReviewScreen.route,
                arguments = listOf(
                    navArgument("id") {
                        type = NavType.IntType
                    }
                )
            ) { backStackEntry ->

                val id = backStackEntry.arguments?.getInt("id")

                AddReviewScreen(
                    mainNavController = mainNavController,
                    placeId = id,
                    modifier = Modifier
                )
            }


            composable(
                route = Screens.GetPlaceByCityScreen.route,
                arguments = listOf(
                    navArgument("id") {
                        type = NavType.IntType
                    }
                )
            ) { backStackEntry ->

                val id = backStackEntry.arguments?.getInt("id")

                GetPlaceByCityScreen(
                    mainNavController = mainNavController,
                    cityId = id,
                )
            }

            composable(
                route = Screens.PlaceDetailScreen.route,
                arguments = listOf(
                    navArgument("id") {
                        type = NavType.IntType
                    }
                )
            ) { backStackEntry ->

                val id = backStackEntry.arguments?.getInt("id")

                PlaceDetailScreen(
                    mainNavController = mainNavController,
                    placeId = id,
                    modifier = Modifier
                )
            }
        }
    }
}