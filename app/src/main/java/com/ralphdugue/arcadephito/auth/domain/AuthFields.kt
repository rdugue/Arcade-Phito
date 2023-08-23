package com.ralphdugue.arcadephito.auth.domain

data class AuthenticationFields(
    val username: String? = null,
    val email: String? = null,
    val password: String? = null,
    val authType: AuthType = AuthType.EMAIL_SIGNIN
)

enum class AuthType {
    EMAIL_SIGNIN,
    EMAIL_SIGN_UP,
    GOOGLE
}