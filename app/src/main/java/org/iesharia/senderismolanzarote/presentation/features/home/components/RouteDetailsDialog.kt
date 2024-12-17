package org.iesharia.senderismolanzarote.presentation.features.home.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.iesharia.senderismolanzarote.domain.model.route.main.RouteModel
import org.iesharia.senderismolanzarote.domain.model.route.main.PointOfInterestModel
import org.iesharia.senderismolanzarote.presentation.core.base.UiState

@Composable
fun RouteDetailDialog(
    route: RouteModel,
    pointsOfInterest: UiState<List<PointOfInterestModel>>,
    isFavorite: Boolean,
    onFavoriteClick: () -> Unit,
    onDismiss: () -> Unit,
    onStartRoute: () -> Unit,
    modifier: Modifier = Modifier
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        modifier = modifier.fillMaxWidth(),
        title = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = route.name,
                    style = MaterialTheme.typography.headlineSmall
                )
                IconButton(onClick = onFavoriteClick) {
                    Icon(
                        imageVector = if (isFavorite)
                            Icons.Default.Favorite
                        else
                            Icons.Default.FavoriteBorder,
                        contentDescription = if (isFavorite)
                            "Quitar de favoritos"
                        else
                            "Añadir a favoritos",
                        tint = if (isFavorite)
                            MaterialTheme.colorScheme.primary
                        else
                            MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        },
        text = {
            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .padding(vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                RouteInfoSection(route)

                when (pointsOfInterest) {
                    is UiState.Loading -> {
                        CircularProgressIndicator(
                            modifier = Modifier.align(Alignment.CenterHorizontally)
                        )
                    }
                    is UiState.Success -> {
                        if (pointsOfInterest.data.isNotEmpty()) {
                            PointsOfInterestSection(pointsOfInterest.data)
                        }
                    }
                    is UiState.Error -> {
                        Text(
                            text = pointsOfInterest.message,
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                    else -> Unit
                }
            }
        },
        confirmButton = {
            Button(
                onClick = onStartRoute,
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(
                    imageVector = Icons.Default.PlayArrow,
                    contentDescription = null,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("Iniciar Ruta")
            }
        },
        dismissButton = {
            TextButton(
                onClick = onDismiss,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Cerrar")
            }
        }
    )
}

@Composable
private fun RouteInfoSection(route: RouteModel) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = "Información General",
            style = MaterialTheme.typography.titleMedium
        )
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                InfoRow(
                    icon = Icons.Default.Timeline,
                    label = "Dificultad",
                    value = route.difficultyLevelModel.name
                )
                InfoRow(
                    icon = Icons.Default.Straighten,
                    label = "Distancia",
                    value = "${route.distanceKm}km"
                )
                InfoRow(
                    icon = Icons.Default.Timer,
                    label = "Duración estimada",
                    value = route.estimatedDuration.toString()
                )
                InfoRow(
                    icon = Icons.Default.Terrain,
                    label = "Desnivel",
                    value = "${route.elevationGain}m"
                )
                if (route.isCircular) {
                    InfoRow(
                        icon = Icons.Default.ChangeHistory,
                        label = "Tipo",
                        value = "Circular"
                    )
                }
            }
        }

        Text(
            text = "Descripción",
            style = MaterialTheme.typography.titleSmall
        )
        Text(
            text = route.description,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Composable
private fun PointsOfInterestSection(
    pointsOfInterest: List<PointOfInterestModel>
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = "Puntos de Interés",
            style = MaterialTheme.typography.titleMedium
        )
        pointsOfInterest.forEach { poi ->
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = poi.name,
                        style = MaterialTheme.typography.titleSmall
                    )
                    Text(
                        text = poi.description,
                        style = MaterialTheme.typography.bodyMedium
                    )
                    if (poi.accessibleReducedMobility) {
                        Text(
                            text = "♿ Accesible",
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun InfoRow(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    label: String,
    value: String
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            modifier = Modifier.size(20.dp)
        )
        Text(
            text = "$label: $value",
            style = MaterialTheme.typography.bodyMedium
        )
    }
}