package com.example.travelguide.auth

import android.app.Application
import android.util.Log
import android.view.PixelCopy.request
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.travelguide.SessionManager
import com.example.travelguide.auth.AuthRepository
import com.example.travelguide.auth.DeviceTokenRequest
import com.example.travelguide.auth.LoginRequest
import com.example.travelguide.auth.SignupRequest
import com.example.travelguide.city.GetAllCityState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed class DeviceTokenState{
    object Idle:DeviceTokenState()
    object Loading:DeviceTokenState()
    data class Success(val message:String):DeviceTokenState()
    data class Error(val message:String):DeviceTokenState()
}

class AuthViewModel(application: Application) : AndroidViewModel(application){

    private val repository = AuthRepository()

    var registerState = mutableStateOf(false)

    var message=mutableStateOf<String>("")

    private val _deviceTokenState = MutableStateFlow<DeviceTokenState>(
        DeviceTokenState.Idle
    )

    val deviceTokenState: StateFlow<DeviceTokenState> =
        _deviceTokenState.asStateFlow()



    private val sessionManager = SessionManager(application)

    var loginState = mutableStateOf(false)

    suspend fun sendDeviceToken( request: DeviceTokenRequest){

        viewModelScope.launch {

            _deviceTokenState.value=DeviceTokenState.Loading

            try{

                val response=repository.post_device_token(request)

                if(response.isSuccessful){
                    _deviceTokenState.value=DeviceTokenState.Success(
                        "success"
                    )
                }
            }
            catch(e:Exception){
                _deviceTokenState.value=DeviceTokenState.Error(
                    e.message?: "Unknown Error"
                )
            }




        }
    }

    fun login(email: String, password: String) {
        viewModelScope.launch {

            val result = repository.loginUser(
                LoginRequest(email, password)
            )

            if (result.isSuccess) {
                val token = result.getOrNull()?.tokens?.access

                if (token != null) {

                    sessionManager.saveAuthToken(token)
                    loginState.value = true
                } else {
                    loginState.value = false
                }

            } else {
                loginState.value = false
            }
        }
    }



    fun register(email: String, password: String, role: String) {
        viewModelScope.launch {



            val result = repository.registerUser(
                SignupRequest(email, password, role)
            )


            Log.d("REGISTER", "Result = $result")
            Log.d("REGISTER", "Success = ${result.isSuccess}")
            Log.d("REGISTER", "Exception = ${result.exceptionOrNull()}")


            if (result.isSuccess) {

                val token = result.getOrNull()?.tokens?.access

                val response = result.getOrNull()

                Log.d("API_RESPONSE", response.toString())

                val accessToken = response?.tokens?.access
                val refreshToken = response?.tokens?.refresh

                Log.d("ACCESS_TOKEN", accessToken ?: "NULL")
                Log.d("REFRESH_TOKEN", refreshToken ?: "NULL")

                if (token != null) {
                    sessionManager.saveAuthToken(token)
                    sessionManager.saveRole(role)
                    message.value = "Signup Successful ✅"
                    registerState.value=true
                } else {
                    message.value = "Token missing ❌"
                    registerState.value=false
                }

            } else {

                message.value = "Error: ${result.exceptionOrNull()?.message}"
                registerState.value=false
            }
        }
    }
}