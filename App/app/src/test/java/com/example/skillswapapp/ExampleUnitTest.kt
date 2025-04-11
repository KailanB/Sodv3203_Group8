package com.example.skillswapapp

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
        val users = listOf(
            UiUserDisplay(
                user = UserWithoutSecureInfo(1, "Alex Codewell", "alexcodewell@example.com", "Passionate coder and lifelong learner.", "Vancouver", "BC", null ),
                skills = listOf(UiDisplaySkill(1, "C++", null)),
                seeksSkills = listOf(UiDisplaySkill(1, "Yoga", null))
            ),
            UiUserDisplay(
                user = UserWithoutSecureInfo(2, "Sky Forrest", "sky.forrest@example.com", "Living the plant parent life.", "Calgary", "AB", null ),
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
}