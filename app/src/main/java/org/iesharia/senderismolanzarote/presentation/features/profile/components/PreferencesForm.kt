package org.iesharia.senderismolanzarote.presentation.features.profile.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.iesharia.senderismolanzarote.domain.model.user.UserPreferencesModel
import org.iesharia.senderismolanzarote.domain.model.route.reference.DifficultyLevelModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PreferencesForm(
    preferences: UserPreferencesModel?,
    difficultyLevels: List<DifficultyLevelModel>,
    isEditMode: Boolean,
    onPreferencesChange: (UserPreferencesModel) -> Unit,
    modifier: Modifier = Modifier
) {
    var maxDistance by remember { mutableStateOf(preferences?.maxDistanceKm?.toString() ?: "") }
    var maxDuration by remember { mutableStateOf(preferences?.maxDurationMinutes?.toString() ?: "") }
    var selectedDifficultyId by remember {
        mutableIntStateOf(preferences?.preferredDifficultyLevel?.id ?: 1)
    }

    Card(
        modifier = modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Preferencias",
                style = MaterialTheme.typography.titleLarge
            )

            if (isEditMode) {
                // Campos editables
                OutlinedTextField(
                    value = maxDistance,
                    onValueChange = {
                        maxDistance = it
                        preferences?.let { prefs ->
                            onPreferencesChange(prefs.copy(
                                maxDistanceKm = it.toBigDecimalOrNull() ?: java.math.BigDecimal.ZERO
                            ))
                        }
                    },
                    label = { Text("Distancia máxima (km)") },
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = maxDuration,
                    onValueChange = {
                        maxDuration = it
                        preferences?.let { prefs ->
                            onPreferencesChange(prefs.copy(
                                maxDurationMinutes = it.toIntOrNull() ?: 0
                            ))
                        }
                    },
                    label = { Text("Duración máxima (minutos)") },
                    modifier = Modifier.fillMaxWidth()
                )

                ExposedDropdownMenuBox(
                    expanded = false,
                    onExpandedChange = { },
                ) {
                    OutlinedTextField(
                        value = difficultyLevels.find { it.id == selectedDifficultyId }?.name ?: "",
                        onValueChange = { },
                        readOnly = true,
                        label = { Text("Dificultad preferida") },
                        modifier = Modifier.menuAnchor()
                    )
                }
            } else {
                // Vista de solo lectura
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(
                        text = "Distancia máxima: ${preferences?.maxDistanceKm ?: 0} km",
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Text(
                        text = "Duración máxima: ${preferences?.maxDurationMinutes ?: 0} minutos",
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Text(
                        text = "Dificultad preferida: ${preferences?.preferredDifficultyLevel?.name ?: "No establecida"}",
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }
        }
    }
}