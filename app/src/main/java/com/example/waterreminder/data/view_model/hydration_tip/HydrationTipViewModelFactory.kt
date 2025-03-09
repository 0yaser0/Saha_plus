package com.example.waterreminder.data.view_model.hydration_tip

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.waterreminder.data.repository.HydrationTipRepository

class HydrationTipViewModelFactory(private val repository: HydrationTipRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HydrationTipViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return HydrationTipViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
