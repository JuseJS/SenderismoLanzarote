package org.iesharia.senderismolanzarote.presentation.common.navigation

import androidx.compose.runtime.Composable
import androidx.compose.material3.DrawerState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import org.iesharia.senderismolanzarote.presentation.features.auth.screens.AuthScreen
import org.iesharia.senderismolanzarote.presentation.features.home.screens.HomeScreen
import org.iesharia.senderismolanzarote.presentation.features.profile.screens.ProfileScreen

sealed class Screen(val route: String) {
    object Auth : Screen("auth")
    object Home : Screen("home")
    object Profile : Screen("profile")

    fun withArgs(vararg args: String): String = buildString {
        append(route)
        args.forEach { arg -> append("/$arg") }
    }
}

@Composable
fun AppNavigation(
    navController: NavHostController,
    isAuthenticated: Boolean,
    drawerState: DrawerState,
    onLogout: () -> Unit
) {
    val startDestination = remember(isAuthenticated) {
        if (isAuthenticated) Screen.Home.route else Screen.Auth.route
    }

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(Screen.Auth.route) {
            AuthScreen(
                onNavigateToHome = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Auth.route) { inclusive = true }
                    }
                }
            )
        }

        composable(Screen.Home.route) {
            if (!isAuthenticated) {
                LaunchedEffect(Unit) {
                    navController.navigate(Screen.Auth.route) {
                        popUpTo(0) { inclusive = true }
                    }
                }
            }
            HomeScreen(
                drawerState = drawerState,
                onNavigateToProfile = {
                    navController.navigate(Screen.Profile.route)
                }
            )
        }

        composable(Screen.Profile.route) {
            if (!isAuthenticated) {
                LaunchedEffect(Unit) {
                    navController.navigate(Screen.Auth.route) {
                        popUpTo(0) { inclusive = true }
                    }
                }
            }
            ProfileScreen(
                drawerState = drawerState,
                onNavigateToHome = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Profile.route) { inclusive = true }
                    }
                },
                onLogout = onLogout
            )
        }
    }
}

@Composable
private fun NavGraph(
    navController: NavHostController,
    startDestination: String,
    drawerState: DrawerState,
    onLogout: () -> Unit
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        // Ruta de autenticación
        composable(Screen.Auth.route) {
            AuthScreen(
                onNavigateToHome = {
                    navController.navigate(Screen.Home.route) {
                        // Limpiar el back stack al autenticarse
                        popUpTo(Screen.Auth.route) { inclusive = true }
                    }
                }
            )
        }

        // Ruta de inicio
        composable(Screen.Home.route) {
            HomeScreen(
                drawerState = drawerState,
                onNavigateToProfile = {
                    navController.navigate(Screen.Profile.route)
                }
            )
        }

        // Ruta de perfil
        composable(Screen.Profile.route) {
            ProfileScreen(
                drawerState = drawerState,
                onNavigateToHome = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Profile.route) { inclusive = true }
                    }
                },
                onLogout = {
                    onLogout()
                    navController.navigate(Screen.Auth.route) {
                        // Limpiar todo el back stack al cerrar sesión
                        popUpTo(0) { inclusive = true }
                    }
                }
            )
        }
    }
}