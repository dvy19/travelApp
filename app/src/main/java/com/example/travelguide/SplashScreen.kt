package com.example.travelguide


import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BlurOn
import androidx.compose.material.icons.outlined.Explore
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import kotlinx.coroutines.delay
// Assuming CreamBackground is defined in your theme, or use Color(0xFFFDFBF7)
val CreamBackground = Color(0xFFFDFBF7)
val TravelTeal = Color(0xFF1A5F7A) // A premium accent color for travel
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
            .background(CreamBackground),
        contentAlignment = Alignment.Center
    ) {
        // Main Branding Group
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(horizontal = 32.dp)
        ) {
            // Swapped to an Outlined Explore (Compass) icon for a premium travel feel
            Icon(
                imageVector = Icons.Outlined.Explore,
                contentDescription = "Travel Guide Logo",
                tint = TravelTeal,
                modifier = Modifier.size(88.dp) // Slightly scaled down for better minimalism
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "VIBESPHERE", // Uppercase tracking gives a premium agency vibe
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontWeight = FontWeight.Light, // Light/Medium weight feels more high-end than ExtraBold
                    letterSpacing = 6.sp,
                    color = Color(0xFF2C3E50)
                )
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Your tailored perspective on the world",
                style = MaterialTheme.typography.bodySmall.copy(
                    color = Color.Gray,
                    letterSpacing = 1.sp
                )
            )
        }

        // Minimalist Progress Indicator
        CircularProgressIndicator(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 80.dp) // Pushed slightly higher for better visual breathing room
                .size(28.dp), // Smaller stroke/size looks more intentional and premium
            color = TravelTeal,
            strokeWidth = 2.dp
        )
    }
}

@Preview
@Composable
fun PreviewSplash(){
    SplashScreen(rootNavController = NavController(LocalContext.current))
}