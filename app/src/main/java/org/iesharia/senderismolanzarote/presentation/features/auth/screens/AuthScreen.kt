package org.iesharia.senderismolanzarote.presentation.features.auth.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import org.iesharia.senderismolanzarote.presentation.common.components.LoadingOverlay
import org.iesharia.senderismolanzarote.presentation.features.auth.components.*
import org.iesharia.senderismolanzarote.presentation.features.auth.viewmodel.AuthViewModel
import org.iesharia.senderismolanzarote.presentation.core.base.UiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AuthScreen(
    onNavigateToHome: () -> Unit,
    viewModel: AuthViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val scrollState = rememberScrollState()
    var snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(uiState.authState) {
        if (uiState.authState is UiState.Success) {
            onNavigateToHome()
        }
    }

    LaunchedEffect(uiState.registerState) {
        if (uiState.registerState is UiState.Success) {
            onNavigateToHome()
        }
    }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        contentColor = MaterialTheme.colorScheme.onBackground,
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState)
                    .padding(horizontal = 24.dp)
                    .windowInsetsPadding(WindowInsets.systemBars)
                    .consumeWindowInsets(WindowInsets.ime),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AuthLogo()

                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    shape = MaterialTheme.shapes.large,
                    tonalElevation = 1.dp
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(24.dp)
                    ) {
                        AuthToggle(
                            isLogin = uiState.isLogin,
                            onToggle = { viewModel.setLoginMode(it) }
                        )

                        AuthForm(
                            isLogin = uiState.isLogin,
                            isLoading = uiState.authState is UiState.Loading ||
                                    uiState.registerState is UiState.Loading,
                            onLogin = { email, password ->
                                viewModel.login(email, password)
                            },
                            onRegister = { username, email, password, firstName, lastName ->
                                viewModel.register(username, email, password, firstName, lastName)
                            }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))
            }

            // Error handling
            LaunchedEffect(uiState.authState, uiState.registerState) {
                val errorState = when {
                    uiState.authState is UiState.Error -> uiState.authState as UiState.Error
                    uiState.registerState is UiState.Error -> uiState.registerState as UiState.Error
                    else -> null
                }

                errorState?.let {
                    snackbarHostState.showSnackbar(
                        message = it.message,
                        actionLabel = "Cerrar",
                        duration = SnackbarDuration.Long
                    )
                    viewModel.clearError()
                }
            }

            if (uiState.authState is UiState.Loading ||
                uiState.registerState is UiState.Loading
            ) {
                LoadingOverlay()
            }
        }
    }
}