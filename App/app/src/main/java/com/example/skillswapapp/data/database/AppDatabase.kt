package com.example.skillswapapp.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.skillswapapp.data.dao.UserDao
import com.example.skillswapapp.data.entities.Location
import com.example.skillswapapp.data.entities.User

@Database(
    entities = [User::class, Location::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "skillswap_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
