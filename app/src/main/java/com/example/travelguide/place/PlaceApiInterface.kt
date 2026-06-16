package com.example.travelguide.place

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface PlaceApiInterface{

    @GET("api/place/places/")
    suspend fun getAllPlaces(
        @Header("Authorization") token:String
    ) : Response<PlaceResponse>

    @GET("api/place/places/{id}/")
    suspend fun getSinglePlace(
        @Header("Authorization") token:String,
        @Path("id") id:Int
    ) : Response<SinglePlaceResponse>
}