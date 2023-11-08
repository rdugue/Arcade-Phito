package com.ralphdugue.arcadephito.navigation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material.IconButton
import androidx.compose.material.NavigationRail
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.NavigationRailItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDeepLink
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.ralphdugue.arcadephito.navigation.domain.DASHBOARD_SCREENS
import com.ralphdugue.arcadephito.navigation.domain.Destination


@Composable
fun NavHost(
    navController: NavHostController,
    startDestination: Destination,
    modifier: Modifier = Modifier,
    route: String? = null,
    builder: NavGraphBuilder.() -> Unit
) {
    androidx.navigation.compose.NavHost(
        navController = navController,
        startDestination = startDestination.fullRoute,
        modifier = modifier,
        route = route,
        builder = builder
    )
}

fun NavGraphBuilder.composable(
    destination: Destination,
    arguments: List<NamedNavArgument> = emptyList(),
    deepLinks: List<NavDeepLink> = emptyList(),
    content: @Composable (NavBackStackEntry) -> Unit
) {
    composable(
        route = destination.fullRoute,
        arguments = arguments,
        deepLinks = deepLinks,
        content = content
    )
}

@Composable
fun BottomNav(
    selectedScreen: Destination,
    onLogoutClick: () -> Unit,
    onScreenSelected: (Destination) -> Unit
) {
    BottomAppBar(
        modifier = Modifier
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            DASHBOARD_SCREENS.forEach { screen ->
                IconButton(
                    onClick = { onScreenSelected(screen) },
                    enabled = screen != selectedScreen,
                    modifier = Modifier
                        .background(
                            color = if (screen == selectedScreen) {
                                MaterialTheme.colorScheme.secondaryContainer
                            } else {
                                androidx.compose.ui.graphics.Color.Transparent
                            },
                            shape = MaterialTheme.shapes.extraLarge
                        )
                        .padding(0.dp),
                ) {
                   Icon(
                       tint = if (screen == selectedScreen) {
                           MaterialTheme.colorScheme.onSecondaryContainer
                       } else {
                           MaterialTheme.colorScheme.onSurfaceVariant
                       },
                       painter = painterResource(id = screen.resourceIcon),
                       contentDescription = stringResource(id = screen.resourceTitle),
                   )
                }
            }
            Row(modifier = Modifier
                .weight(1f)
                .padding(end = 8.dp), horizontalArrangement = Arrangement.End) {
                FloatingActionButton(
                    onClick = { onLogoutClick() },
                ) {
                    Icon(imageVector = Icons.Default.Logout, contentDescription = "Logout")
                }
            }
        }
    }
}

@Composable
fun NavSideBar(
    selectedScreen: Destination,
    onLogoutClick: () -> Unit,
    onScreenSelected: (Destination) -> Unit
) {
    NavigationRail(backgroundColor = MaterialTheme.colorScheme.surfaceVariant) {
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(end = 8.dp), verticalArrangement = Arrangement.Top
        ) {
            FloatingActionButton(
                onClick = { onLogoutClick() },
            ) {
                Icon(imageVector = Icons.Default.Logout, contentDescription = "Logout")
            }
        }
        DASHBOARD_SCREENS.forEach { screen ->
            NavigationRailItem(
                icon = {
                    Icon(
                        tint = if (screen == selectedScreen) {
                            MaterialTheme.colorScheme.onSecondaryContainer
                        } else {
                            MaterialTheme.colorScheme.onSurfaceVariant
                        },
                        painter = painterResource(id = screen.resourceIcon),
                        contentDescription = null,
                        modifier = Modifier.background(
                            color = if (screen == selectedScreen) {
                                MaterialTheme.colorScheme.secondaryContainer
                            } else {
                                androidx.compose.ui.graphics.Color.Transparent
                            },
                            shape = MaterialTheme.shapes.large
                        )
                    )
                },
                label = {
                    Text(
                        text = stringResource(id = screen.resourceTitle),
                        color = if (screen == selectedScreen) {
                            MaterialTheme.colorScheme.onSecondaryContainer
                        } else {
                            MaterialTheme.colorScheme.onSurfaceVariant
                        }
                    )
                },
                selected = screen == selectedScreen,
                onClick = { onScreenSelected(screen) }
            )
        }
        Spacer(modifier = Modifier.weight(1f))
    }
}