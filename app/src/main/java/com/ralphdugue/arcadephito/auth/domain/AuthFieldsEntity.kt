package com.ralphdugue.arcadephito.auth.domain

data class AuthFieldsEntity(
    val username: String = "",
    val email: String = "",
    val password: String = "",
    val authType: AuthType = AuthType.EMAIL_SIGNIN
)

enum class AuthType {
    EMAIL_SIGNIN,
    EMAIL_SIGN_UP,
    GOOGLE
}

data class AuthUserEntity(
    val username: String? = null,
    val token: String? = null
)