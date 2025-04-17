package com.example.skillswapapp

import com.example.skillswapapp.data.entities.Friendship
import com.example.skillswapapp.data.entities.User
import com.example.skillswapapp.data.relations.SkillSwapRequestDetails
import com.example.skillswapapp.data.relations.UserWithFriends
import com.example.skillswapapp.data.relations.UserWithoutSecureInfo
import com.example.skillswapapp.model.UiDisplaySkill
import com.example.skillswapapp.model.UiUserDisplay
import com.example.skillswapapp.view.filterUsersBySearchInput
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {

    @Test
    fun testFilterUsersBySearchInput_withMatchingSkill() {
        val mockProfilePicture = byteArrayOf(1, 2, 3, 4, 5)
        val users = listOf(
            UiUserDisplay(
                user = UserWithoutSecureInfo(1, "Alex Codewell", "alexcodewell@example.com", "Passionate coder and lifelong learner.", "this is the user unit test description", "Coding", "Vancouver", "BC",mockProfilePicture),
                skills = listOf(UiDisplaySkill(1, "C++", null)),
                seeksSkills = listOf(UiDisplaySkill(1, "Yoga", null))
            ),
            UiUserDisplay(
                user = UserWithoutSecureInfo(2, "Sky Forrest", "sky.forrest@example.com", "Living the plant parent life.", "this is the user unit test description", "Yoga", "Calgary", "AB",mockProfilePicture ),
                skills = listOf(UiDisplaySkill(3, "Herb Gardening", null)),
                seeksSkills = listOf(UiDisplaySkill(1, "C++", null))
            )
        )

        val searchInput = "C++"
        val result = filterUsersBySearchInput(users, searchInput)

        assertEquals(1, result.size)
        assertEquals("Alex Codewell", result[0].user.name)
    }

    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }
    @Test
    fun testUserWithFriendsRelationship() {
        val mockProfilePicture = byteArrayOf(1, 2, 3, 4, 5)
        val user = User(1, "deepa@example.com", "Deepa", "Intro here", "Calgary", "AB", "C++", 1,mockProfilePicture)
        val friend1 = User(2, "alex@example.com", "Alex", "Tech lover", "Toronto", "ON", "C", 1,mockProfilePicture)
        val friend2 = User(3, "mira@example.com", "Mira", "Yoga master", "Vancouver", "BC", "Yoga", 1,mockProfilePicture )

        val userWithFriends = UserWithFriends(
            user = user,
            friends = listOf(friend1, friend2)
        )

        assertEquals(2, userWithFriends.friends.size)
        assertEquals("Alex", userWithFriends.friends[0].name)
    }

    @Test
    fun testFriendshipStatus() {
        val friendship = Friendship(user_id = 1, friend_id = 2, status = "accepted")
        assertEquals("accepted", friendship.status)
    }

    // ----------------------------
    // Skill Swap Request Unit Tests
    // ----------------------------
    @Test
    fun testSwapRequestDetailsMapping() {
        val request = SkillSwapRequestDetails(
            request_id = 101,
            request_status = "pending",
            user_id_to = 1,
            user_id_from = 2,
            appointment_time = 1687200000000,
            details = "Let's learn Kotlin together!",
            name = "Sky",
            email = "sky@example.com"
        )

        assertEquals(101, request.request_id)
        assertEquals("pending", request.request_status)
        assertEquals("Sky", request.name)
    }

    @Test
    fun testCompletedRequestStatus() {
        val request = SkillSwapRequestDetails(
            request_id = 2,
            request_status = "completed",
            user_id_to = 1,
            user_id_from = 2,
            appointment_time = 1687200000000,
            details = "Done with the session.",
            name = "Alex",
            email = "alex@example.com"
        )

        assertTrue(request.request_status == "completed")
    }
}


