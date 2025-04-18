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
import com.example.skillswapapp.viewModel.LoginViewModel
import com.example.skillswapapp.viewModel.LoginViewModelFactory
import com.example.skillswapappimport.SessionViewModel

@Composable
fun LoginScreen(
    navController: NavHostController = rememberNavController(),
    sessionViewModel: SessionViewModel
) {

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Login", style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(16.dp))

        // Email input field
        TextField(
            value = email,
            onValueChange = { newText -> email = newText },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Password input field
        TextField(
            value = password,
            onValueChange = { newText -> password = newText },
            label = { Text("Password") },
            modifier = Modifier.fillMaxWidth(),
            // visualTransformation = PasswordVisualTransformation()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Login Button
        Button(
            onClick = {
                sessionViewModel.login(email = email, password = password)
                // Add
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Login")
        }

        Spacer(modifier = Modifier.height(16.dp))

        TextButton(
            onClick = {

            }
        ) {
            Text(text = "Don't have an account? Sign up")
        }

    }
}

//@Preview(showBackground = true)
//@Composable
//fun PreviewLoginScreen() {
//    LoginScreen(navController = rememberNavController())
//}
