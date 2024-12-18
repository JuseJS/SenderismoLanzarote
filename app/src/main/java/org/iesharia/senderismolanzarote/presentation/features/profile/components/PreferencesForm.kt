package org.iesharia.senderismolanzarote.presentation.features.profile.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.iesharia.senderismolanzarote.domain.model.route.reference.DifficultyLevelModel
import org.iesharia.senderismolanzarote.domain.model.user.UserPreferencesModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PreferencesForm(
    preferences: UserPreferencesModel?,
    difficultyLevels: List<DifficultyLevelModel>,
    isEditMode: Boolean,
    onPreferencesChange: (UserPreferencesModel) -> Unit,
    modifier: Modifier = Modifier
) {
    var maxDistance by remember(preferences) {
        mutableStateOf(preferences?.maxDistanceKm?.toString() ?: "10.0")
    }
    var maxDuration by remember(preferences) {
        mutableStateOf(preferences?.maxDurationMinutes?.toString() ?: "120")
    }
    var selectedDifficultyId by remember(preferences) {
        mutableIntStateOf(preferences?.preferredDifficultyLevel?.id ?: difficultyLevels.firstOrNull()?.id ?: 1)
    }
    var isDropdownExpanded by remember { mutableStateOf(false) }

    LaunchedEffect(preferences) {
        preferences?.let {
            maxDistance = it.maxDistanceKm.toString()
            maxDuration = it.maxDurationMinutes.toString()
            selectedDifficultyId = it.preferredDifficultyLevel.id
        }
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
                text = "Preferencias de Rutas",
                style = MaterialTheme.typography.titleLarge
            )

            if (isEditMode) {
                ExposedDropdownMenuBox(
                    expanded = isDropdownExpanded,
                    onExpandedChange = { isDropdownExpanded = it },
                ) {
                    OutlinedTextField(
                        value = difficultyLevels.find { it.id == selectedDifficultyId }?.name ?: "",
                        onValueChange = { },
                        readOnly = true,
                        label = { Text("Dificultad preferida") },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = isDropdownExpanded) },
                        modifier = Modifier
                            .menuAnchor()
                            .fillMaxWidth()
                    )

                    ExposedDropdownMenu(
                        expanded = isDropdownExpanded,
                        onDismissRequest = { isDropdownExpanded = false }
                    ) {
                        difficultyLevels.forEach { difficulty ->
                            DropdownMenuItem(
                                text = {
                                    Text(
                                        text = "${difficulty.name} - ${difficulty.description}"
                                    )
                                },
                                onClick = {
                                    selectedDifficultyId = difficulty.id
                                    isDropdownExpanded = false
                                    preferences?.let { prefs ->
                                        onPreferencesChange(prefs.copy(
                                            preferredDifficultyLevel = difficulty
                                        ))
                                    }
                                }
                            )
                        }
                    }
                }

                OutlinedTextField(
                    value = maxDistance,
                    onValueChange = { newValue ->
                        if (newValue.isEmpty() || newValue.matches(Regex("^\\d*\\.?\\d*$"))) {
                            maxDistance = newValue
                            newValue.toBigDecimalOrNull()?.let { distance ->
                                preferences?.let { prefs ->
                                    onPreferencesChange(prefs.copy(
                                        maxDistanceKm = distance
                                    ))
                                }
                            }
                        }
                    },
                    label = { Text("Distancia máxima (km)") },
                    modifier = Modifier.fillMaxWidth(),
                    supportingText = { Text("Introduce la distancia máxima que deseas recorrer") }
                )

                OutlinedTextField(
                    value = maxDuration,
                    onValueChange = { newValue ->
                        if (newValue.isEmpty() || newValue.matches(Regex("^\\d+$"))) {
                            maxDuration = newValue
                            newValue.toIntOrNull()?.let { duration ->
                                preferences?.let { prefs ->
                                    onPreferencesChange(prefs.copy(
                                        maxDurationMinutes = duration
                                    ))
                                }
                            }
                        }
                    },
                    label = { Text("Duración máxima (minutos)") },
                    modifier = Modifier.fillMaxWidth(),
                    supportingText = { Text("Introduce la duración máxima que deseas caminar") }
                )
            } else {
                // Vista de solo lectura con datos reales
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    ListItem(
                        headlineContent = {
                            Text(preferences?.preferredDifficultyLevel?.name ?: "No establecida")
                        },
                        supportingContent = {
                            Text(preferences?.preferredDifficultyLevel?.description ?: "")
                        }
                    )
                    ListItem(
                        headlineContent = {
                            Text("${preferences?.maxDistanceKm ?: 10.0} km")
                        },
                        supportingContent = { Text("Distancia máxima por ruta") }
                    )
                    ListItem(
                        headlineContent = {
                            Text("${preferences?.maxDurationMinutes ?: 120} minutos")
                        },
                        supportingContent = { Text("Duración máxima por ruta") }
                    )
                }
            }
        }
    }
}