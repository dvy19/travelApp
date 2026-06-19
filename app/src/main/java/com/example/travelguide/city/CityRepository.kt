package com.example.travelguide.city

import com.example.travelguide.SessionManager
import com.example.travelguide.TravelApiClient
import com.example.travelguide.place.PlaceResponse
import retrofit2.Response


class CityRepository(
    private val sessionManager: SessionManager
) {

    private val apiService = TravelApiClient.getCityApi

    private val placeByCityApiService=TravelApiClient.getPlaceByCityApi

    suspend fun get_all_posts(): Response<CityResponse> {

        val token = sessionManager.getAuthToken()
            ?: throw IllegalStateException("User is not authenticated")

        return apiService.getAllCity(
            token = "Bearer $token"

        )
    }

    suspend fun get_places_by_city(id:Int , search:String ="") : Response<PlaceResponse>{

        val token=sessionManager.getAuthToken()

        return placeByCityApiService.getPlacesByCity(
            "Bearer $token", id , search
        )
    }

    suspend fun get_city_by_id(id:Int) :  Response<SingleCityResponse>{

        val token=sessionManager.getAuthToken()

        return apiService.getCityById("Bearer $token" ,id)

    }

}