package com.example.waterreminder.data.view_model.water_consumption

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.waterreminder.data.local.model.WaterConsumption
import com.example.waterreminder.data.repository.WaterConsumptionRepository
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class WaterConsumptionViewModel(private val repository: WaterConsumptionRepository) : ViewModel() {

    private val _consumptions = MutableLiveData<List<WaterConsumption>>()
    val consumptions: LiveData<List<WaterConsumption>> get() = _consumptions

    fun loadConsumptions(userId: String) {
        viewModelScope.launch {
            _consumptions.value = repository.getConsumptionsForUser(userId)
        }
    }

    fun insertConsumption(consumption: WaterConsumption) {
        viewModelScope.launch {
            repository.insertConsumption(consumption)
            loadConsumptions(consumption.userId)
        }
    }

    fun updateConsumption(consumption: WaterConsumption) {
        viewModelScope.launch {
            repository.updateConsumption(consumption)
            loadConsumptions(consumption.userId)
        }
    }

    fun deleteConsumption(consumption: WaterConsumption) {
        viewModelScope.launch {
            repository.deleteConsumption(consumption)
            loadConsumptions(consumption.userId)
        }
    }

    fun getTodayTotal(userId: String, callback: (Float) -> Unit) {
        viewModelScope.launch {
            val all = repository.getConsumptionsForUser(userId)
            val today = all.filter {
                val cal = Calendar.getInstance()
                val todayDay = cal.get(Calendar.DAY_OF_YEAR)
                val todayYear = cal.get(Calendar.YEAR)

                cal.time = it.timestamp
                cal.get(Calendar.DAY_OF_YEAR) == todayDay && cal.get(Calendar.YEAR) == todayYear
            }
            val total = today.sumOf { it.volume.toDouble() }.toFloat()
            callback(total)
        }
    }

    fun getWeeklyStats(userId: String, callback: (Map<String, Float>) -> Unit) {
        viewModelScope.launch {
            val all = repository.getConsumptionsForUser(userId)

            val result = mutableMapOf<String, Float>()
            val format = SimpleDateFormat("EEE", Locale.getDefault())

            for (i in 0..6) {
                val cal = Calendar.getInstance().apply {
                    add(Calendar.DAY_OF_YEAR, -i)
                }
                val dayKey = format.format(cal.time)
                result[dayKey] = 0f
            }

            all.forEach {
                val dayKey = format.format(it.timestamp)
                if (result.containsKey(dayKey)) {
                    result[dayKey] = result[dayKey]!! + it.volume
                }
            }

            callback(result.toSortedMap(compareBy {
                SimpleDateFormat("EEE", Locale.getDefault()).parse(it)
            }))
        }
    }

}
