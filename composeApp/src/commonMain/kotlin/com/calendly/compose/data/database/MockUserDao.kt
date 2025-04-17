package com.calendly.compose.data.database

import kotlinx.coroutines.delay

internal class MockUserDao : UserDao {

    private val users = listOf(
        UserEntity(
            id = "1",
            fullName = "Koji",
            imageUrl = "",
        ),
        UserEntity(
            id = "2",
            fullName = "Arvind Menon",
            imageUrl = "https://d3v0px0pttie1i.cloudfront.net/uploads/user/avatar/36673524/3c49f63e.jpg",
        )
    )

    override suspend fun readUser(id: String): UserEntity? {
        delay(100L) // Simulate read disk delay
        return users.find { it.id == id }
    }
}
