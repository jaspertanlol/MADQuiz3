package com.example.finaltestpractise

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.finaltestpractise.ui.theme.FinalTestPractiseTheme

class MainActivity : ComponentActivity() {

    // Create an instance of the ViewModel
    private val prefViewModel: PrefViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // Define Nav Controller
            val navController = rememberNavController()
            NavHost(
                navController = navController,
                startDestination = "HomeScreen"
            ) {
                composable("HomeScreen") {
                    PreferenceToggle(navController, prefViewModel)
                }
                composable("NextScreen") {
                }
            }
        }
    }
}

@Composable
fun PreferenceToggle(navController: NavController, viewModel: PrefViewModel) {
    Column {
        // Collect the latest toggle state from the ViewModel
        val toggleState by viewModel.toggleState.collectAsState()

        // Display the current state of the toggle
        Text(text = "Toggle is ${if (toggleState) "ON" else "OFF"}")

        // A Switch that represents the toggle, bound to the current state and updating it on change
        Switch(
            checked = toggleState,
            onCheckedChange = { newState ->
                viewModel.updateToggleState(newState)
            }
        )
    }
}