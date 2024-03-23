package com.example.finaltestpractise

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

// Create a DataStore Instance
val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class PreferencesManager(private val context: Context) {

    private val dataStore = context.dataStore

    // Function that returns a Flow of Boolean values. Flow is a type that can emit multiple values sequentially over time.
    fun getToggleState(): Flow<Boolean> {
        // Create a key for our Boolean value. This key is used to identify the value in the data store.
        val dataStoreKey = booleanPreferencesKey("toggle_state")
        // Access the data store and map its current state to a Flow<Boolean>. The map operation allows us to transform the data coming out of the data store.
        return dataStore.data.map { preferences ->
            // Attempt to retrieve the Boolean value using our key. If the value isn't found, return false by default.
            preferences[dataStoreKey] ?: false
        }
    }

    // A suspend function, meaning it can be paused and resumed, designed to save a Boolean state. It's suspendable because it performs disk I/O operations which should not block the main thread.
    suspend fun saveToggleState(state: Boolean) {
        // Again, create a key for our Boolean value. This ensures we're saving it under the correct identifier.
        val dataStoreKey = booleanPreferencesKey("toggle_state")
        // Open the data store for editing. The edit operation provides us with a mutable snapshot of the preferences to modify.
        dataStore.edit { preferences ->
            // Insert or update our Boolean value in the snapshot. The key identifies where the value should go, and 'state' is the value to be saved.
            preferences[dataStoreKey] = state
        }
    }
}