package com.example.waterreminder.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.waterreminder.data.local.dao.GoalHistoryDao
import com.example.waterreminder.data.local.dao.HydrationTipDao
import com.example.waterreminder.data.local.dao.ReminderSettingsDao
import com.example.waterreminder.data.local.dao.UserDao
import com.example.waterreminder.data.local.dao.WaterConsumptionDao
import com.example.waterreminder.data.local.model.GoalHistory
import com.example.waterreminder.data.local.model.HydrationTip
import com.example.waterreminder.data.local.model.ReminderSettings
import com.example.waterreminder.data.local.model.User
import com.example.waterreminder.data.local.model.WaterConsumption
import com.example.waterreminder.util.Converters

@Database(
    entities = [User::class, GoalHistory::class, HydrationTip::class, ReminderSettings::class, WaterConsumption::class],
    version = 2,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
    abstract fun goalHistoryDao(): GoalHistoryDao
    abstract fun hydrationTipDao(): HydrationTipDao
    abstract fun reminderSettingsDao(): ReminderSettingsDao
    abstract fun waterConsumptionDao(): WaterConsumptionDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext, AppDatabase::class.java, "hydration-app-database"
                ).fallbackToDestructiveMigration().build()
                INSTANCE = instance
                instance
            }
        }
    }
}
