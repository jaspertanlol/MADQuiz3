package com.example.finaltestpractise

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PrefViewModel(application: Application) : AndroidViewModel(application) {

    // Create an instance of PreferencesManager.
    // This preferencesManager will be used to interact with the DataStore.
    private val preferencesManager = PreferencesManager(application)

    // This line initializes a MutableStateFlow with an initial value of false.
    // MutableStateFlow is a state holder observable flow that emits the current and new state updates to its collectors.
    // The _toggleState variable is private because it should only be modified within the ViewModel.
    private val _toggleState = MutableStateFlow(false)

    // This exposes _toggleState to the outside world, so other components can read
    val toggleState: StateFlow<Boolean> = _toggleState

    // This block of code is executed when the ViewModel is created.
    init {
        // Collect the flow returned by preferencesManager.getToggleState()
        viewModelScope.launch {
            preferencesManager.getToggleState().collect { value ->
                _toggleState.value = value
            }
        }
    }

    // This function is called when the user toggles the switch in the UI to update the toggle state.
    fun updateToggleState(newState: Boolean) {
        viewModelScope.launch {
            preferencesManager.saveToggleState(newState)
        }
    }

}