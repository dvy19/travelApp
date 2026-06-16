package com.example.travelguide.city

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider


class CityViewModelFactory(
    private val repository: CityRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CityViewModel::class.java)) {
            return CityViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}