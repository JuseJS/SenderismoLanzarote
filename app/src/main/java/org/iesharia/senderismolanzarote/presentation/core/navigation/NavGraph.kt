package org.iesharia.senderismolanzarote.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import org.iesharia.senderismolanzarote.presentation.features.auth.screen.AuthScreen
import org.iesharia.senderismolanzarote.presentation.home.screen.HomeScreen
import org.iesharia.senderismolanzarote.presentation.profile.screen.ProfileScreen

sealed class Screen(val route: String) {
    object Auth : Screen("auth")
    object Home : Screen("home")
    object Profile : Screen("profile")

    // Helper para rutas con argumentos
    fun withArgs(vararg args: String): String {
        return buildString {
            append(route)
            args.forEach { arg ->
                append("/$arg")
            }
        }
    }
}

@Composable
fun NavGraph(
    navController: NavHostController,
    startDestination: String,
    isAuthenticated: Boolean
) {
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
                navController.navigate(Screen.Auth.route) {
                    popUpTo(Screen.Home.route) { inclusive = true }
                }
            }
            HomeScreen()
        }

        composable(Screen.Profile.route) {
            if (!isAuthenticated) {
                navController.navigate(Screen.Auth.route) {
                    popUpTo(Screen.Profile.route) { inclusive = true }
                }
            }
            ProfileScreen()
        }
    }
}