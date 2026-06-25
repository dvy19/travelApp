package com.example.travelguide.place

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


sealed class SingleReviewState{
    object Idle: SingleReviewState()
    object Loading: SingleReviewState()
    data class Success( val review: ReviewData) : SingleReviewState()
    data class Error( val message:String) : SingleReviewState()
}

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

sealed class CreateReviewState{

    object Idle: CreateReviewState()
    object Loading: CreateReviewState()
    data class Success( val  review: ReviewData): CreateReviewState()
    data class Error(var message: String): CreateReviewState()
}

sealed class GetAllReviewState{
    object Idle: GetAllReviewState()
    object Loading: GetAllReviewState()
    data class Success(val reviews: List<ReviewData>): GetAllReviewState()
    data class Error(val message: String): GetAllReviewState()
}

sealed class UpdateReviewState {
    object Idle:UpdateReviewState()
    object  Loading:UpdateReviewState()
    data class Success( val review: ReviewUpdateResponse) : UpdateReviewState()
    data class Error(val message:String): UpdateReviewState()
}

sealed class  GetFamousPlaceState{
    object Idle: GetFamousPlaceState()
    object Loading: GetFamousPlaceState()
    data class Success(val places: List<FamousPlace>): GetFamousPlaceState()
    data class Error(val message: String): GetFamousPlaceState()
}

sealed class GetUserReviewsState{
    object Idle: GetUserReviewsState()
    object Loading: GetUserReviewsState()
    data class Success(val reviews: List<ReviewData>): GetUserReviewsState()
    data class Error(val message: String): GetUserReviewsState()
}

class PlaceViewModel(
    private val repository: PlaceRepository
): ViewModel(){

    private val _singleReviewState= MutableStateFlow<SingleReviewState>(SingleReviewState.Idle)
    val singleReviewState: StateFlow<SingleReviewState> = _singleReviewState.asStateFlow()

    private val _updateReviewState = MutableStateFlow<UpdateReviewState>(UpdateReviewState.Idle)
    val updateReviewState: StateFlow<UpdateReviewState> = _updateReviewState.asStateFlow()

    private val _userReviewState = MutableStateFlow<GetUserReviewsState>(GetUserReviewsState.Idle)
    val userReviewState: StateFlow<GetUserReviewsState> = _userReviewState.asStateFlow()

    private val _getFamousPlaceState = MutableStateFlow<GetFamousPlaceState>(GetFamousPlaceState.Idle)
    val getFamousPlaceState: StateFlow<GetFamousPlaceState> = _getFamousPlaceState.asStateFlow()

    private val _getPlaceState = MutableStateFlow<GetAllPlaceState>(GetAllPlaceState.Idle)
    val getPlaceState: StateFlow<GetAllPlaceState> = _getPlaceState.asStateFlow()

    private val _getSinglePlaceState = MutableStateFlow<GetSinglePlaceState>(GetSinglePlaceState.Idle)
    val getSinglePlaceState: StateFlow<GetSinglePlaceState> = _getSinglePlaceState.asStateFlow()

    private val _reviewState = MutableStateFlow<CreateReviewState>(CreateReviewState.Idle)
    val reviewState: StateFlow<CreateReviewState> = _reviewState.asStateFlow()

    private val _getAllReviewState = MutableStateFlow<GetAllReviewState>(GetAllReviewState.Idle)
    val getAllReviewState: StateFlow<GetAllReviewState> = _getAllReviewState.asStateFlow()


    fun get_single_review(
        id:Int
    ) {

        viewModelScope.launch{

            _singleReviewState.value= SingleReviewState.Idle

            try{

                val response=repository.fetchSingleReview(id)

                if( response.isSuccessful){

                    _singleReviewState.value= SingleReviewState.Success(
                        response.body()!!.data
                    )
                }
                else{
                    _singleReviewState.value= SingleReviewState.Error(
                        "failed"
                    )

                }
            }
            catch(e: Exception) {
                _singleReviewState.value = SingleReviewState.Error(
                    e.message ?: "Unknown Error"
                )
            }
        }

    }


    fun updateReview(
        reviewId:Int,
        rating:Int,
        content:String
    ){

        viewModelScope.launch{

            _updateReviewState.value= UpdateReviewState.Loading

            try{

                val response=repository.editReview(
                    reviewId,
                    rating,
                    content
                )

                if (response.isSuccessful &&
                    response.body() != null
                ) {

                    _updateReviewState.value =
                        UpdateReviewState.Success(
                            response.body()!!
                        )

                }
                else{
                    _updateReviewState.value= UpdateReviewState.Error(
                        "failed"
                    )

                }
                }
            catch(e: Exception) {
                _updateReviewState.value= UpdateReviewState.Error(
                    e.message?: "Unknown Error"
                )


            }



        }
    }

    fun get_user_reviews(){

        viewModelScope.launch{

            _userReviewState.value= GetUserReviewsState.Loading


            try{
                val response= repository.getUserReview()

                if(response!=null && response.isSuccessful){

                    _userReviewState.value= GetUserReviewsState.Success(
                        response.body()!!.data
                    )

                }
                else{
                    _userReviewState.value= GetUserReviewsState.Error(
                        "failed"
                        )
                }
            }
            catch(e: Exception) {
                _userReviewState.value= GetUserReviewsState.Error(
                    e.message?: "Unknown Error"
                )
            }

        }
    }

    fun fetchFamousPlaces() {
        viewModelScope.launch {
            _getFamousPlaceState.value = GetFamousPlaceState.Loading

            try {
                val response = repository.getFamousPlaces()
                if (response.body() != null && response.isSuccessful) {
                    _getFamousPlaceState.value = GetFamousPlaceState.Success(response.body()!!)


                } else {
                    _getFamousPlaceState.value = GetFamousPlaceState.Error(
                        response.message() ?: "Failed"
                    )
                }
            } catch (e: Exception) {
                _getFamousPlaceState.value = GetFamousPlaceState.Error(
                    e.message ?: "Unknown Error"
                )
            }
        }

    }

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

    fun createReview( request: ReviewRequest){


        viewModelScope.launch{

            _reviewState.value= CreateReviewState.Idle

            try{

                val response= repository.createReview(request)

                if(response!=null && response.isSuccessful){

                    _reviewState.value= CreateReviewState.Success(
                        response.body()!!.data
                    )
                }
                else{
                    _reviewState.value= CreateReviewState.Error(
                        "failed"
                    )

                }
            }
            catch(e: Exception) {
                _reviewState.value= CreateReviewState.Error(
                    e.message?: "Unknown Error"
                )
            }



        }

    }

    fun get_all_reviews(){

        viewModelScope.launch{

            _getAllReviewState.value= GetAllReviewState.Loading

            try{

                val response= repository.getAllReviews()

                //Log.d("m",response.body().toString())
                if(response!=null && response.isSuccessful){

                    _getAllReviewState.value= GetAllReviewState.Success(
                        response.body()!!.data
                    )
                }
                else{
                    _getAllReviewState.value= GetAllReviewState.Error(
                        "failed"
                        )
                }
            }
            catch(e: Exception) {
                _getAllReviewState.value= GetAllReviewState.Error(
                    e.message?: "Unknown Error"
                )
            }

        }

    }



}