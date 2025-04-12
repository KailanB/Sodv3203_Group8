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
import com.example.skillswapapp.data.AppDatabase
import com.example.skillswapapp.data.repository.UserRepositoryImpl
import com.example.skillswapapp.viewModel.LoginViewModel
import com.example.skillswapapp.viewModel.LoginViewModelFactory

@Composable
fun LoginScreen() {

    val context = LocalContext.current

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
            // Navigate to next screen here
        } ?: run {

        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
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
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewLoginScreen() {
    LoginScreen()
}
