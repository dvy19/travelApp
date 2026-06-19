package com.example.travelguide


import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BlurOn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(rootNavController: NavController) {


    val context = LocalContext.current
    val sessionManager = remember { SessionManager(context) }

    // 1. Session Routing Logic Execution
    LaunchedEffect(key1 = true) {
        // Soft delay to let the splash branding breathe
        delay(1500)

        if (!sessionManager.isLoggedIn()) {
            rootNavController.navigate("main") {
                // Critical: Pops the splash screen off the backstack
                popUpTo("splash") { inclusive = true }
            }
        } else {
            rootNavController.navigate("login") {
                popUpTo("splash") { inclusive = true }
            }
        }
    }
    val CreamBackground = Color(0xFFFAF8F5) // Clean off-white surface color


    // 2. Premium Minimalist Branding Layout
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(CreamBackground), // Inherited our custom theme color
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Elegant placeholder for your app's main logo
            Icon(
                imageVector = Icons.Default.BlurOn,
                contentDescription = "App Logo",
                modifier = Modifier.size(100.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "VibeSphere", // Your app name
                style = MaterialTheme.typography.headlineLarge.copy(
                    fontWeight = FontWeight.ExtraBold,
                )
            )

            Text(
                text = "Connect beautifully",
                style = MaterialTheme.typography.bodyMedium,
            )
        }

        // Smooth modern progress indicator at the bottom edge
        CircularProgressIndicator(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 64.dp)
                .size(36.dp),
            strokeWidth = 3.dp
        )
    }
}