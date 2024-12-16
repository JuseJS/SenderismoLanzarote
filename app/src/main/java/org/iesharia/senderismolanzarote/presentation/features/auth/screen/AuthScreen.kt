package org.iesharia.senderismolanzarote.presentation.auth.screen

import androidx.compose.animation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import org.iesharia.senderismolanzarote.presentation.auth.component.AuthForm
import org.iesharia.senderismolanzarote.presentation.auth.component.AuthLogo
import org.iesharia.senderismolanzarote.presentation.auth.component.AuthToggle
import org.iesharia.senderismolanzarote.presentation.auth.state.AuthState
import org.iesharia.senderismolanzarote.presentation.auth.state.RegisterState
import org.iesharia.senderismolanzarote.presentation.auth.viewmodel.AuthViewModel

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AuthScreen(
    onNavigateToHome: () -> Unit,
    viewModel: AuthViewModel = hiltViewModel()
) {
    val authState by viewModel.authState.collectAsState()
    val registerState by viewModel.registerState.collectAsState()
    var isLogin by remember { mutableStateOf(true) }

    LaunchedEffect(authState) {
        if (authState is AuthState.Success) {
            onNavigateToHome()
        }
    }

    LaunchedEffect(registerState) {
        if (registerState is RegisterState.Success) {
            onNavigateToHome()
        }
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                AuthLogo(
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier
                        .size(120.dp)
                        .padding(bottom = 32.dp)
                )

                if (authState is AuthState.Error) {
                    Text(
                        text = (authState as AuthState.Error).message,
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )
                }

                if (registerState is RegisterState.Error) {
                    Text(
                        text = (registerState as RegisterState.Error).message,
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )
                }

                AuthToggle(
                    isLogin = isLogin,
                    onToggle = { isLogin = it }
                )

                Spacer(modifier = Modifier.height(24.dp))

                AnimatedContent(
                    targetState = isLogin,
                    transitionSpec = {
                        fadeIn() + slideInHorizontally() togetherWith
                                fadeOut() + slideOutHorizontally()
                    },
                    label = "AuthFormAnimation"
                ) { isLoginState ->
                    AuthForm(
                        isLogin = isLoginState,
                        isLoading = authState is AuthState.Loading || registerState is RegisterState.Loading,
                        onLogin = { email, password ->
                            viewModel.login(email, password)
                        },
                        onRegister = { username, email, password, firstName, lastName ->
                            viewModel.register(username, email, password, firstName, lastName)
                        }
                    )
                }
            }

            // Loading overlay
            if (authState is AuthState.Loading || registerState is RegisterState.Loading) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .size(48.dp)
                        .align(Alignment.Center)
                )
            }
        }
    }
}
