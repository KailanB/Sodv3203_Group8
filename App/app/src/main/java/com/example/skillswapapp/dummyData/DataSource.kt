package com.example.skillswapapp.dummyData

import com.example.skillswapapp.model.Category
import com.example.skillswapapp.model.Friendship
import com.example.skillswapapp.model.Location
import com.example.skillswapapp.model.Skill
import com.example.skillswapapp.model.SkillSwapRequest
import com.example.skillswapapp.model.UserSeeksSkills
import com.example.skillswapapp.model.UserSkills
import com.example.skillswapapp.model.Users

class DataSource {

    fun loadLocations(): List<Location> {
        return listOf(
            Location(1, "Toronto", "ON"),
            Location(2, "Vancouver", "BC"),
            Location(3, "Montreal", "QC"),
            Location(4, "Calgary", "AB"),
            Location(5, "Edmonton", "AB")
        )
    }

    fun loadCategories(): List<Category> {
        return listOf(
            Category(1, "Trades"),
            Category(2, "Computer Science"),
            Category(3, "Sports")
        )
    }

    fun loadSkills(): List<Skill> {
        return listOf(
            // Trades
            Skill(1, "Electrician", 1, "Expert in electrical wiring", "2024-03-10 08:00:00", "2024-03-10 12:00:00", true),
            Skill(2, "Plumbing", 1, "Experienced plumber", "2024-03-11 09:00:00", "2024-03-11 13:00:00", true),

            // Computer Science
            Skill(3, "C#", 2, "Software development in C#", "2024-03-12 10:00:00", "2024-03-12 14:00:00", true),
            Skill(4, "C++", 2, "Expert in C++ programming", "2024-03-13 11:00:00", "2024-03-13 15:00:00", true),
            Skill(5, "Python", 2, "Machine Learning & AI", "2024-03-14 12:00:00", "2024-03-14 16:00:00", true),

            // Sports
            Skill(6, "Soccer", 3, "Professional soccer coach", "2024-03-15 13:00:00", "2024-03-15 17:00:00", true),
            Skill(7, "Football", 3, "Football tactics and strategy", "2024-03-16 14:00:00", "2024-03-16 18:00:00", true)
        )
    }

    fun loadUsers(): List<Users> {
        return listOf(
            Users(1, "Alice", "alice@example.com", "pass123", "Software Engineer", "Loves coding in C++", "Prefers remote learning", 1, byteArrayOf(0x00)),
            Users(2, "Bob", "bob@example.com", "securepass", "Plumbing Expert", "Has 10 years of experience", "Prefers in-person training", 2, byteArrayOf(0x00)),
            Users(3, "Charlie", "charlie@example.com", "mypassword", "Football Coach", "Played professionally for 5 years", "Prefers hands-on learning", 3, byteArrayOf(0x00)),
            Users(4, "David", "david@example.com", "strongpass", "Data Scientist", "Python enthusiast", "Prefers video tutorials", 4, byteArrayOf(0x00)),
            Users(5, "Eve", "eve@example.com", "evepass", "Electrician & Mentor", "Loves teaching trade skills", "Enjoys one-on-one mentoring", 5, byteArrayOf(0x00))
        )
    }

    fun loadUserSkills(): List<UserSkills> {
        return listOf(
            UserSkills(3, 1, "Alice codes in C# professionally"),
            UserSkills(4, 1, "Alice has deep knowledge in C++"),
            UserSkills(5, 1, "Alice uses Python for AI projects"),

            UserSkills(2, 2, "Bob is a master plumber"),
            UserSkills(1, 2, "Bob also does electrical work"),
            UserSkills(6, 2, "Bob plays soccer as a hobby"),

            UserSkills(7, 3, "Charlie coaches soccer professionally"),
            UserSkills(6, 3, "Charlie is also good at football"),
            UserSkills(2, 3, "Charlie has plumbing experience"),

            UserSkills(5, 4, "David is a Python data scientist"),
            UserSkills(4, 4, "David also teaches C++"),
            UserSkills(3, 4, "David develops C# applications"),

            UserSkills(1, 5, "Eve is an expert electrician"),
            UserSkills(2, 5, "Eve is also a skilled plumber"),
            UserSkills(6, 5, "Eve enjoys soccer coaching")
        )
    }

    fun loadUserSeeksSkills(): List<UserSeeksSkills> {
        return listOf(
            UserSeeksSkills(1, 1, "Alice wants to learn electrical work"),
            UserSeeksSkills(2, 1, "Alice wants to understand plumbing"),
            UserSeeksSkills(7, 1, "Alice is interested in soccer training"),

            UserSeeksSkills(3, 2, "Bob wants to learn C#"),
            UserSeeksSkills(4, 2, "Bob is curious about C++"),
            UserSeeksSkills(5, 2, "Bob wants to try Python"),

            UserSeeksSkills(1, 3, "Charlie wants to become an electrician"),
            UserSeeksSkills(3, 3, "Charlie wants to develop in C#"),
            UserSeeksSkills(5, 3, "Charlie is interested in Python for analytics"),

            UserSeeksSkills(6, 4, "David wants to get better at soccer"),
            UserSeeksSkills(7, 4, "David is learning football strategy"),
            UserSeeksSkills(2, 4, "David wants to pick up plumbing skills"),

            UserSeeksSkills(3, 5, "Eve wants to explore C# coding"),
            UserSeeksSkills(4, 5, "Eve is interested in C++"),
            UserSeeksSkills(5, 5, "Eve wants to learn Python for automation")
        )
    }

    fun loadFriendships(): List<Friendship> {
        return listOf(
            Friendship(1, 2, "accepted"),
            Friendship(1, 3, "pending"),
            Friendship(2, 4, "accepted"),
            Friendship(3, 5, "rejected"),
            Friendship(4, 5, "accepted")
        )
    }

    fun loadSkillSwapRequests(): List<SkillSwapRequest> {
        return listOf(
            SkillSwapRequest(1, "pending", 1, 2, "2024-03-20 10:00:00", "Hi Bob, I was hoping you could teach me about some plumbing", "pending"),
            SkillSwapRequest(2, "accepted", 3, 4, "2024-03-22 14:00:00", "Charlie I was wondering if you could teach me some tips on football", "accepted"),
            SkillSwapRequest(3, "rejected", 5, 1, "2024-03-24 16:00:00", "Hi Eve I would love to learn electrical from you", "rejected"),
            SkillSwapRequest(4, "pending", 2, 3, "2024-03-25 12:00:00", "Charlie. This is Bob. Teach me C++. Tks.", "pending")
        )
    }

}