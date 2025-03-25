package com.example.skillswapapp.dummyData

import com.example.skillswapapp.model.Category
import com.example.skillswapapp.model.Friendship
import com.example.skillswapapp.model.Location
import com.example.skillswapapp.model.AvailableSkill
import com.example.skillswapapp.model.Skill
import com.example.skillswapapp.model.SkillSwapRequest
import com.example.skillswapapp.model.User


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

    fun loadSkills(): List<AvailableSkill> {
        return listOf(
            // Trades
            AvailableSkill(1, "Electrician", 1, "Expert in electrical wiring", "2024-03-10 08:00:00", "2024-03-10 12:00:00", true),
            AvailableSkill(2, "Plumbing", 1, "Experienced plumber", "2024-03-11 09:00:00", "2024-03-11 13:00:00", true),

            // Computer Science
            AvailableSkill(3, "C#", 2, "Software development in C#", "2024-03-12 10:00:00", "2024-03-12 14:00:00", true),
            AvailableSkill(4, "C++", 2, "Expert in C++ programming", "2024-03-13 11:00:00", "2024-03-13 15:00:00", true),
            AvailableSkill(5, "Python", 2, "Machine Learning & AI", "2024-03-14 12:00:00", "2024-03-14 16:00:00", true),

            // Sports
            AvailableSkill(6, "Soccer", 3, "Professional soccer coach", "2024-03-15 13:00:00", "2024-03-15 17:00:00", true),
            AvailableSkill(7, "Football", 3, "Football tactics and strategy", "2024-03-16 14:00:00", "2024-03-16 18:00:00", true)
        )
    }

    fun loadUsers(): List<User> {
        return listOf(
            User(1, "Alice", "alice@example.com", "pass123", "Software Engineer", "Loves coding in C++", "Prefers remote learning", 1, byteArrayOf(0x00)),
            User(2, "Bob", "bob@example.com", "securepass", "Plumbing Expert", "Has 10 years of experience", "Prefers in-person training", 2, byteArrayOf(0x00)),
            User(3, "Charlie", "charlie@example.com", "mypassword", "Football Coach", "Played professionally for 5 years", "Prefers hands-on learning", 3, byteArrayOf(0x00)),
            User(4, "David", "david@example.com", "strongpass", "Data Scientist", "Python enthusiast", "Prefers video tutorials", 4, byteArrayOf(0x00)),
            User(5, "Eve", "eve@example.com", "evepass", "Electrician & Mentor", "Loves teaching trade skills", "Enjoys one-on-one mentoring", 5, byteArrayOf(0x00))
        )
    }

    fun loadUserId1(): User {
        return User(1, "Alice", "alice@example.com", "pass123", "Software Engineer", "Loves coding in C++", "Prefers remote learning", 1, byteArrayOf(0x00),
            listOf(
            Skill(3, 1, "C#", "Alice codes in C# professionally"),
            Skill(4, 1, "C++", "Alice has deep knowledge in C++"),
            Skill(5, 1, "Python", "Alice uses Python for AI projects")
            ),
            listOf (
                Skill(1, 1, "Electrician", "Alice wants to learn electrical work"),
                Skill(2, 1, "Plumbing", "Alice wants to understand plumbing"),
                Skill(7, 1, "Football", "Alice is interested in soccer training")
            )
        )



    }

    fun loadUserSkills(): List<Skill> {
        return listOf(
            Skill(3, 1, "C#", "Alice codes in C# professionally"),
            Skill(4, 1, "C++", "Alice has deep knowledge in C++"),
            Skill(5, 1, "Python", "Alice uses Python for AI projects"),

            Skill(2, 2, "Plumbing", "Bob is a master plumber"),
            Skill(1, 2, "Electrician", "Bob also does electrical work"),
            Skill(6, 2, "Soccer", "Bob plays soccer as a hobby"),

            Skill(7, 3, "Football", "Charlie coaches soccer professionally"),
            Skill(6, 3, "Soccer", "Charlie is also good at football"),
            Skill(2, 3, "Plumbing", "Charlie has plumbing experience"),

            Skill(5, 4, "Python", "David is a Python data scientist"),
            Skill(4, 4, "C++", "David also teaches C++"),
            Skill(3, 4, "C#", "David develops C# applications"),

            Skill(1, 5, "Electrician", "Eve is an expert electrician"),
            Skill(2, 5, "Plumbing", "Eve is also a skilled plumber"),
            Skill(6, 5, "Soccer", "Eve enjoys soccer coaching")
        )
    }

    fun loadUserSkillsId(id: Int): List<Skill> {
        return when(id) {
            1 -> listOf(
                Skill(3, 1, "C#", "Alice codes in C# professionally"),
                Skill(4, 1, "C++", "Alice has deep knowledge in C++"),
                Skill(5, 1, "Python", "Alice uses Python for AI projects")
            )
            2 -> listOf(
                Skill(2, 2, "Plumbing", "Bob is a master plumber"),
                Skill(1, 2, "Electrician", "Bob also does electrical work"),
                Skill(6, 2, "Soccer", "Bob plays soccer as a hobby")
            )
            3 -> listOf(
                Skill(7, 3, "Football", "Charlie coaches soccer professionally"),
                Skill(6, 3, "Soccer", "Charlie is also good at football"),
                Skill(2, 3, "Plumbing", "Charlie has plumbing experience")
            )
            4 -> listOf(
                Skill(5, 4, "Python", "David is a Python data scientist"),
                Skill(4, 4, "C++", "David also teaches C++"),
                Skill(3, 4, "C#", "David develops C# applications")
            )
            5 -> listOf(
                Skill(1, 5, "Electrician", "Eve is an expert electrician"),
                Skill(2, 5, "Plumbing", "Eve is also a skilled plumber"),
                Skill(6, 5, "Soccer", "Eve enjoys soccer coaching")
            )
            else -> emptyList() // Return an empty list if the id doesn't match
        }
    }

            fun loadUserSeeksSkills(): List<Skill> {
        return listOf(
            Skill(1, 1, "Electrician", "Alice wants to learn electrical work"),
            Skill(2, 1, "Plumbing", "Alice wants to understand plumbing"),
            Skill(7, 1, "Football", "Alice is interested in soccer training"),

            Skill(3, 2, "C#", "Bob wants to learn C#"),
            Skill(4, 2, "C++", "Bob is curious about C++"),
            Skill(5, 2, "Python", "Bob wants to try Python"),

            Skill(1, 3, "Electrician", "Charlie wants to become an electrician"),
            Skill(3, 3, "C#", "Charlie wants to develop in C#"),
            Skill(5, 3, "Python", "Charlie is interested in Python for analytics"),

            Skill(6, 4, "Soccer", "David wants to get better at soccer"),
            Skill(7, 4, "Football", "David is learning football strategy"),
            Skill(2, 4, "Plumbing", "David wants to pick up plumbing skills"),

            Skill(3, 5, "C#", "Eve wants to explore C# coding"),
            Skill(4, 5, "C++", "Eve is interested in C++"),
            Skill(5, 5, "Python", "Eve wants to learn Python for automation")
        )
    }
    fun loadUserSeeksSkillsId(id: Int): List<Skill> {
        return when(id) {
            1 -> listOf(
                Skill(1, 1, "Electrician", "Alice wants to learn electrical work"),
                Skill(2, 1, "Plumbing", "Alice wants to understand plumbing"),
                Skill(7, 1, "Football", "Alice is interested in soccer training")
            )
            2 -> listOf(
                Skill(3, 2, "C#", "Bob wants to learn C#"),
                Skill(4, 2, "C++", "Bob is curious about C++"),
                Skill(5, 2, "Python", "Bob wants to try Python")
            )
            3 -> listOf(
                Skill(1, 3, "Electrician", "Charlie wants to become an electrician"),
                Skill(3, 3, "C#", "Charlie wants to develop in C#"),
                Skill(5, 3, "Python", "Charlie is interested in Python for analytics")
            )
            4 -> listOf(
                Skill(6, 4, "Soccer", "David wants to get better at soccer"),
                Skill(7, 4, "Football", "David is learning football strategy"),
                Skill(2, 4, "Plumbing", "David wants to pick up plumbing skills")
            )
            5 -> listOf(
                Skill(3, 5, "C#", "Eve wants to explore C# coding"),
                Skill(4, 5, "C++", "Eve is interested in C++"),
                Skill(5, 5, "Python", "Eve wants to learn Python for automation")
            )
            else -> emptyList() // Return an empty list if the id doesn't match
        }
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