package com.example.saha_plus.data.view_model.water_consumption

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.saha_plus.data.repository.WaterConsumptionRepository

class WaterConsumptionViewModelFactory(private val repository: WaterConsumptionRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WaterConsumptionViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return WaterConsumptionViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
