package com.myaxa.academycourse.data.remote.retrofit

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.myaxa.academycourse.BuildConfig
import com.myaxa.academycourse.data.remote.retrofit.interceptors.ApiKeyInterceptor
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.create

@ExperimentalSerializationApi
object RetrofitModule {
    private val json = Json {
        prettyPrint = true
        ignoreUnknownKeys = true
        coerceInputValues = true
    }

    private val httpClient = OkHttpClient().newBuilder()
        .addInterceptor(ApiKeyInterceptor())
        .build()

    private val retrofit: Retrofit = Retrofit.Builder()
        .client(httpClient)
        .baseUrl(BuildConfig.BASE_URL)
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .build()

    val moviesApi: MoviesApi = retrofit.create<MoviesApi>()
}