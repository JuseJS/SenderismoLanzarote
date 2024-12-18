package org.iesharia.senderismolanzarote.presentation.features.home.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.launch
import org.iesharia.senderismolanzarote.domain.model.route.main.RouteModel
import org.iesharia.senderismolanzarote.presentation.common.components.ErrorContent
import org.iesharia.senderismolanzarote.presentation.common.components.LoadingOverlay
import org.iesharia.senderismolanzarote.presentation.core.base.UiState
import org.iesharia.senderismolanzarote.presentation.features.home.components.*
import org.iesharia.senderismolanzarote.presentation.features.home.viewmodel.HomeViewModel
import org.iesharia.senderismolanzarote.presentation.features.navigation.components.MapView
import org.iesharia.senderismolanzarote.presentation.features.navigation.viewmodel.NavigationViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    drawerState: DrawerState,
    onNavigateToProfile: () -> Unit,
    homeViewModel: HomeViewModel = hiltViewModel(),
    navigationViewModel: NavigationViewModel = hiltViewModel()
) {
    val uiState by homeViewModel.uiState.collectAsState()
    val navigationUiState by navigationViewModel.uiState.collectAsState()
    val scope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Senderismo Lanzarote") },
                navigationIcon = {
                    IconButton(onClick = { scope.launch { drawerState.open() } }) {
                        Icon(Icons.Default.Menu, contentDescription = "Menú")
                    }
                }
            )
        },
        floatingActionButton = {
            if (uiState.navigationState.isNavigating) {
                FloatingActionButton(
                    onClick = { homeViewModel.stopRoute() },
                    containerColor = MaterialTheme.colorScheme.error
                ) {
                    Icon(Icons.Default.Close, contentDescription = "Cancelar Ruta")
                }
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            if (uiState.navigationState.isNavigating) {
                Box(modifier = Modifier.fillMaxSize()) {
                    MapView(
                        navigationState = uiState.navigationState,
                        mapSettings = navigationUiState.mapSettings,
                        pointsOfInterest = when (val poi = uiState.pointsOfInterest) {
                            is UiState.Success -> poi.data
                            else -> emptyList()
                        },
                        modifier = Modifier.fillMaxSize()
                    )

                    Surface(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                            .align(Alignment.TopCenter),
                        color = MaterialTheme.colorScheme.surface.copy(alpha = 0.9f),
                        shape = MaterialTheme.shapes.medium,
                        tonalElevation = 4.dp
                    ) {
                        Text(
                            text = uiState.navigationState.currentRoute?.name ?: "Navegación",
                            style = MaterialTheme.typography.titleLarge,
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(24.dp)
                ) {
                    // Rutas Sugeridas
                    when (val suggestedRoutes = uiState.suggestedRoutes) {
                        is UiState.Loading -> {
                            item {
                                LoadingOverlay()
                            }
                        }
                        is UiState.Success -> {
                            if (suggestedRoutes.data.isNotEmpty()) {
                                item {
                                    RouteSection(
                                        title = "Rutas Sugeridas",
                                        subtitle = "Basadas en tus preferencias",
                                        routes = suggestedRoutes.data,
                                        onRouteClick = { homeViewModel.selectRoute(it.id) },
                                        getFavoriteStatus = { id -> id in uiState.routesFavoriteStatus },
                                        onFavoriteClick = { homeViewModel.toggleFavorite(it.id) }
                                    )
                                }
                            }
                        }
                        is UiState.Error -> {
                            item {
                                ErrorContent(
                                    message = suggestedRoutes.message,
                                    onRetry = { homeViewModel.loadSuggestedRoutes() }
                                )
                            }
                        }
                        else -> Unit
                    }

                    // Rutas Favoritas
                    when (val favoriteRoutes = uiState.favoriteRoutes) {
                        is UiState.Loading -> {
                            item {
                                LoadingOverlay()
                            }
                        }
                        is UiState.Success -> {
                            if (favoriteRoutes.data.isNotEmpty()) {
                                item {
                                    RouteSection(
                                        title = "Mis Rutas Favoritas",
                                        subtitle = "Tus rutas guardadas",
                                        routes = favoriteRoutes.data,
                                        onRouteClick = { homeViewModel.selectRoute(it.id) },
                                        getFavoriteStatus = { id -> id in uiState.routesFavoriteStatus },
                                        onFavoriteClick = { homeViewModel.toggleFavorite(it.id) }
                                    )
                                }
                            }
                        }
                        is UiState.Error -> {
                            item {
                                ErrorContent(
                                    message = favoriteRoutes.message,
                                    onRetry = { homeViewModel.loadInitialData() }
                                )
                            }
                        }
                        else -> Unit
                    }

                    // Todas las Rutas
                    when (val allRoutes = uiState.allRoutes) {
                        is UiState.Loading -> {
                            item {
                                LoadingOverlay()
                            }
                        }
                        is UiState.Success -> {
                            item {
                                RouteSection(
                                    title = "Todas las Rutas",
                                    subtitle = "Explora todas las rutas disponibles",
                                    routes = allRoutes.data,
                                    onRouteClick = { homeViewModel.selectRoute(it.id) },
                                    getFavoriteStatus = { id -> id in uiState.routesFavoriteStatus },
                                    onFavoriteClick = { homeViewModel.toggleFavorite(it.id) }
                                )
                            }
                        }
                        is UiState.Error -> {
                            item {
                                ErrorContent(
                                    message = allRoutes.message,
                                    onRetry = { homeViewModel.loadAllRoutes() }
                                )
                            }
                        }
                        else -> Unit
                    }
                }
            }

            // Diálogo de detalles de ruta
            when (val selectedRoute = uiState.selectedRoute) {
                is UiState.Success -> {
                    selectedRoute.data?.let { route ->
                        RouteDetailDialog(
                            route = route,
                            pointsOfInterest = uiState.pointsOfInterest,
                            isFavorite = route.id in uiState.routesFavoriteStatus,
                            onFavoriteClick = { homeViewModel.toggleFavorite(route.id) },
                            onDismiss = { homeViewModel.clearSelectedRoute() },
                            onStartRoute = { homeViewModel.startRoute(route.id) }
                        )
                    }
                }
                else -> Unit
            }
        }
    }
}

@Composable
private fun RouteSection(
    title: String,
    subtitle: String,
    routes: List<RouteModel>,
    onRouteClick: (RouteModel) -> Unit,
    getFavoriteStatus: (Int) -> Boolean,
    onFavoriteClick: (RouteModel) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge
        )
        Text(
            text = subtitle,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        routes.forEach { route ->
            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
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