package com.example.travelguide

import com.example.travelguide.auth.AuthInterface
import com.example.travelguide.city.CityInterface
import com.example.travelguide.place.PlaceApiInterface
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object TravelApiClient {

    private const val BASE_URL = "https://travel-guide-116q.onrender.com/"

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
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



}
