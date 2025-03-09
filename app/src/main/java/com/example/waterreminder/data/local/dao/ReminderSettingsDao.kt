package com.example.waterreminder.data.local.dao

import androidx.room.*
import com.example.waterreminder.data.local.model.ReminderSettings

@Dao
interface ReminderSettingsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertReminder(reminder: ReminderSettings): Long

    @Query("SELECT * FROM reminder_settings WHERE user_id = :userId ORDER BY reminder_time ASC")
    suspend fun getRemindersForUser(userId: String): List<ReminderSettings>

    @Update
    suspend fun updateReminder(reminder: ReminderSettings)

    @Delete
    suspend fun deleteReminder(reminder: ReminderSettings)
}
