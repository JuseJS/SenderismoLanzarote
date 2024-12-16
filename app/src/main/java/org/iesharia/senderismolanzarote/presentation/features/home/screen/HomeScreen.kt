package org.iesharia.senderismolanzarote.presentation.home.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import org.iesharia.senderismolanzarote.presentation.home.state.*
import org.iesharia.senderismolanzarote.presentation.home.viewmodel.HomeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            when (uiState.activeRoute) {
                is ActiveRouteState.None -> {
                    // Mostrar listas de rutas
                    RoutesList(
                        uiState = uiState,
                        onRouteClick = { routeId ->
                            viewModel.setActiveRoute(routeId)
                        }
                    )
                }
                is ActiveRouteState.Loading -> {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
                is ActiveRouteState.Success -> {
                    // Aquí se integrará el componente del mapa
                    // Por ahora solo mostraremos un placeholder
                    Text("Mapa - Pendiente de implementar")
                }
                is ActiveRouteState.Error -> {
                    ErrorContent(
                        message = (uiState.activeRoute as ActiveRouteState.Error).message,
                        onRetry = { viewModel.clearActiveRoute() }
                    )
                }
            }
        }
    }
}

@Composable
private fun RoutesList(
    uiState: HomeUiState,
    onRouteClick: (Int) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Sección de rutas sugeridas
        Text(
            text = "Rutas sugeridas",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        when (val suggestedState = uiState.suggestedRoutes) {
            is SuggestedRoutesState.Loading -> {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            }
            is SuggestedRoutesState.Success -> {
                if (suggestedState.routes.isEmpty()) {
                    Text(
                        text = "Ninguna ruta cumple con tus preferencias",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                } else {
                    // Aquí se integrará el componente de lista de rutas
                    Text("Lista de rutas sugeridas - Pendiente de implementar")
                }
            }
            is SuggestedRoutesState.Error -> {
                Text(
                    text = suggestedState.message,
                    color = MaterialTheme.colorScheme.error
                )
            }
            else -> Unit
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Sección de todas las rutas
        Text(
            text = "Todas las rutas",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        when (val allRoutesState = uiState.allRoutes) {
            is AllRoutesState.Loading -> {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            }
            is AllRoutesState.Success -> {
                // Aquí se integrará el componente de lista de rutas
                Text("Lista de todas las rutas - Pendiente de implementar")
            }
            is AllRoutesState.Error -> {
                Text(
                    text = allRoutesState.message,
                    color = MaterialTheme.colorScheme.error
                )
            }
            else -> Unit
        }
    }
}

@Composable
private fun ErrorContent(
    message: String,
    onRetry: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = message,
            color = MaterialTheme.colorScheme.error,
            style = MaterialTheme.typography.bodyLarge
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = onRetry) {
            Text("Reintentar")
        }
    }
}