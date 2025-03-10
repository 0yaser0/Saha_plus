package com.example.saha_plus.data.repository

import com.example.saha_plus.data.local.dao.ReminderSettingsDao
import com.example.saha_plus.data.local.model.ReminderSettings
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ReminderSettingsRepository(private val dao: ReminderSettingsDao) {

    suspend fun insertReminder(reminder: ReminderSettings): Long = withContext(Dispatchers.IO) {
        dao.insertReminder(reminder)
    }

    suspend fun getRemindersForUser(userId: String): List<ReminderSettings> = withContext(Dispatchers.IO) {
        dao.getRemindersForUser(userId)
    }

    suspend fun updateReminder(reminder: ReminderSettings) = withContext(Dispatchers.IO) {
        dao.updateReminder(reminder)
    }

    suspend fun deleteReminder(reminder: ReminderSettings) = withContext(Dispatchers.IO) {
        dao.deleteReminder(reminder)
    }
}
