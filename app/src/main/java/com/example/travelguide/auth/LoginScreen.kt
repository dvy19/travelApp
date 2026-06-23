package com.example.travelguide.auth



import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController


@Composable
fun LoginScreen(
    rootNavController: NavController,
    viewModel: AuthViewModel=viewModel()
){

    var email by remember{ mutableStateOf("") }
    var password by remember{mutableStateOf("")}

    var passwordVisible by remember { mutableStateOf(false) }

    val primaryColor = Color(0xef024675) // Professional Blue
    val secondaryText = Color(0xFF64748B)

    val loginSuccess = viewModel.loginState.value

    LaunchedEffect(loginSuccess) {
        if (loginSuccess) {
            rootNavController.navigate("main") {
                popUpTo("login") {
                    inclusive = true
                }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF8FAFC)) // Very light grey background
            .padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.Start
    ) {
        Spacer(modifier = Modifier.height(64.dp))

        // Branding/Header Section
        Text(
            text = "Welcome Back",
            style = MaterialTheme.typography.headlineLarge.copy(
                fontWeight = FontWeight.Bold,
                color = primaryColor
            )
        )
        Text(
            text = "Login to continue your journey",
            style = MaterialTheme.typography.bodyMedium,
            color = secondaryText
        )

        Spacer(modifier = Modifier.height(40.dp))

        // Email Input
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email Address") },
            placeholder = { Text("name@company.com") },
            leadingIcon = { Icon(Icons.Default.Email, contentDescription = null) },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            singleLine = true,
            colors = TextFieldDefaults.colors(
                // Text colors
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black,

                // Background color (making it transparent or dark so white text is visible)
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,

                // Border/Indicator colors
                focusedIndicatorColor = primaryColor,
                unfocusedIndicatorColor = Color(0xFFE2E8F0),

                // Label and Icon colors
                focusedLabelColor = primaryColor,
                unfocusedLabelColor = Color(0xFF64748B),
                focusedLeadingIconColor = primaryColor,
                unfocusedLeadingIconColor = Color(0xFF64748B),
                focusedTrailingIconColor = primaryColor,
                unfocusedTrailingIconColor = Color(0xFF64748B),

                // Cursor color
                cursorColor = primaryColor
            )

        )

        Spacer(modifier = Modifier.height(16.dp))

        // Password Input
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null) },
            trailingIcon = {
                val image = if (passwordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff
                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(imageVector = image, contentDescription = null)
                }
            },
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            singleLine = true,
            colors = TextFieldDefaults.colors(
                // Text colors
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black,

                // Background color (making it transparent or dark so white text is visible)
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,

                // Border/Indicator colors
                focusedIndicatorColor = primaryColor,
                unfocusedIndicatorColor = Color(0xFFE2E8F0),

                // Label and Icon colors
                focusedLabelColor = primaryColor,
                unfocusedLabelColor = Color(0xFF64748B),
                focusedLeadingIconColor = primaryColor,
                unfocusedLeadingIconColor = Color(0xFF64748B),
                focusedTrailingIconColor = primaryColor,
                unfocusedTrailingIconColor = Color(0xFF64748B),

                // Cursor color
                cursorColor = primaryColor
            )
        )

        // Forgot Password Link
        TextButton(
            onClick = { /* Handle Forgot Password */ },
            modifier = Modifier.align(Alignment.End)
        ) {
            Text("Forgot Password?", color = primaryColor, fontWeight = FontWeight.SemiBold)
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Login Button
        Button(
            onClick = { viewModel.login(email, password) },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(containerColor = primaryColor, contentColor = Color.White)
        ) {
            Text("Sign In", style = MaterialTheme.typography.titleMedium, color = Color.White)
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Sign Up Footer
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("New to the portal?", color = secondaryText)
            TextButton(onClick = { rootNavController.navigate("signup") }) {
                Text("Join Now", color = primaryColor, fontWeight = FontWeight.Bold)
            }
        }



    }
}





