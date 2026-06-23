package com.example.travelguide.auth

import android.util.Log
import com.example.travelguide.TravelApiClient
import retrofit2.Response


class AuthRepository{

    suspend fun post_device_token(request: DeviceTokenRequest):Response<Unit>{

        return TravelApiClient.registerApi.postDeviceToken(request)

    }

    suspend fun registerUser(request: SignupRequest): Result<SignupResponse> {
        return try {
            val response = TravelApiClient.registerApi.registerUser(request)


            Log.d("REGISTER", "Code = ${response.code()}")
            Log.d("REGISTER", "Body = ${response.body()}")
            Log.d("REGISTER", "Error = ${response.errorBody()?.string()}")

            if (response.isSuccessful) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception("Error: ${response.code()}"))
            }

        } catch (e: Exception) {
            Result.failure(e)
        }
    }



    suspend fun loginUser(request: LoginRequest): Result<LoginResponse> {
        return try {
            val response = TravelApiClient.registerApi.loginUser(request)

            if (response.isSuccessful) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception("Error: ${response.code()}"))
            }

        } catch (e: Exception) {
            Result.failure(e)
        }
    }




}