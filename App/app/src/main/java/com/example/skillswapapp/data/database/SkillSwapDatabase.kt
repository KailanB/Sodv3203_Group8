package com.example.skillswapapp.data.database

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
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
    abstract fun userSeeksSkillsDao(): UserSeeksSkillsDao
    abstract fun userSkillsDao(): UserSkillsDao

    companion object {
        @Volatile
        private var Instance: SkillSwapDatabase? = null

        fun getDatabase(context: Context): SkillSwapDatabase {

            // ************************* DELETE DATA BASE ON APP LAUNCH *************
            context.deleteDatabase("skill_swap_database")
            // ************************* DELETE DATA BASE ON APP LAUNCH *************

            return Instance ?: synchronized(this) {
                Room.databaseBuilder(
                    context,
                    SkillSwapDatabase::class.java, "skill_swap_database")
                    .fallbackToDestructiveMigrationOnDowngrade()
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { Instance = it }
            }
        }
    }
}



// add dummy data
class DatabaseInitializer(private val context: Context) {

    suspend fun populateDatabase(database: SkillSwapDatabase)
    {
        val categories = listOf (
            Category(category_id = 0, category = "Coding"),
            Category(category_id = 0, category = "Cooking"),
            Category(category_id = 0, category = "Music"),
            Category(category_id = 0, category = "Home Repair"),
            Category(category_id = 0, category = "Fitness"),
            Category(category_id = 0, category = "Gardening")
        )

        val skills = listOf (
            // Coding
            Skill(skill_id = 0, skill_name = "C++", category_id = 1, skill_description = "Object-oriented programming language."),
            Skill(skill_id = 0, skill_name = "C#", category_id = 1, skill_description = "Great for building Windows apps and games with Unity."),
            Skill(skill_id = 0, skill_name = "Python", category_id = 1, skill_description = "Popular scripting and data science language."),

            // Cooking
            Skill(skill_id = 0, skill_name = "Baking Bread", category_id = 2, skill_description = "Learn how to bake artisan loaves."),
            Skill(skill_id = 0, skill_name = "Sushi Making", category_id = 2, skill_description = "Roll fresh sushi and sashimi."),

            // Music
            Skill(skill_id = 0, skill_name = "Guitar", category_id = 3, skill_description = "Play chords, solos, and songs."),
            Skill(skill_id = 0, skill_name = "Piano", category_id = 3, skill_description = "Learn classical and pop tunes."),

            // Home Repair
            Skill(skill_id = 0, skill_name = "Basic Plumbing", category_id = 4, skill_description = "Fix leaky faucets and clogs."),
            Skill(skill_id = 0, skill_name = "Furniture Assembly", category_id = 4, skill_description = "Master of IKEA builds."),

            // Fitness
            Skill(skill_id = 0, skill_name = "Yoga", category_id = 5, skill_description = "Stretch, breathe, and build strength."),
            Skill(skill_id = 0, skill_name = "Weightlifting", category_id = 5, skill_description = "Pump iron safely and effectively."),

            // Gardening
            Skill(skill_id = 0, skill_name = "Composting", category_id = 6, skill_description = "Turn scraps into garden gold."),
            Skill(skill_id = 0, skill_name = "Herb Gardening", category_id = 6, skill_description = "Grow basil, mint, and more.")
        )

        val locations = listOf(
            Location(location_id = 1, city = "Vancouver", province = "BC"),
            Location(location_id = 2, city = "Toronto", province = "ON"),
            Location(location_id = 3, city = "Calgary", province = "AB"),
            Location(location_id = 4, city = "Halifax", province = "NS"),
            Location(location_id = 5, city = "Winnipeg", province = "MB"),
            Location(location_id = 6, city = "Calgary", province = "AB")
        )

        val users = listOf(
            User(
                user_id = 0,
                name = "Alex Codewell",
                email = "alex.codewell@example.com",
                password = "pass1234",
                profile_intro = "Passionate coder and lifelong learner.",
                description = "I love sharing my knowledge in software engineering and am always up for a coding challenge.",
                preferences = "In-person or virtual sessions, evenings preferred.",
                location_id = 1,
                profile_picture = null
            ),
            User(
                user_id = 0,
                name = "Jamie Pan",
                email = "jamie.pan@example.com",
                password = "bakeitup",
                profile_intro = "Aspiring pastry chef who loves a flaky crust.",
                description = "Currently obsessed with sourdough. Let's knead!",
                preferences = "Weekends only. Prefer in-person lessons.",
                location_id = 2,
                profile_picture = null
            ),
            User(
                user_id = 0,
                name = "Riley Strum",
                email = "riley.strum@example.com",
                password = "shred123",
                profile_intro = "Music is life",
                description = "I've played guitar for 12 years and love helping beginners get started.",
                preferences = "Virtual sessions preferred.",
                location_id = 3,
                profile_picture = null
            ),
            User(
                user_id = 0,
                name = "Morgan Fixit",
                email = "morgan.fixit@example.com",
                password = "tools4life",
                profile_intro = "DIY enthusiast with a knack for repairs.",
                description = "From leaky taps to assembling furniture, I’ve got you covered.",
                preferences = "Anytime. I can travel around the city.",
                location_id = 4,
                profile_picture = null
            ),
            User(
                user_id = 0,
                name = "Sky Forrest",
                email = "sky.forrest@example.com",
                password = "greenthumb",
                profile_intro = "Living the plant parent life",
                description = "I specialize in composting, urban gardening, and plant care.",
                preferences = "Mornings only. Outdoor lessons welcome!",
                location_id = 5,
                profile_picture = null
            ),
            User(
                user_id = 0,
                name = "tester",
                email = "test@gmail.com",
                password = "test12",
                profile_intro = "I am a test",
                description = null,
                preferences = null,
                location_id = 6,
                profile_picture = null
            )
        )

        val userSkills = listOf(
            UserSkills(user_id = 1, skill_id = 1, skill_description = "Expert in C++ for embedded systems."),
            UserSkills(user_id = 1, skill_id = 3, skill_description = "Used Python for data analysis projects."),

            UserSkills(user_id = 2, skill_id = 4, skill_description = "Home baker for 3 years."),
            UserSkills(user_id = 2, skill_id = 5, skill_description = "Learned sushi making while working in a restaurant."),

            UserSkills(user_id = 3, skill_id = 6, skill_description = "Taught beginner guitar for 5 years."),
            UserSkills(user_id = 3, skill_id = 7, skill_description = "Plays piano recreationally."),

            UserSkills(user_id = 4, skill_id = 8, skill_description = "Plumbing repairs around the house."),
            UserSkills(user_id = 4, skill_id = 9, skill_description = "Fast and efficient with furniture builds."),

            UserSkills(user_id = 5, skill_id = 12, skill_description = "Experienced with urban composting."),
            UserSkills(user_id = 5, skill_id = 13, skill_description = "Grows herbs on a balcony garden.")
        )

        val userSeeksSkills = listOf(
            UserSeeksSkills(user_id = 1, skill_id = 10, skill_seekers_description = "Looking to learn yoga for flexibility."),
            UserSeeksSkills(user_id = 1, skill_id = 13, skill_seekers_description = "Would like to start a herb garden."),

            UserSeeksSkills(user_id = 2, skill_id = 1, skill_seekers_description = "Interested in learning basic C++."),
            UserSeeksSkills(user_id = 2, skill_id = 11, skill_seekers_description = "Trying to build strength with weights."),

            UserSeeksSkills(user_id = 3, skill_id = 3, skill_seekers_description = "Want to automate tasks with Python."),
            UserSeeksSkills(user_id = 3, skill_id = 4, skill_seekers_description = "Curious about baking sourdough."),

            UserSeeksSkills(user_id = 4, skill_id = 6, skill_seekers_description = "Always wanted to learn guitar."),
            UserSeeksSkills(user_id = 4, skill_id = 10, skill_seekers_description = "Trying yoga for stress relief."),

            UserSeeksSkills(user_id = 5, skill_id = 2, skill_seekers_description = "C# skills needed for game dev project."),
            UserSeeksSkills(user_id = 5, skill_id = 8, skill_seekers_description = "Learn plumbing basics for home fixes.")
        )

        val friendships = listOf(
            // User 1 has 4 friends
            Friendship(user_id = 1, friend_id = 2, status = "accepted"),  // User 1 is friends with User 2
            Friendship(user_id = 1, friend_id = 3, status = "accepted"),  // User 1 is friends with User 3
            Friendship(user_id = 1, friend_id = 4, status = "pending"),   // User 1 is pending with User 4
            Friendship(user_id = 1, friend_id = 5, status = "blocked"),   // User 1 has blocked User 5

            // User 2 has 1 friend
            Friendship(user_id = 2, friend_id = 1, status = "accepted"),  // User 2 is friends with User 1

            // User 3 has 3 friends
            Friendship(user_id = 3, friend_id = 1, status = "accepted"),  // User 3 is friends with User 1
            Friendship(user_id = 3, friend_id = 2, status = "pending"),   // User 3 is pending with User 2
            Friendship(user_id = 3, friend_id = 5, status = "accepted"),  // User 3 is friends with User 5

            // User 4 has 2 friends
            Friendship(user_id = 4, friend_id = 1, status = "accepted"),  // User 4 is friends with User 1
            Friendship(user_id = 4, friend_id = 3, status = "accepted"),  // User 4 is friends with User 3

            // User 5 has 1 friend
            Friendship(user_id = 5, friend_id = 1, status = "pending"),   // User 5 is pending with User 1
        )

        val skillSwapRequests = listOf(
            // User 1 offers C++ and wants to learn Weightlifting from User 4
            SkillSwapRequest(
                request_id = 0,
                request_status = "pending",
                user_id_to = 4, // User 4 (Weightlifting expert)
                user_id_from = 1, // User 1 (C++ expert)
                appointment_time = System.currentTimeMillis(),
                details = "Hi, I'm Alex Codewell! I’d love to learn furniture assembly and I can teach you C++ in return! Let me know if you're interested!"
            ),

            // User 2 offers Baking Bread and wants to learn Python from User 1
            SkillSwapRequest(
                request_id = 0,
                request_status = "pending",
                user_id_to = 1, // User 1 (Python expert)
                user_id_from = 2, // User 2 (Baking Bread expert)
                appointment_time = System.currentTimeMillis(),
                details = "Hello, I'm Jamie Pan! I’d love to learn Python, and I can show you how to bake artisan bread! How about it?"
            ),

            // User 3 offers Guitar and wants to learn Basic Plumbing from User 4
            SkillSwapRequest(
                request_id = 0,
                request_status = "pending",
                user_id_to = 4, // User 4 (Basic Plumbing expert)
                user_id_from = 3, // User 3 (Guitar expert)
                appointment_time = System.currentTimeMillis(),
                details = "Hey there, I’m Riley Strum! I’m looking to learn some basic plumbing skills, and I’d be happy to teach you how to play guitar. Interested?"
            ),

            // User 4 offers Furniture Assembly and wants to learn Guitar from User 3
            SkillSwapRequest(
                request_id = 0,
                request_status = "pending",
                user_id_to = 3, // User 3 (Guitar expert)
                user_id_from = 4, // User 4 (Furniture Assembly expert)
                appointment_time = System.currentTimeMillis(),
                details = "Hi, I’m Morgan Fixit! I’m experienced in furniture assembly and would love to learn how to play guitar. Let me know if you’re up for a swap!"
            )
        )

        try {
            database.categoryDao().insertCategories(categories)
            database.skillDao().insertSkills(skills)
            database.locationDao().insertLocations(locations)
            database.userDao().insertUsers(users)
            database.userSkillsDao().insertUserSkills(userSkills)
            database.userSeeksSkillsDao().insertUserSeeks(userSeeksSkills)
            database.friendshipDao().insertFriendships(friendships)
            database.skillSwapRequestDao().insertSkillSwaps(skillSwapRequests)

            // verify inserts
            // SELECT * FROM category c JOIN skill s ON c.category_id = s.category_id
            // SELECT * FROM users


        }
        catch (exception: Exception)
        {
            Log.d("CatchErrors", "Insert Error $exception")
        }
    }
}



