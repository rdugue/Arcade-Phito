package com.ralphdugue.arcadephito.auth.domain

import com.google.firebase.auth.FirebaseUser
import com.ralphdugue.arcadephito.profile.domain.UserProfile

fun FirebaseUser.toAuthenticatedUser() = with(this) {
    UserProfile(
        username = displayName ?: "JohnDoe",
        email = email,
        imageUrl = photoUrl.toString()
    )
}