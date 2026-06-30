package com.example.travelguide

import com.example.travelguide.auth.AuthInterface
import com.example.travelguide.city.CityInterface
import com.example.travelguide.place.PlaceApiInterface
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


object TravelApiClient {

    private const val BASE_URL = "https://travel-guide-116q.onrender.com/"

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private const  val FLASK_BASE_URL="https://travel-app-flask.onrender.com/"

    val okHttpClient = OkHttpClient.Builder()
        .connectTimeout(60, TimeUnit.SECONDS)
        .readTimeout(60, TimeUnit.SECONDS)
        .writeTimeout(60, TimeUnit.SECONDS)
        .build()

    private val flaskRetrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(FLASK_BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
     val famousPlaceApi:PlaceApiInterface by lazy{
         flaskRetrofit.create(PlaceApiInterface::class.java)
     }

    val registerApi: AuthInterface by lazy {
        retrofit.create(AuthInterface::class.java)

    }

    val getCityApi : CityInterface by lazy{
        retrofit.create(CityInterface::class.java)
    }

    val getAllPlaceApi: PlaceApiInterface by lazy{
        retrofit.create(PlaceApiInterface::class.java)
    }

    val createReviewApi: PlaceApiInterface by lazy{
        retrofit.create(PlaceApiInterface::class.java)
    }

    val getAllReviewApi: PlaceApiInterface by lazy{
        retrofit.create(PlaceApiInterface::class.java)
    }

    val getPlaceByCityApi: CityInterface by lazy{
        retrofit.create(CityInterface::class.java)
    }

    val getUserReviewApi: PlaceApiInterface by lazy{
        retrofit.create(PlaceApiInterface::class.java)
    }



}
