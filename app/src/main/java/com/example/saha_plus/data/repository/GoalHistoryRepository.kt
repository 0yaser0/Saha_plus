package com.example.saha_plus.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import com.example.saha_plus.data.local.model.GoalHistory
import com.example.saha_plus.data.local.dao.GoalHistoryDao
import java.util.Date

class GoalHistoryRepository(private val goalHistoryDao: GoalHistoryDao) {

    suspend fun insertGoalHistory(goalHistory: GoalHistory): Long = withContext(Dispatchers.IO) {
        goalHistoryDao.insertGoalHistory(goalHistory)
    }

    suspend fun getGoalHistoryForUser(userId: String): List<GoalHistory> = withContext(Dispatchers.IO) {
        goalHistoryDao.getGoalHistoryForUser(userId)
    }

    suspend fun getGoalHistoryByDate(userId: String, date: Date): GoalHistory? = withContext(Dispatchers.IO) {
        goalHistoryDao.getGoalHistoryByDate(userId, date)
    }

    suspend fun updateGoalHistory(goalHistory: GoalHistory) = withContext(Dispatchers.IO) {
        goalHistoryDao.updateGoalHistory(goalHistory)
    }

    suspend fun deleteGoalHistory(goalHistory: GoalHistory) = withContext(Dispatchers.IO) {
        goalHistoryDao.deleteGoalHistory(goalHistory)
    }
}
