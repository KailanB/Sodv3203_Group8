package com.example.skillswapapp.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.skillswapapp.data.dao.CategoryDao
import com.example.skillswapapp.data.dao.FriendshipDao
import com.example.skillswapapp.data.dao.LocationDao
import com.example.skillswapapp.data.dao.SkillDao
import com.example.skillswapapp.data.dao.SkillSwapRequestDao
import com.example.skillswapapp.data.dao.UserDao
import com.example.skillswapapp.data.dao.UserSeeksSkillsDao
import com.example.skillswapapp.data.dao.UserSkillsDao
import com.example.skillswapapp.data.entities.Category
import com.example.skillswapapp.data.entities.Friendship
import com.example.skillswapapp.data.entities.Location
import com.example.skillswapapp.data.entities.Skill
import com.example.skillswapapp.data.entities.SkillSwapRequest
import com.example.skillswapapp.data.entities.User
import com.example.skillswapapp.data.entities.UserSeeksSkills
import com.example.skillswapapp.data.entities.UserSkills


@Database(entities = [Category::class, Friendship::class, Location::class,
    Skill::class, SkillSwapRequest::class, User::class, UserSeeksSkills::class,
     UserSkills::class], version = 1, exportSchema = false)
abstract class SkillSwapDatabase: RoomDatabase() {

    abstract fun categoryDao(): CategoryDao
    abstract fun friendshipDao(): FriendshipDao
    abstract fun locationDao(): LocationDao
    abstract fun skillDao(): SkillDao
    abstract fun skillSwapRequestDao(): SkillSwapRequestDao
    abstract fun userDao(): UserDao
    abstract fun userSeeksSkills(): UserSeeksSkillsDao
    abstract fun userSkillsDao(): UserSkillsDao

    companion object {
        @Volatile
        private var Instance: SkillSwapDatabase? = null

        fun getDatabase(context: Context): SkillSwapDatabase {

            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, SkillSwapDatabase::class.java, "skill_swap_database")
                    .build()
                    .also { Instance = it }
            }
        }
    }
}
