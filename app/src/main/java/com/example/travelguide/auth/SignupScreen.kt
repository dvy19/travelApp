package com.example.travelguide.auth

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.travelguide.Screens
import com.example.travelguide.SessionManager


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignupScreen(
    rootNavController: NavController,
    viewModel: AuthViewModel = viewModel()
) {

    var isRegister=viewModel.registerState.value

    val context=LocalContext.current

    val sessionManager= SessionManager(context)

    val role=sessionManager.getRole()

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    var passwordVisible by remember { mutableStateOf(false) }
    var confirmPasswordVisible by remember { mutableStateOf(false) }


    // Dropdown state


    val primaryColor = Color(0xFF024675)
    val backgroundColor = Color(0xFFF8FAFC)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
            .verticalScroll(rememberScrollState())
            .padding(24.dp),
        horizontalAlignment = Alignment.Start
    ) {
        Spacer(modifier = Modifier.height(40.dp))

        // Header
        Text(
            text = "Create Account",
            style = MaterialTheme.typography.headlineLarge.copy(
                fontWeight = FontWeight.Bold,
                color = primaryColor
            )
        )
        Text(
            text = "Join thousands of professionals finding their next move.",
            style = MaterialTheme.typography.bodyMedium,
            color = Color(0xFF64748B)
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Role Selection (The "Why am I here?" section)
        Text(
            text = "I am a...",
            style = MaterialTheme.typography.labelLarge,
            color = primaryColor,
            modifier = Modifier.padding(bottom = 8.dp)
        )





        Spacer(modifier = Modifier.height(20.dp))

        // Email Field
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email Address") },
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

        // Password Field
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            singleLine = true,
            leadingIcon = {
                Icon(Icons.Default.Lock, contentDescription = null, tint = Color(0xFF024675))
            },
            // The "Eye" Toggle logic
            trailingIcon = {
                val image = if (passwordVisible)
                    Icons.Filled.Visibility
                else
                    Icons.Filled.VisibilityOff

                val description = if (passwordVisible) "Hide password" else "Show password"

                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(imageVector = image, contentDescription = description)
                }
            },
            // Logic to mask/unmask text
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
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

        // Confirm Password Field (New)
        OutlinedTextField(
            value = confirmPassword,
            onValueChange = { confirmPassword = it },
            label = { Text("Confirm Password") },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            singleLine = true,
            leadingIcon = {
                Icon(Icons.Default.Lock, contentDescription = null, tint = Color(0xFF024675))
            },
            // The "Eye" Toggle logic
            trailingIcon = {
                val image = if (confirmPasswordVisible)
                    Icons.Filled.Visibility
                else
                    Icons.Filled.VisibilityOff

                val description = if (confirmPasswordVisible) "Hide password" else "Show password"

                IconButton(onClick = { confirmPasswordVisible = !confirmPasswordVisible }) {
                    Icon(imageVector = image, contentDescription = description)
                }
            },
            // Logic to mask/unmask text
            visualTransformation = if (confirmPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
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

        Spacer(modifier = Modifier.height(32.dp))

        // Register Button
        Button(
            onClick = { viewModel.register(email.trim(), password.trim(), "user")

                        Log.d("m",isRegister.toString())
                      Log.d("m" , "button clicked")

                Log.d("m",isRegister.toString())
                      },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(containerColor = primaryColor, contentColor = Color.White)
        ) {

            Text("Sign Up", style = MaterialTheme.typography.titleMedium, color = Color.White)


        }

        // Footer Navigation
        Row(
            modifier = Modifier.fillMaxWidth().padding(top = 16.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Already have an account?", color = Color(0xFF64748B))
            TextButton(onClick = {
                rootNavController.navigate("login")
            }) {
                Text("Sign In", color = primaryColor, fontWeight = FontWeight.Bold)
            }
        }
    }

    LaunchedEffect(isRegister) {
        if (isRegister) {
            rootNavController.navigate(Screens.MainScreen.route)
        }

    }






    Spacer(modifier = Modifier.height(16.dp))

}


@Preview
@Composable
fun PreviewSignUpScreen() {
    SignupScreen(rootNavController = rememberNavController())

}