package org.iesharia.senderismolanzarote.presentation.features.home.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.iesharia.senderismolanzarote.domain.model.route.main.RouteModel

@Composable
fun RouteCard(
    route: RouteModel,
    isFavorite: Boolean,
    onFavoriteClick: () -> Unit,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        onClick = onClick,
        modifier = modifier.fillMaxWidth()
    ) {
        Box(modifier = Modifier.fillMaxWidth()) {
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
                    text = "Duración Estimada: ${route.estimatedDuration}",
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            IconButton(
                onClick = onFavoriteClick,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(8.dp)
            ) {
                Icon(
                    imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                    contentDescription = if (isFavorite) "Quitar de favoritos" else "Añadir a favoritos",
                    tint = if (isFavorite) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
                )
            }
        }
    }
}