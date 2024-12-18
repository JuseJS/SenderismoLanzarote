package org.iesharia.senderismolanzarote

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import org.iesharia.senderismolanzarote.presentation.common.navigation.AppNavigation
import org.iesharia.senderismolanzarote.presentation.common.navigation.Screen
import org.iesharia.senderismolanzarote.presentation.common.theme.SenderismoLanzaroteTheme
import org.iesharia.senderismolanzarote.presentation.common.theme.ThemeConfig
import org.iesharia.senderismolanzarote.presentation.common.theme.ThemeViewModel
import org.iesharia.senderismolanzarote.presentation.core.components.DrawerMenu
import org.iesharia.senderismolanzarote.presentation.features.auth.viewmodel.AuthViewModel
import org.iesharia.senderismolanzarote.presentation.initialization.viewmodel.InitializationViewModel
import org.osmdroid.config.Configuration
import java.io.File

private const val TAG = "MainActivity"

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val authViewModel: AuthViewModel by viewModels()
    private val themeViewModel: ThemeViewModel by viewModels()
    private val initViewModel: InitializationViewModel by viewModels()

    private val locationPermissionRequest = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        when {
            permissions.getOrDefault(android.Manifest.permission.ACCESS_FINE_LOCATION, false) ||
                    permissions.getOrDefault(android.Manifest.permission.ACCESS_COARSE_LOCATION, false) -> {
                Log.d(TAG, "Location permissions granted")
            }
            else -> {
                Toast.makeText(
                    this,
                    "Se requieren permisos de ubicación para la navegación",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Configuration.getInstance().let { config ->
            config.userAgentValue = packageName
            config.osmdroidBasePath = File(cacheDir, "osmdroid")
            config.osmdroidTileCache = File(config.osmdroidBasePath, "tiles")
        }

        locationPermissionRequest.launch(arrayOf(
            android.Manifest.permission.ACCESS_FINE_LOCATION,
            android.Manifest.permission.ACCESS_COARSE_LOCATION
        ))

        Log.d(TAG, "Starting app initialization")
        initViewModel.isInitialized.observe(this) { isInitialized ->
            Log.i(
                TAG,
                "Database initialization status: ${if (isInitialized) "Completed" else "In Progress"}"
            )
        }

        initViewModel.initializationError.observe(this) { error ->
            error?.let {
                Log.e(TAG, "Database initialization error: $it")
            }
        }

        setContent {
            val themeConfig by themeViewModel.themeConfig.collectAsState()
            val isAuthenticated by authViewModel.isAuthenticated.collectAsState(initial = false)
            val isAuthLoading by authViewModel.isAuthLoading.collectAsState()
            val logoutComplete by authViewModel.logoutComplete.collectAsState()
            val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
            val navController = rememberNavController()
            val scope = rememberCoroutineScope()
            val currentRoute = remember { mutableStateOf(if (isAuthenticated) Screen.Home.route else Screen.Auth.route) }

            // Monitorear cambios en la navegación
            DisposableEffect(navController) {
                val listener = NavController.OnDestinationChangedListener { _, destination, _ ->
                    currentRoute.value = destination.route ?: Screen.Home.route
                    Log.d(TAG, "Navigation changed to: ${destination.route}")
                }
                navController.addOnDestinationChangedListener(listener)
                onDispose {
                    navController.removeOnDestinationChangedListener(listener)
                }
            }


            LaunchedEffect(logoutComplete) {
                if (logoutComplete) {
                    navController.navigate(Screen.Auth.route) {
                        popUpTo(0) { inclusive = true }
                    }
                    authViewModel.resetLogout()
                }
            }

            SenderismoLanzaroteTheme(
                darkTheme = when (themeConfig) {
                    is ThemeConfig.FollowSystem -> isSystemInDarkTheme()
                    is ThemeConfig.Dark -> true
                    is ThemeConfig.Light -> false
                }
            ) {
                if (!isAuthLoading) {
                    ModalNavigationDrawer(
                        drawerState = drawerState,
                        gesturesEnabled = false,
                        drawerContent = {
                            if (isAuthenticated) {
                                DrawerMenu(
                                    currentRoute = currentRoute.value,
                                    onNavigateToHome = {
                                        Log.d(TAG, "Navigating to Home")
                                        navController.navigate(Screen.Home.route) {
                                            popUpTo(Screen.Home.route) { inclusive = true }
                                        }
                                    },
                                    onNavigateToProfile = {
                                        Log.d(TAG, "Navigating to Profile")
                                        navController.navigate(Screen.Profile.route)
                                    },
                                    onLogout = {
                                        Log.d(TAG, "User logout requested")
                                        scope.launch {
                                            drawerState.close()
                                        }
                                        authViewModel.logout()
                                    },
                                    onCloseDrawer = {
                                        scope.launch {
                                            Log.d(TAG, "Closing drawer")
                                            drawerState.close()
                                        }
                                    }
                                )
                            }
                        },
                    ) {
                        Surface(
                            modifier = Modifier.fillMaxSize(),
                            color = MaterialTheme.colorScheme.background
                        ) {
                            AppNavigation(
                                navController = navController,
                                isAuthenticated = isAuthenticated,
                                drawerState = drawerState,
                                onLogout = {
                                    authViewModel.logout()
                                }
                            )
                        }
                    }
                } else {
                    // Opcional: Mostrar un indicador de carga
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                }
            }
        }
    }
}