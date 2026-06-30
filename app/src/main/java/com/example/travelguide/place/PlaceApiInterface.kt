package com.example.travelguide.place

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path

interface PlaceApiInterface{

    @GET("api/place/reviews/{id}/")
    suspend fun getSingleReview(
        @Header("Authorization") token:String,
        @Path("id") id: Int
    ) : Response<SingleReviewResponse>

    @GET("/api/place/my-reviews/")
    suspend fun getUserReview(
        @Header("Authorization") token:String
    ) : Response<UserReviewsResponse>

    @GET("api/places")
    suspend fun getFamousPlaces(
    ): Response <List<FamousPlace>>

    @GET("api/place/places/")
    suspend fun getAllPlaces(
        @Header("Authorization") token:String
    ) : Response<PlaceResponse>

    @PATCH("reviews/{id}/")
    suspend fun updateReview(
        @Path("id") id:Int,
        @Body review: Map<String, Any>
    ): Response<ReviewUpdateResponse>

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