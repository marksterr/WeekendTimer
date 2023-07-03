package com.example.weekendtimer.data.remote

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.create

// This annotation identifies this class as a Dagger module.
// A module is a class where you provide dependencies for parts of your code that cannot be easily instantiated, such as interface implementations.
@Module
// This annotation tells Dagger that the dependencies provided in this module should be created only once and shared.
@InstallIn(SingletonComponent::class)
object NetworkModule {
    // We are setting up constants for API's base URL.
    private const val VERSION = "/api/json/v1/1/"
    private const val BASE_URL = "https://www.themealdb.com$VERSION"

    // MediaType is used by Retrofit to convert our data to/from JSON.
    private val mediaType = "application/json".toMediaType()

    // Annotating with OptIn allows us to use experimental features in Kotlin serialization.
    @OptIn(ExperimentalSerializationApi::class)
    // This function provides a Json instance with some configuration. This will be used to convert our data to/from JSON.
    private fun providesJson(): Json = Json {
        // Handle nullable input values.
        coerceInputValues = true
        // Skip unexpected fields in JSON.
        ignoreUnknownKeys = true
        // Do not encode 'null' values to JSON.
        explicitNulls = false
    }

    // This function provides a Retrofit instance. Retrofit is a type-safe HTTP client for Android and Java.
    @Provides
    fun providesRetrofit(): Retrofit {
        return Retrofit.Builder()
            // Set the API base URL.
            .baseUrl(BASE_URL)
            // Add converter factory to convert our data to/from JSON.
            .addConverterFactory(providesJson().asConverterFactory(mediaType))
            .build()
    }

    // This function provides an instance of our APIService interface.
    @Provides
    fun providesAPIService(retrofit: Retrofit): APIService = retrofit.create()
}
