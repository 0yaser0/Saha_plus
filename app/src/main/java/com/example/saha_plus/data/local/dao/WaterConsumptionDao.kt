package com.example.saha_plus.data.local.dao

import androidx.room.*
import com.example.saha_plus.data.local.model.WaterConsumption

@Dao
interface WaterConsumptionDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertConsumption(consumption: WaterConsumption): Long

    @Query("SELECT * FROM water_consumption WHERE user_id = :userId ORDER BY timestamp DESC")
    suspend fun getConsumptionsForUser(userId: String): List<WaterConsumption>

    @Update
    suspend fun updateConsumption(consumption: WaterConsumption)

    @Delete
    suspend fun deleteConsumption(consumption: WaterConsumption)
}
