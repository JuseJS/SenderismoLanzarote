package org.iesharia.senderismolanzarote.presentation.features.home.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.launch
import org.iesharia.senderismolanzarote.domain.model.route.main.RouteModel
import org.iesharia.senderismolanzarote.presentation.features.home.components.*
import org.iesharia.senderismolanzarote.presentation.core.base.UiState
import org.iesharia.senderismolanzarote.presentation.features.home.viewmodel.HomeViewModel

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
                    IconButton(onClick = { scope.launch { drawerState.open() } }) {
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
            RoutesSection(
                title = "Rutas Sugeridas",
                routesState = uiState.suggestedRoutes,
                onRouteClick = { viewModel.selectRoute(it.id) },
                getFavoriteStatus = { id -> id in uiState.routesFavoriteStatus },
                onFavoriteClick = { viewModel.toggleFavorite(it.id) }
            )

            RoutesSection(
                title = "Rutas Favoritas",
                routesState = uiState.favoriteRoutes,
                onRouteClick = { viewModel.selectRoute(it.id) },
                getFavoriteStatus = { id -> id in uiState.routesFavoriteStatus },
                onFavoriteClick = { viewModel.toggleFavorite(it.id) }
            )

            RoutesSection(
                title = "Todas las Rutas",
                routesState = uiState.allRoutes,
                onRouteClick = { viewModel.selectRoute(it.id) },
                getFavoriteStatus = { id -> id in uiState.routesFavoriteStatus },
                onFavoriteClick = { viewModel.toggleFavorite(it.id) }
            )
        }

        // Diálogo de detalles de ruta
        when (val selectedRoute = uiState.selectedRoute) {
            is UiState.Success -> {
                selectedRoute.data?.let { route ->
                    RouteDetailDialog(
                        route = route,
                        pointsOfInterest = uiState.pointsOfInterest,
                        isFavorite = route.id in uiState.routesFavoriteStatus,
                        onFavoriteClick = { viewModel.toggleFavorite(route.id) },
                        onDismiss = { viewModel.clearSelectedRoute() },
                        onStartRoute = { /* Implementar más adelante */ }
                    )
                }
            }
            else -> Unit
        }
    }
}

@Composable
fun RoutesSection(
    title: String,
    routesState: UiState<List<RouteModel>>,
    onRouteClick: (RouteModel) -> Unit,
    getFavoriteStatus: (Int) -> Boolean,
    onFavoriteClick: (RouteModel) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge
        )

        when (routesState) {
            is UiState.Loading -> {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            }
            is UiState.Success -> {
                if (routesState.data.isEmpty()) {
                    Text(
                        text = "No hay rutas disponibles",
                        style = MaterialTheme.typography.bodyLarge
                    )
                } else {
                    RoutesList(
                        routes = routesState.data,
                        onRouteClick = onRouteClick,
                        getFavoriteStatus = getFavoriteStatus,
                        onFavoriteClick = onFavoriteClick
                    )
                }
            }
            is UiState.Error -> {
                Text(
                    text = routesState.message,
                    color = MaterialTheme.colorScheme.error
                )
            }
            else -> Unit
        }
    }
}

@Composable
fun RoutesList(
    routes: List<RouteModel>,
    onRouteClick: (RouteModel) -> Unit,
    getFavoriteStatus: (Int) -> Boolean,
    onFavoriteClick: (RouteModel) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        routes.forEach { route ->
            key(route.id) {
                RouteCard(
                    route = route,
                    isFavorite = getFavoriteStatus(route.id),
                    onFavoriteClick = { onFavoriteClick(route) },
                    onClick = { onRouteClick(route) }
                )
            }
        }
    }
}