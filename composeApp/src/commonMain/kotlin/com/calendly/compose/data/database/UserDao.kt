package com.calendly.compose.data.database

internal interface UserDao {

    suspend fun readUser(id: String): UserEntity?
}
