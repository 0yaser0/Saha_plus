package com.example.waterreminder.data.view_model.hydration_tip

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.waterreminder.data.local.model.HydrationTip
import com.example.waterreminder.data.repository.HydrationTipRepository
import kotlinx.coroutines.launch

class HydrationTipViewModel(private val repository: HydrationTipRepository) : ViewModel() {

    private val _tips = MutableLiveData<List<HydrationTip>>()
    val tips: LiveData<List<HydrationTip>> get() = _tips

    fun loadTips() {
        viewModelScope.launch {
            _tips.value = repository.getAllTips()
        }
    }

    fun insertTip(tip: HydrationTip) {
        viewModelScope.launch {
            repository.insertTip(tip)
            loadTips()
        }
    }

    fun updateTip(tip: HydrationTip) {
        viewModelScope.launch {
            repository.updateTip(tip)
            loadTips()
        }
    }

    fun deleteTip(tip: HydrationTip) {
        viewModelScope.launch {
            repository.deleteTip(tip)
            loadTips()
        }
    }
}
