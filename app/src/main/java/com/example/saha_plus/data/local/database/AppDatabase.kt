package com.example.saha_plus.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.saha_plus.data.local.dao.GoalHistoryDao
import com.example.saha_plus.data.local.dao.HydrationTipDao
import com.example.saha_plus.data.local.dao.ReminderSettingsDao
import com.example.saha_plus.data.local.dao.UserDao
import com.example.saha_plus.data.local.dao.WaterConsumptionDao
import com.example.saha_plus.data.local.model.GoalHistory
import com.example.saha_plus.data.local.model.HydrationTip
import com.example.saha_plus.data.local.model.ReminderSettings
import com.example.saha_plus.data.local.model.User
import com.example.saha_plus.data.local.model.WaterConsumption
import com.example.saha_plus.util.Converters

@Database(
    entities = [User::class, GoalHistory::class, HydrationTip::class, ReminderSettings::class, WaterConsumption::class],
    version = 1,
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
