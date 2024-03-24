package com.example.finaltestpractise

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import retrofit2.Call
import retrofit2.Response

class CommentViewModel : ViewModel() {

    // The _toggleState variable is private because it should only be modified within the ViewModel.
    private val _comments = MutableLiveData<List<Comment>>()
    // This exposes _toggleState to the outside world, so other components can read
    val comments: LiveData<List<Comment>> = _comments

    init { // Run this code when the ViewModel is created
        fetchComments()
    }

    // Defines a private function named fetchComments. This function is responsible for making an asynchronous HTTP request
    // to fetch comments using the Retrofit library.
    private fun fetchComments() {

        // Calls the getComments() method on the ApiService instance obtained from the RetrofitClient object. This method is
        // an asynchronous call to the comments endpoint of the API.
        // The enqueue method schedules the call to be executed on a background thread and provides a callback for the response or failure.
        RetrofitClient.instance.getComments().enqueue(object : retrofit2.Callback<List<Comment>> {

            // This method is called when the HTTP response is successfully received from the server.
            // The call object represents the request that generated this response, and the response object includes both the
            // data returned from the server and the status information of the response.
            override fun onResponse(call: Call<List<Comment>>, response: Response<List<Comment>>) {

                // Checks if the response from the API is successful, which means the HTTP status code is in the range [200..300).
                if (response.isSuccessful) {

                    // If the response is successful, the body of the response (which is expected to be a List<Comment>) is
                    // assigned to the LiveData _comments. This LiveData can then be observed by the UI to display the comments.
                    _comments.value = response.body()
                }
            }

            // This method is called when the request fails to execute or a non-2XX response is received.
            // The call object represents the failed request, and the Throwable object represents the reason for the failure.
            override fun onFailure(call: Call<List<Comment>>, t: Throwable) {
                // Here you would handle the failure, for example by logging the error or updating the UI to show an error message.
            }
        })
    }

}