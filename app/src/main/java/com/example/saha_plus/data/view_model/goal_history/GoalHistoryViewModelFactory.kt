package com.example.saha_plus.data.view_model.goal_history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.saha_plus.data.repository.GoalHistoryRepository

class GoalHistoryViewModelFactory(private val repository: GoalHistoryRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(GoalHistoryViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return GoalHistoryViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
