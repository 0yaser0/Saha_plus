package com.example.waterreminder.data.repository

import com.example.waterreminder.data.local.dao.WaterConsumptionDao
import com.example.waterreminder.data.local.model.WaterConsumption
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class WaterConsumptionRepository(private val waterConsumptionDao: WaterConsumptionDao) {

    suspend fun insertConsumption(consumption: WaterConsumption): Long = withContext(Dispatchers.IO) {
        waterConsumptionDao.insertConsumption(consumption)
    }

    suspend fun getConsumptionsForUser(userId: String): List<WaterConsumption> = withContext(Dispatchers.IO) {
        waterConsumptionDao.getConsumptionsForUser(userId)
    }

    suspend fun updateConsumption(consumption: WaterConsumption) = withContext(Dispatchers.IO) {
        waterConsumptionDao.updateConsumption(consumption)
    }

    suspend fun deleteConsumption(consumption: WaterConsumption) = withContext(Dispatchers.IO) {
        waterConsumptionDao.deleteConsumption(consumption)
    }
}
