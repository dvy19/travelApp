package com.example.travelguide.city

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path


interface CityInterface {

    @GET("api/city/cities/")
    suspend fun getAllCity(
        @Header("Authorization") token: String,
    ): Response<CityResponse>

    @GET("api/city/cities/{id}/")
    suspend fun getCityById(
        @Header("Authorization") token: String,
        @Path("id") id: Int
    ): Response<SingleCityResponse>

}