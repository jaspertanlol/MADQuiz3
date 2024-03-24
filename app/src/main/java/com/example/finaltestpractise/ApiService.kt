package com.example.finaltestpractise

import retrofit2.Call
import retrofit2.http.GET

// This interface defines the possible HTTP operations and endpoints for your Retrofit service.
interface ApiService {

    // This annotation tells Retrofit that this method is a GET request.
    // The parameter passed to the @GET annotation specifies the endpoint's relative URL.
    // In this case, "comments" is appended to the base URL defined in the Retrofit instance.
    @GET("comments")

    // Defines a function named getComments. This function corresponds to the "comments" endpoint
    // and tells Retrofit to execute a GET request to this endpoint.
    // The function returns a Call object, which represents the HTTP request and response.
    // The generic type <List<Comment>> indicates the expected type of the response body.
    // This tells Retrofit and the Gson converter how to parse the JSON response into Kotlin objects.
    // Here, it expects the JSON response to be an array of comment objects, which it will parse into a List of Comment Kotlin objects.
    fun getComments(): Call<List<Comment>> // Return a list of comments
}
