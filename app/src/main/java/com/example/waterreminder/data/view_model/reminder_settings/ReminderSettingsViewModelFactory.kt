package com.example.waterreminder.data.view_model.reminder_settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.waterreminder.data.repository.ReminderSettingsRepository

class ReminderSettingsViewModelFactory(private val repository: ReminderSettingsRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ReminderSettingsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ReminderSettingsViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
