package com.example.travelguide.place

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed class GetAllPlaceState{

    object Idle: GetAllPlaceState()
    object Loading: GetAllPlaceState()
    data class Success(val places: List<Place>): GetAllPlaceState()
    data class Error(val message: String): GetAllPlaceState()
}

sealed class GetSinglePlaceState{

    object Idle: GetSinglePlaceState()
    object Loading: GetSinglePlaceState()
    data class Success(val place: SinglePlaceData): GetSinglePlaceState()
    data class Error(val message: String): GetSinglePlaceState()
}


class PlaceViewModel(
    private val repository: PlaceRepository
): ViewModel(){

    private val _getPlaceState = MutableStateFlow<GetAllPlaceState>(GetAllPlaceState.Idle)
    val getPlaceState: StateFlow<GetAllPlaceState> = _getPlaceState.asStateFlow()

    private val _getSinglePlaceState = MutableStateFlow<GetSinglePlaceState>(GetSinglePlaceState.Idle)
    val getSinglePlaceState: StateFlow<GetSinglePlaceState> = _getSinglePlaceState.asStateFlow()

    fun fetchAllPlaces() {

        viewModelScope.launch {

            _getPlaceState.value = GetAllPlaceState.Loading

            try {
                val response = repository.getAllPlaces()

                if (response.body() != null && response.isSuccessful) {

                    _getPlaceState.value = GetAllPlaceState.Success(response.body()!!.data)
                } else {

                    _getPlaceState.value = GetAllPlaceState.Error(
                        response.message() ?: "Failed"
                    )
                }
            }
            catch (e: Exception) {

                Log.d("m", getPlaceState.value.toString())


                _getPlaceState
                    .value =
                    GetAllPlaceState.Error(
                        e.message
                            ?: "Unknown Error"
                    )
            }
        }

    }

    fun fetchSinglePlace (
        id:Int
    ) {

        viewModelScope.launch{

            _getSinglePlaceState.value=GetSinglePlaceState.Loading


            try {
                val response = repository.getSinglePlace(id)

                if (response.body() != null && response.isSuccessful) {

                    _getSinglePlaceState.value = GetSinglePlaceState.Success(response.body()!!.data)

                }
                else {
                    _getSinglePlaceState.value = GetSinglePlaceState.Error(
                        response.message() ?: "Failed"
                    )
                }

                }
            catch(e: Exception) {
                _getSinglePlaceState.value=GetSinglePlaceState.Error(
                    e.message?: "Unknown Error"
                )
            }
        }


    }

}