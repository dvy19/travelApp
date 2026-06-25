package com.example.travelguide.place

import com.example.travelguide.SessionManager
import com.example.travelguide.TravelApiClient
import retrofit2.Response


class PlaceRepository(
    private val sessionManager: SessionManager
) {

    private val apiService= TravelApiClient.getAllPlaceApi
    private val reviewApiService= TravelApiClient.createReviewApi
    private val famousPlaceApiService= TravelApiClient.famousPlaceApi

    suspend fun fetchSingleReview(id:Int) : Response<SingleReviewResponse>{

        val token=sessionManager.getAuthToken()

        return reviewApiService.getSingleReview(
            "Bearer $token",
            id
        )
    }

    suspend fun getFamousPlaces() : Response<List<FamousPlace>>{

        return famousPlaceApiService.getFamousPlaces()

    }


    suspend fun getUserReview() : Response<UserReviewsResponse>{
        val token=sessionManager.getAuthToken()

        return reviewApiService.getUserReview(
            "Bearer $token"
        )
    }

    suspend fun getAllPlaces(): Response<PlaceResponse> {

        val token= sessionManager.getAuthToken()

        return apiService.getAllPlaces(
            "Bearer $token"
        )
    }

    suspend fun getSinglePlace(id:Int) : Response<SinglePlaceResponse>{

        val token=sessionManager.getAuthToken()

        return apiService.getSinglePlace(
           "Bearer $token", id
        )

    }

    suspend fun createReview( request: ReviewRequest) : Response<ReviewResponse>{

        val token=sessionManager.getAuthToken()

        return reviewApiService.createReview(
            "Bearer $token",
            request
        )
    }


    suspend fun getAllReviews() : Response<AllReviewsResponse>{

        val token=sessionManager.getAuthToken()

        return reviewApiService.getAllReviews(
            "Bearer $token"
        )
    }

    suspend fun editReview(
        id:Int,
        rating:Int,
        content: String
    ): Response<ReviewUpdateResponse> {

        val body = mapOf(
            "rating" to rating,
            "content" to content
        )

        return reviewApiService.updateReview(id, body)
    }

}




