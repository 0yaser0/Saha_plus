package com.example.waterreminder.data.repository

import com.example.waterreminder.data.local.dao.HydrationTipDao
import com.example.waterreminder.data.local.model.HydrationTip
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class HydrationTipRepository(private val hydrationTipDao: HydrationTipDao) {

    suspend fun insertTip(tip: HydrationTip): Long = withContext(Dispatchers.IO) {
        hydrationTipDao.insertTip(tip)
    }

    suspend fun getAllTips(): List<HydrationTip> = withContext(Dispatchers.IO) {
        hydrationTipDao.getAllTips()
    }

    suspend fun updateTip(tip: HydrationTip) = withContext(Dispatchers.IO) {
        hydrationTipDao.updateTip(tip)
    }

    suspend fun deleteTip(tip: HydrationTip) = withContext(Dispatchers.IO) {
        hydrationTipDao.deleteTip(tip)
    }
}
