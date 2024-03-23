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
import com.example.finaltestpractise.ui.theme.FinalTestPractiseTheme

class MainActivity : ComponentActivity() {

    // Create an instance of the ViewModel
    private val prefViewModel: PrefViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FinalTestPractiseTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // Display the toggle preference
                    PreferenceToggle(prefViewModel)
                }
            }
        }
    }
}

@Composable
fun PreferenceToggle(viewModel: PrefViewModel) {
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