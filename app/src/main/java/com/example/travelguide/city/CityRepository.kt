package com.example.travelguide.city

import com.example.travelguide.SessionManager
import com.example.travelguide.TravelApiClient
import retrofit2.Response


class CityRepository(
    private val sessionManager: SessionManager
) {

    private val apiService = TravelApiClient.getCityApi

    suspend fun get_all_posts(): Response<CityResponse> {

        val token = sessionManager.getAuthToken()
            ?: throw IllegalStateException("User is not authenticated")

        return apiService.getAllCity(
            token = "Bearer $token"

        )
    }

}