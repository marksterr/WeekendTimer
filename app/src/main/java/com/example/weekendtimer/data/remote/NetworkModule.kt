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

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    private const val VERSION = "/api/json/v1/1/"
    private const val BASE_URL = "https://www.themealdb.com$VERSION"

    private val mediaType = "application/json".toMediaType()

    @OptIn(ExperimentalSerializationApi::class)
    private fun providesJson(): Json = Json {
        coerceInputValues = true
        ignoreUnknownKeys = true
        explicitNulls = false
    }

    @Provides
    fun providesRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(providesJson().asConverterFactory(mediaType))
            .build()
    }

    @Provides
    fun providesAPIService(retrofit: Retrofit): APIService = retrofit.create()
}