package com.example.waterreminder.data.local.dao

import androidx.room.*
import com.example.waterreminder.data.local.model.GoalHistory
import java.util.Date

@Dao
interface GoalHistoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGoalHistory(goalHistory: GoalHistory): Long

    @Query("SELECT * FROM goal_history WHERE user_id = :userId ORDER BY date DESC")
    suspend fun getGoalHistoryForUser(userId: String): List<GoalHistory>

    @Query("SELECT * FROM goal_history WHERE user_id = :userId AND date = :date LIMIT 1")
    suspend fun getGoalHistoryByDate(userId: String, date: Date): GoalHistory?

    @Update
    suspend fun updateGoalHistory(goalHistory: GoalHistory)

    @Delete
    suspend fun deleteGoalHistory(goalHistory: GoalHistory)
}
