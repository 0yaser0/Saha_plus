package com.example.saha_plus.data.local.dao

import androidx.room.*
import com.example.saha_plus.data.local.model.HydrationTip

@Dao
interface HydrationTipDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTip(tip: HydrationTip): Long

    @Query("SELECT * FROM hydration_tip ORDER BY created_at DESC")
    suspend fun getAllTips(): List<HydrationTip>

    @Update
    suspend fun updateTip(tip: HydrationTip)

    @Delete
    suspend fun deleteTip(tip: HydrationTip)
}
