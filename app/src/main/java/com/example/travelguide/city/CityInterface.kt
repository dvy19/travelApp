package com.example.travelguide.city

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header


interface CityInterface {

    @GET("api/city/cities/")
    suspend fun getAllCity(
        @Header("Authorization") token: String,
    ): Response<CityResponse>

}