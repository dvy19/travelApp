package com.example.travelguide.place

import com.example.travelguide.SessionManager
import com.example.travelguide.TravelApiClient
import retrofit2.Response


class PlaceRepository(
    private val sessionManager: SessionManager
) {

    private val apiService= TravelApiClient.getAllPlaceApi


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


}

