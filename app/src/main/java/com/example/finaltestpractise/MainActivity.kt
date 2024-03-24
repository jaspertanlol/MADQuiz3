package com.example.finaltestpractise

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

class MainActivity : ComponentActivity() {

    // Create an instance of the PrefViewModel
    private val prefViewModel: PrefViewModel by viewModels()
    // Create an instance of the CommentViewModel
    private val commentViewModel: CommentViewModel by viewModels()

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
                composable("ForegroundScreen") {
                    ForegroundScreen(this@MainActivity)
                }
                composable("CommentScreen") {
                    CommentScreen(commentViewModel)
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

        // Navigate to the ForegroundScreen
        Button(onClick = {
            navController.navigate("ForegroundScreen")
        }, modifier = Modifier.padding(top = 8.dp)) {
            Text("Foreground Screen")
        }
        // Navigate to the CommentScreen
        Button(onClick = {
            navController.navigate("CommentScreen")
        }, modifier = Modifier.padding(top = 8.dp)) {
            Text("Comment Screen")
        }
    }
}

@Composable
fun ForegroundScreen(context: Context) {
    Column(modifier = Modifier.padding(16.dp)) {
        Button(onClick = {
            context.startForegroundService(Intent(context, CounterForegroundService::class.java))
        }) {
            Text("Start Foreground Service")
        }
        Button(onClick = {
            context.stopService(Intent(context, CounterForegroundService::class.java))
        }, modifier = Modifier.padding(top = 8.dp)) {
            Text("Stop Foreground Service")
        }
    }
}

@Composable
fun CommentScreen(viewModel: CommentViewModel) {
    val comments by viewModel.comments.observeAsState(initial = emptyList())

    LazyColumn {
        items(comments) { comment ->
            CommentItem(comment)
        }
    }
}

@Composable
fun CommentItem(comment: com.example.finaltestpractise.Comment) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = "Name: ${comment.name}", fontWeight = FontWeight.Bold)
        Text(text = "Email: ${comment.email}")
        Text(text = "Body: ${comment.body}")
    }
}

