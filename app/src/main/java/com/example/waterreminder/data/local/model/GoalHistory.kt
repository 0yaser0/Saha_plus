package com.example.waterreminder.data.local.model

import androidx.room.*
import java.util.Date

@Entity(
    tableName = "goal_history",
    foreignKeys = [
        ForeignKey(
            entity = User::class,
            parentColumns = ["user_id"],
            childColumns = ["user_id"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class GoalHistory(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "goal_history_id")
    val goalHistoryId: Int = 0,

    @ColumnInfo(name = "user_id", index = true)
    val userId: String,

    @ColumnInfo(name = "date")
    val date: Date,

    @ColumnInfo(name = "goal_value")
    val goalValue: Float,

    @ColumnInfo(name = "achieved")
    val achieved: Boolean,

    @ColumnInfo(name = "created_at")
    val createdAt: Date = Date()
)