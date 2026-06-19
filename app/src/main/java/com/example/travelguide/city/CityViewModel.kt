package com.example.travelguide.city

import android.util.Log
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.travelguide.place.GetSinglePlaceState
import com.example.travelguide.place.Place
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

sealed class GetSingleCityState{

    object Idle: GetSingleCityState()
    object Loading: GetSingleCityState()
    data class Success(val city  : City): GetSingleCityState()
    data class Error(val message:String) : GetSingleCityState()
}

sealed class GetPlaceByCityState{
    object Idle: GetPlaceByCityState()
    object Loading: GetPlaceByCityState()
    data class Success(val places: List<Place>) : GetPlaceByCityState()
    data class Error(val message: String) : GetPlaceByCityState()
}

class CityViewModel(
    private val repository: CityRepository
) : ViewModel() {

    private val _getCityState = MutableStateFlow<GetAllCityState>(
        GetAllCityState.Idle
    )

    val getCityState: StateFlow<GetAllCityState> =
        _getCityState.asStateFlow()

    private val _getSingleCityState = MutableStateFlow<GetSingleCityState>(
        GetSingleCityState.Idle
    )

    val getSingleCityState: StateFlow<GetSingleCityState> =
        _getSingleCityState.asStateFlow()

    private val _getPlaceByCityState = MutableStateFlow<GetPlaceByCityState>(
        GetPlaceByCityState.Idle
    )

    val getPlaceByCityState: StateFlow<GetPlaceByCityState> =
        _getPlaceByCityState.asStateFlow()


    fun fetchPlaceByCity(id:Int){

        viewModelScope.launch{

            _getPlaceByCityState.value= GetPlaceByCityState.Idle

            try{
                val response=repository.get_places_by_city(id)


                if(response.body()!=null && response.isSuccessful){

                    _getPlaceByCityState.value= GetPlaceByCityState.Success(
                        response.body()!!.data
                    )


                }
                else{

                    _getPlaceByCityState.value= GetPlaceByCityState.Error(
                        "failed"
                    )
                }
            }

            catch(e: Exception) {
                _getPlaceByCityState.value= GetPlaceByCityState.Error(
                    e.message?: "Unknown Error"
                )
            }


        }



    }

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

    fun fetchSingleCity(
        id:Int
    ){

        viewModelScope.launch{

            _getSingleCityState.value= GetSingleCityState.Idle

            try{

                val response=repository.get_city_by_id(id)

                if(response.body()!=null && response.isSuccessful){

                    _getSingleCityState.value= GetSingleCityState.Success(
                        response.body()!!.data
                    )
                }
                else{

                    _getSingleCityState.value= GetSingleCityState.Error(
                        "failed"
                    )
                }
            }

            catch(e: Exception) {
                _getSingleCityState.value= GetSingleCityState.Error(
                    e.message?: "Unknown Error"
                )
            }
        }
    }



}