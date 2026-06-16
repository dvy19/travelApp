package com.example.travelguide.city

import android.util.Log
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.asStateFlow

sealed class GetAllCityState{

    object Idle: GetAllCityState()
    object Loading: GetAllCityState()
    data class Success(val cities: List<City>) : GetAllCityState()
    data class Error(val message: String) : GetAllCityState()

}

class CityViewModel(
    private val repository: CityRepository
) : ViewModel() {

    private val _getCityState = MutableStateFlow<GetAllCityState>(
        GetAllCityState.Idle
    )

    val getCityState: StateFlow<GetAllCityState> =
        _getCityState.asStateFlow()



    fun fetchAllCity(){

        viewModelScope.launch{

            _getCityState.value = GetAllCityState.Loading

            Log.d("m", getCityState.value.toString())

            try{

                // Log.d("POST_API", "Before API call")

                val response = repository.get_all_posts()


                Log.d("POST_API", "Code = ${response.code()}")
                Log.d("POST_API", "Successful = ${response.isSuccessful}")
                Log.d("POST_API", "Body = ${response.body()}")
                Log.d("POST_API", "ErrorBody = ${response.errorBody()?.string()}")

                Log.d("POST_API", "After API call")

                Log.d("m", getCityState.value.toString())



                if(response.body()!=null && response.isSuccessful){
                    Log.d("m", getCityState.value.toString())


                    _getCityState.value =
                        GetAllCityState.Success(
                            response.body()!!.data
                        )

                }

                // api error
                else {

                    Log.d("m", getCityState.value.toString())


                    _getCityState.value =
                        GetAllCityState.Error(

                            response.message()
                                ?: "Failed to fetch posts"
                        )
                }

            }

            // network/parsing error
            catch (e: Exception) {

                Log.d("m",getCityState.value.toString())


                _getCityState.value =
                    GetAllCityState.Error(
                        e.message
                            ?: "Unknown Error"
                    )
            }
        }}



}