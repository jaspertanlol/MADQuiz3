package com.example.finaltestpractise

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

// Defines an object named RetrofitClient. In Kotlin, an object is a singleton,
// meaning there will only ever be one instance of this object in the application.
object RetrofitClient {

    // A private constant for the base URL of the API you're going to access.
    // This is the root endpoint of your web service.
    private const val BASE_URL = "https://jsonplaceholder.typicode.com/"

    // A public property named 'instance' that lazily initializes and provides an implementation of the ApiService interface.
    // The 'by lazy' means that the Retrofit.Builder() code block will only be executed the first time 'instance' is accessed.
    val instance: ApiService by lazy {

        // Begins building a Retrofit instance. This is the starting point for all Retrofit declarations.
        Retrofit.Builder()

            // Sets the base URL for the HTTP requests. This base URL is then combined with the endpoint paths you define in your ApiService interface.
            .baseUrl(BASE_URL)

            // Adds a converter factory for serialization and deserialization of objects.
            // Here, GsonConverterFactory is used to convert JSON to Kotlin objects and vice versa.
            .addConverterFactory(GsonConverterFactory.create())

            // Creates the Retrofit instance with the configured settings.
            .build()

            // Creates an implementation of the ApiService interface.
            // Retrofit uses the interface to generate an object that sends HTTP requests and reads their responses.
            .create(ApiService::class.java)
    }
}
