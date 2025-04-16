package com.example.skillswapapp.view

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.res.painterResource
import androidx.compose.animation.core.animateFloatAsState
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.graphics.graphicsLayer

//import coil.compose.rememberImagePainter

//import coil.compose.AsyncImage

import com.example.skillswapapp.R


class RegisterActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CreateAccount()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateAccount(navController: NavHostController = rememberNavController()) {
    val context = LocalContext.current

//    val DarkGrayBackground = Color(0xFFD3D3D3) // Adjust as needed
//    val LightTextColor = Color(0xFFFF9800)
    // State variables to store input values
    var fullName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var profileIntro by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var province by remember { mutableStateOf("") }
    var city by remember { mutableStateOf("") }
    var neighborhood by remember { mutableStateOf("") }

    // Animation for sign-up button scale
    val scale by animateFloatAsState(
        targetValue = if (password == confirmPassword && password.isNotEmpty()) 1.1f else 1f
    )

    //var profilePictureUri by remember { mutableStateOf<Uri?>(null) }

    // Image picker
//    val imagePickerLauncher = rememberLauncherForActivityResult(
//        contract = ActivityResultContracts.GetContent()
//    ) { uri: Uri? ->
//        profilePictureUri = uri
//    }
    Scaffold(
        topBar = { /* No top bar */ }



    ) { contentPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
//                .background(DarkGrayBackground)
                .verticalScroll(rememberScrollState()) // added this to scroll the page
                .padding(16.dp)
                .padding(contentPadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Sign Up",
                style = MaterialTheme.typography.headlineMedium,
//                color = MaterialTheme.colors.primary
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(value = fullName, onValueChange = { fullName = it }, label = { Text("Full Name", ) })
            OutlinedTextField(value = email, onValueChange = { email = it }, label = { Text("Email") }, keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email))
            OutlinedTextField(value = password, onValueChange = { password = it }, label = { Text("Password") }, keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password))
            OutlinedTextField(value = confirmPassword, onValueChange = { confirmPassword = it }, label = { Text("Confirm Password") }, keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password))
            OutlinedTextField(value = profileIntro, onValueChange = { profileIntro = it }, label = { Text("Profile Intro") })
            OutlinedTextField(value = description, onValueChange = { description = it }, label = { Text("Description") })
            OutlinedTextField(value = province, onValueChange = { province = it }, label = { Text("Province") })
            OutlinedTextField(value = city, onValueChange = { city = it }, label = { Text("City") })
            OutlinedTextField(value = neighborhood, onValueChange = { neighborhood = it }, label = { Text("Neighborhood") })

            Spacer(modifier = Modifier.height(8.dp))

            // Profile picture upload
//            profilePictureUri?.let {
//                AsyncImage(
//                    model = it,
//                    contentDescription = "Profile Picture",
//                    modifier = Modifier
//                        .size(100.dp)
//                        .padding(8.dp)
//                )
//            } ?: Image(
//                painter = painterResource(id = R.drawable.ic_launcher_foreground),
//                contentDescription = "Upload Icon",
//                modifier = Modifier
//                    .size(100.dp)
//                    .padding(8.dp)
//            )
//            Button(onClick = { imagePickerLauncher.launch("image/*") }) {
//                Text("Upload Profile Picture")
//            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    if (password == confirmPassword) {
                        Toast.makeText(context, "Account Created!", Toast.LENGTH_SHORT).show()
                        // Clear all fields
                        fullName = ""
                        email = ""
                        password = ""
                        confirmPassword = ""
                        profileIntro = ""
                        description = ""
                        province = ""
                        city = ""
                        neighborhood = ""
                    } else {
                        Toast.makeText(context, "Passwords do not match!", Toast.LENGTH_SHORT).show()
                    }
                },
                modifier = Modifier.fillMaxWidth()
                    .graphicsLayer(
                        scaleX = scale,
                        scaleY = scale
                    )
            ) {
                Text("Sign Up")
            }

            Spacer(modifier = Modifier.height(8.dp))

            TextButton(onClick = { /* Navigate to Login */ }) {
                Text("Already have an account? Log in")
            }
        }
    }
}

@Composable
fun AsyncImage(model: Uri, contentDescription: String, modifier: Modifier) {
    TODO("Not yet implemented")
}


