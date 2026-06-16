package com.example.travelguide

import androidx.compose.material.icons.filled.Home


import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Message
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState


@Composable
fun BottomNavBar(mainNavController: NavHostController) {

    val currentRoute =
        mainNavController.currentBackStackEntryAsState().value?.destination?.route

    BottomAppBar {

        IconButton(
            onClick = {
                mainNavController.navigate(Screens.HomeScreen.route)
            },
            modifier = Modifier.weight(1f)
        ) {
            Icon(
                Icons.Default.Home,
                contentDescription = null,
                tint = if (currentRoute == Screens.HomeScreen.route)
                    Color(0xFF6C2BEE) else Color.Gray
            )
        }

        /*

        IconButton(
            onClick = {
                mainNavController.navigate(Screens.SearchScreen.route) {
                    popUpTo(mainNavController.graph.startDestinationId)
                    launchSingleTop = true
                }
            },
            modifier = Modifier.weight(1f)
        ) {
            Icon(
                Icons.Default.Search,
                contentDescription = null,
                tint = if (currentRoute == Screens.SearchScreen.route)
                    Color(0xFF6C2BEE) else Color.Gray
            )
        }
        /*


        IconButton(
            onClick = {
                mainNavController.navigate(Screens.CreateBlog.route)
            },
            modifier = Modifier.weight(1f)
        ) {
            Icon(
                Icons.Default.Message,
                contentDescription = null,
                tint = if (currentRoute == Screens.CreateBlog.route)
                    Color(0xFF6C2BEE) else Color.Gray
            )
        }


        */

        IconButton(
            onClick = {
                mainNavController.navigate(Screens.ProfileScreen.route) {
                    popUpTo(mainNavController.graph.startDestinationId)
                    launchSingleTop = true
                }
            },
            modifier = Modifier.weight(1f)
        ) {
            Icon(
                Icons.Default.Person,
                contentDescription = null,
                tint = if (currentRoute == Screens.ProfileScreen.route)
                    Color(0xFF6C2BEE) else Color.Gray
            )
        }
    }

         */


    }
}
