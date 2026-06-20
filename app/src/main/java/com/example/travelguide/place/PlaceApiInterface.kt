package com.example.travelguide.place

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface PlaceApiInterface{

    @GET("/api/places")
    suspend fun getFamousPlaces(
    ): Response <List<FamousPlace>>

    @GET("api/place/places/")
    suspend fun getAllPlaces(
        @Header("Authorization") token:String
    ) : Response<PlaceResponse>

    @GET("api/place/places/{id}/")
    suspend fun getSinglePlace(
        @Header("Authorization") token:String,
        @Path("id") id:Int
    ) : Response<SinglePlaceResponse>

    @POST("api/place/reviews/")
    suspend fun createReview(
        @Header("Authorization") token:String,
        @Body request: ReviewRequest
    ) : Response<ReviewResponse>

    @GET("api/place/reviews/")
    suspend fun getAllReviews(
        @Header("Authorization") token:String
    ) : Response<AllReviewsResponse>

}