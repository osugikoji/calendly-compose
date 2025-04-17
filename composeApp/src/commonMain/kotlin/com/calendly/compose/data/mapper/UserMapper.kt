package com.calendly.compose.data.mapper

import com.calendly.compose.data.database.UserEntity
import com.calendly.compose.data.model.User

internal fun UserEntity.toUser(): User {
    return User(
        id = this.id,
        fullName = this.fullName,
        imageUrl = this.imageUrl,
    )
}
