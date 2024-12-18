package org.iesharia.senderismolanzarote.presentation.features.profile.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.launch
import org.iesharia.senderismolanzarote.presentation.common.components.ErrorContent
import org.iesharia.senderismolanzarote.presentation.common.components.LoadingOverlay
import org.iesharia.senderismolanzarote.presentation.features.profile.components.*
import org.iesharia.senderismolanzarote.presentation.features.profile.viewmodel.ProfileViewModel
import org.iesharia.senderismolanzarote.presentation.core.base.UiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    drawerState: DrawerState,
    onNavigateToHome: () -> Unit,
    onLogout: () -> Unit,
    viewModel: ProfileViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val scope = rememberCoroutineScope()
    var showConfirmDialog by remember { mutableStateOf(false) }

    // Manejo de Snackbars
    val snackbarHostState = remember { SnackbarHostState() }

    // Diálogo de confirmación para cancelar cambios
    if (showConfirmDialog) {
        AlertDialog(
            onDismissRequest = { showConfirmDialog = false },
            title = { Text("Cancelar cambios") },
            text = { Text("¿Estás seguro de que quieres descartar los cambios?") },
            confirmButton = {
                TextButton(
                    onClick = {
                        viewModel.cancelChanges()
                        showConfirmDialog = false
                    }
                ) {
                    Text("Sí, descartar")
                }
            },
            dismissButton = {
                TextButton(onClick = { showConfirmDialog = false }) {
                    Text("No, continuar editando")
                }
            }
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Perfil") },
                navigationIcon = {
                    IconButton(
                        onClick = { scope.launch { drawerState.open() } }
                    ) {
                        Icon(Icons.Default.Menu, "Menú")
                    }
                },
                actions = {
                    if (uiState.isEditMode) {
                        // Botones de guardar y cancelar cuando está en modo edición
                        IconButton(onClick = { showConfirmDialog = true }) {
                            Icon(Icons.Default.Close, "Cancelar cambios")
                        }
                        IconButton(onClick = { viewModel.saveChanges() }) {
                            Icon(Icons.Default.Save, "Guardar cambios")
                        }
                    } else {
                        // Botón de editar cuando no está en modo edición
                        IconButton(onClick = { viewModel.toggleEditMode() }) {
                            Icon(Icons.Default.Edit, "Editar perfil")
                        }
                    }
                }
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            when (val profileState = uiState.profile) {
                is UiState.Loading -> LoadingOverlay()
                is UiState.Success -> {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        // Información del perfil
                        ProfileHeader(
                            user = profileState.data.user,
                            isEditMode = uiState.isEditMode
                        )

                        // Preferencias del usuario
                        PreferencesForm(
                            preferences = profileState.data.preferences,
                            difficultyLevels = viewModel.difficultyLevels,
                            isEditMode = uiState.isEditMode,
                            onPreferencesChange = { preferences ->
                                viewModel.updatePreferences(preferences)
                            }
                        )

                        // Actividades recientes
                        if (profileState.data.activities.isNotEmpty()) {
                            Card(
                                modifier = Modifier.fillMaxWidth(),
                                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                            ) {
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(16.dp)
                                ) {
                                    Text(
                                        text = "Actividades Recientes",
                                        style = MaterialTheme.typography.titleLarge,
                                        modifier = Modifier.padding(bottom = 8.dp)
                                    )
                                    // Aquí irían las actividades recientes
                                }
                            }
                        }

                        // Rutas favoritas
                        if (profileState.data.favorites.isNotEmpty()) {
                            Card(
                                modifier = Modifier.fillMaxWidth(),
                                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                            ) {
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(16.dp)
                                ) {
                                    Text(
                                        text = "Rutas Favoritas",
                                        style = MaterialTheme.typography.titleLarge,
                                        modifier = Modifier.padding(bottom = 8.dp)
                                    )
                                    // Aquí irían las rutas favoritas
                                }
                            }
                        }
                    }
                }
                is UiState.Error -> {
                    ErrorContent(
                        message = profileState.message,
                        onRetry = { viewModel.reloadProfile() }
                    )
                }
                else -> Unit
            }
        }
    }

    // Efecto para mostrar mensajes de error o éxito
    LaunchedEffect(uiState.profile) {
        when (val state = uiState.profile) {
            is UiState.Error -> {
                snackbarHostState.showSnackbar(
                    message = state.message,
                    duration = SnackbarDuration.Long
                )
            }
            else -> Unit
        }
    }
}