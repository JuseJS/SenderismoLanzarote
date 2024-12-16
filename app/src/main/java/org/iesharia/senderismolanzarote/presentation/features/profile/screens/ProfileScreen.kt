package org.iesharia.senderismolanzarote.presentation.features.profile.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
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
                }
            )
        }
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
                        ProfileHeader(
                            user = profileState.data.user,
                            isEditMode = uiState.isEditMode,
                            onEditClick = { viewModel.toggleEditMode() },
                            onSaveClick = { viewModel.saveChanges() }
                        )

                        PreferencesForm(
                            preferences = profileState.data.preferences,
                            difficultyLevels = viewModel.difficultyLevels,
                            isEditMode = uiState.isEditMode,
                            onPreferencesChange = { preferences ->
                                viewModel.updatePreferences(preferences)
                            }
                        )

                        // Aquí irían más secciones como actividades recientes
                        // y rutas favoritas
                    }
                }
                is UiState.Error -> {
                    ErrorContent(
                        message = profileState.message,
                        onRetry = { viewModel.loadProfile() }
                    )
                }
                else -> Unit
            }
        }
    }
}