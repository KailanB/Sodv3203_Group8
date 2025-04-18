package com.example.skillswapapp.ui.screens

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.skillswapapp.data.AppDatabase
import com.example.skillswapapp.data.repository.UserRepositoryImpl
import com.example.skillswapapp.viewModel.LoginViewModel
import com.example.skillswapapp.viewModel.LoginViewModelFactory
import com.example.skillswapappimport.SessionViewModel

@Composable
fun LoginScreen(
    navController: NavHostController = rememberNavController(),
    sessionViewModel: SessionViewModel = viewModel() ) {

    val context = LocalContext.current
    val currentUser by sessionViewModel.currentUser.collectAsState()
    var email by remember { mutableStateOf(TextFieldValue("")) }
    var password by remember { mutableStateOf(TextFieldValue("")) }

    // Set up ViewModel with factory
    val userDao = AppDatabase.getDatabase(context).userDao()
    val userRepository = UserRepositoryImpl(userDao)
    val loginViewModel: LoginViewModel = viewModel(
        factory = LoginViewModelFactory(userRepository)
    )

    val loginState by loginViewModel.loginState.collectAsState()

    LaunchedEffect(loginState) {
        loginState?.let {
            Toast.makeText(context, "Welcome ${it.name}", Toast.LENGTH_LONG).show()
            sessionViewModel.getLoggedInUser()
            navController.navigate("HomeScreen")
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if (currentUser == null) {
            // Title
            Text(text = "Login", style = MaterialTheme.typography.headlineMedium)

            Spacer(modifier = Modifier.height(16.dp))

            // Email input field
            TextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") },
                modifier = Modifier.fillMaxWidth(),
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Password input field
            TextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password") },
                modifier = Modifier.fillMaxWidth(),
                visualTransformation = PasswordVisualTransformation(),
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Login Button
            Button(
                onClick = {
//                Toast.makeText(context, "Logging in...", Toast.LENGTH_SHORT).show()
                    loginViewModel.login(email.text, password.text)
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Login")
            }

            Spacer(modifier = Modifier.height(16.dp))

            TextButton(onClick = {
                // Navigate to sign up screen
                Toast.makeText(context, "Navigating to sign up page", Toast.LENGTH_SHORT).show()
            }) {
                Text(text = "Don't have an account? Sign up")
            }
        } else {
            // Logged in UI
            Text("Hello, ${currentUser?.user?.name}")
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = {
                    sessionViewModel.logout()
                    Toast.makeText(context, "Logged out", Toast.LENGTH_SHORT).show()
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Logout")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewLoginScreen() {
    LoginScreen(navController = rememberNavController())
}