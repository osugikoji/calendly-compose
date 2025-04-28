package com.calendly.compose.data.mapper

import com.calendly.compose.data.database.UserEntity
import kotlin.test.Test
import kotlin.test.assertEquals

class UserMapperTest {

    @Test
    fun `when converting UserEntity to User then returns correctly mapped User object`() {
        // arrange
        val userEntity = UserEntity(
            id = "1",
            fullName = "Koji",
            imageUrl = "test.com",
        )

        // act
        val result = userEntity.toUser()

        // assert
        assertEquals("1", result.id)
        assertEquals("Koji", result.fullName)
        assertEquals("test.com", result.imageUrl)
    }
}
