package org.iesharia.senderismolanzarote.presentation.features.home.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.iesharia.senderismolanzarote.domain.model.route.main.RouteModel
import org.iesharia.senderismolanzarote.presentation.core.base.UiState
import org.iesharia.senderismolanzarote.presentation.common.components.ErrorContent
import org.iesharia.senderismolanzarote.presentation.common.components.LoadingOverlay

@Composable
fun RoutesList(
    title: String,
    routes: UiState<List<RouteModel>>,
    onRouteClick: (RouteModel) -> Unit,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onBackground
        )

        when (routes) {
            is UiState.Loading -> {
                LoadingOverlay(
                    modifier = Modifier.height(200.dp)
                )
            }
            is UiState.Success -> {
                if (routes.data.isEmpty()) {
                    EmptyRoutesList()
                } else {
                    RoutesGrid(
                        routes = routes.data,
                        onRouteClick = onRouteClick
                    )
                }
            }
            is UiState.Error -> {
                ErrorContent(
                    message = routes.message,
                    onRetry = onRetry
                )
            }
            else -> Unit
        }
    }
}

@Composable
private fun EmptyRoutesList() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "No hay rutas disponibles",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun RoutesGrid(
    routes: List<RouteModel>,
    onRouteClick: (RouteModel) -> Unit
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(routes) { route ->
            Card(
                onClick = { onRouteClick(route) },
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = route.name,
                        style = MaterialTheme.typography.titleMedium
                    )
                    Text(
                        text = "Dificultad: ${route.difficultyLevelModel.name}",
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Text(
                        text = "Distancia: ${route.distanceKm}km",
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Text(
                        text = "Duración Estimada: ${route.estimatedDuration}h",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }
    }
}