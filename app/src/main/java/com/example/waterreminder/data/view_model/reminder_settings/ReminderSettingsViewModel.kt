package com.example.waterreminder.data.view_model.reminder_settings

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.waterreminder.data.local.model.ReminderSettings
import com.example.waterreminder.data.repository.ReminderSettingsRepository
import kotlinx.coroutines.launch

class ReminderSettingsViewModel(private val repository: ReminderSettingsRepository) : ViewModel() {

    private val _reminders = MutableLiveData<List<ReminderSettings>>()
    val reminders: LiveData<List<ReminderSettings>> get() = _reminders

    fun loadRemindersForUser(userId: String) {
        viewModelScope.launch {
            _reminders.value = repository.getRemindersForUser(userId)
        }
    }

    fun insertReminder(reminder: ReminderSettings) {
        viewModelScope.launch {
            repository.insertReminder(reminder)
            loadRemindersForUser(reminder.userId)
        }
    }

    fun updateReminder(reminder: ReminderSettings) {
        viewModelScope.launch {
            repository.updateReminder(reminder)
            loadRemindersForUser(reminder.userId)
        }
    }

    fun deleteReminder(reminder: ReminderSettings) {
        viewModelScope.launch {
            repository.deleteReminder(reminder)
            loadRemindersForUser(reminder.userId)
        }
    }
}
