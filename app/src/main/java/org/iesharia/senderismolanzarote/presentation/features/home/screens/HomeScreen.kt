package org.iesharia.senderismolanzarote.presentation.features.home.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.launch
import org.iesharia.senderismolanzarote.presentation.features.home.components.RoutesList
import org.iesharia.senderismolanzarote.presentation.features.home.viewmodel.HomeViewModel
import org.iesharia.senderismolanzarote.presentation.core.base.UiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    drawerState: DrawerState,
    onNavigateToProfile: () -> Unit,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val scope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Senderismo Lanzarote") },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            scope.launch { drawerState.open() }
                        }
                    ) {
                        Icon(Icons.Default.Menu, "Menú")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            // Rutas Sugeridas
            RoutesList(
                title = "Rutas Sugeridas",
                routes = uiState.suggestedRoutes,
                onRouteClick = { route ->
                    viewModel.setActiveRoute(route.id)
                },
                onRetry = viewModel::refreshRoutes
            )

            // Todas las Rutas
            RoutesList(
                title = "Todas las Rutas",
                routes = uiState.allRoutes,
                onRouteClick = { route ->
                    viewModel.setActiveRoute(route.id)
                },
                onRetry = viewModel::refreshRoutes
            )
        }

        /*
        // Overlay para ruta activa
        if (uiState.activeRoute is UiState.Success && uiState.activeRoute.data != null) {
            // Aquí iría el componente de mapa con la ruta activa
            // Por ahora mostraremos un placeholder
            AlertDialog(
                onDismissRequest = { viewModel.clearActiveRoute() },
                title = { Text("Ruta: ${uiState.activeRoute.data.name}") },
                text = { Text("Aquí se mostrará el mapa de la ruta") },
                confirmButton = {
                    TextButton(onClick = { viewModel.clearActiveRoute() }) {
                        Text("Cerrar")
                    }
                }
            )
        }
        */
    }
}