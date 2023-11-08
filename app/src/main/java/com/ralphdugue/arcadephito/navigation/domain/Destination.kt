package com.ralphdugue.arcadephito.navigation.domain

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.ralphdugue.arcadephito.R

sealed class Destination(
    protected val route: String,
    @StringRes val resourceTitle: Int = R.string.login_screen,
    @DrawableRes val resourceIcon: Int = R.drawable.phito_logo,
    vararg params: String
) {
    val fullRoute: String = if (params.isEmpty()) route else {
        val builder = StringBuilder(route)
        params.forEach { builder.append("/{${it}}") }
        builder.toString()
    }

    sealed class NoArgumentDestination(route: String): Destination(route) {
        operator fun invoke(): String = fullRoute
    }

    data object LoginScreen : NoArgumentDestination("login")

    data object ProfileScreen : Destination(
        route = "profile",
        resourceTitle = R.string.profile_screen,
        resourceIcon = R.drawable.profile
    )

    data object GamesScreen : Destination(
        route = "games",
        resourceTitle = R.string.games_screen,
        resourceIcon = R.drawable.games
    )

    data object TicTacToeScreen : Destination(
        route = "games/tictactoe",
        resourceTitle = R.string.tictactoe_screen,
        resourceIcon = R.drawable.tic_tac_toe
    )
}

val DASHBOARD_SCREENS = listOf(
    Destination.GamesScreen,
    Destination.ProfileScreen
)

fun Destination.isDashboardRoute(): Boolean = this in DASHBOARD_SCREENS