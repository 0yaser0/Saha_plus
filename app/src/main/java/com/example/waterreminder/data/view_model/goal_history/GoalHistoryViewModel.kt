package com.example.waterreminder.data.view_model.goal_history

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.waterreminder.data.local.model.GoalHistory
import com.example.waterreminder.data.repository.GoalHistoryRepository
import kotlinx.coroutines.launch
import java.util.Date

class GoalHistoryViewModel(private val repository: GoalHistoryRepository) : ViewModel() {

    private val _goalHistories = MutableLiveData<List<GoalHistory>>()
    val goalHistories: LiveData<List<GoalHistory>> get() = _goalHistories

    fun insertGoalHistory(goalHistory: GoalHistory) {
        viewModelScope.launch {
            repository.insertGoalHistory(goalHistory)
            loadGoalHistories(goalHistory.userId)
        }
    }

    fun loadGoalHistories(userId: String) {
        viewModelScope.launch {
            _goalHistories.value = repository.getGoalHistoryForUser(userId)
        }
    }

    fun getGoalHistoryByDate(userId: String, date: Date, onResult: (GoalHistory?) -> Unit) {
        viewModelScope.launch {
            val goalHistory = repository.getGoalHistoryByDate(userId, date)
            onResult(goalHistory)
        }
    }

    fun updateGoalHistory(goalHistory: GoalHistory) {
        viewModelScope.launch {
            repository.updateGoalHistory(goalHistory)
            loadGoalHistories(goalHistory.userId)
        }
    }

    fun deleteGoalHistory(goalHistory: GoalHistory) {
        viewModelScope.launch {
            repository.deleteGoalHistory(goalHistory)
            loadGoalHistories(goalHistory.userId)
        }
    }
}
