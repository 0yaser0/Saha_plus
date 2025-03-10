package com.example.saha_plus.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ColumnInfo
import java.util.Date

@Entity(tableName = "hydration_tip")
data class HydrationTip(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "tip_id")
    val tipId: Int = 0,

    @ColumnInfo(name = "title")
    val title: String,

    @ColumnInfo(name = "description")
    val description: String,

    @ColumnInfo(name = "tip_type")
    val tipType: String, // e.g., "article", "video", etc.

    @ColumnInfo(name = "link")
    val link: String? = null,

    @ColumnInfo(name = "created_at")
    val createdAt: Date = Date()
)
