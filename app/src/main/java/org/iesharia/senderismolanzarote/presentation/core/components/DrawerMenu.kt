package org.iesharia.senderismolanzarote.presentation.core.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.Image
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import org.iesharia.senderismolanzarote.R
import org.iesharia.senderismolanzarote.presentation.common.navigation.Screen

data class DrawerMenuItem(
    val icon: @Composable () -> Unit,
    val label: String,
    val onClick: () -> Unit,
    val isSelected: Boolean = false
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DrawerMenu(
    currentRoute: String,
    onNavigateToHome: () -> Unit,
    onNavigateToProfile: () -> Unit,
    onLogout: () -> Unit,
    onCloseDrawer: () -> Unit,
    modifier: Modifier = Modifier,
    username: String = "",
    email: String = ""
) {
    ModalDrawerSheet(modifier = modifier) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // Header
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .padding(vertical = 16.dp)
            ) {
                Column(
                    modifier = Modifier.align(Alignment.Center),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Profile Image
                    Surface(
                        modifier = Modifier.size(80.dp),
                        shape = MaterialTheme.shapes.medium,
                        tonalElevation = 1.dp
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_account_circle),
                            contentDescription = "Foto de perfil",
                            contentScale = ContentScale.Crop
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    // User Info
                    if (username.isNotEmpty()) {
                        Text(
                            text = username,
                            style = MaterialTheme.typography.titleMedium
                        )
                    }
                    if (email.isNotEmpty()) {
                        Text(
                            text = email,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }

            Divider()

            // Menu Items
            val menuItems = listOf(
                DrawerMenuItem(
                    icon = { Icon(Icons.Default.Home, "Inicio") },
                    label = "Inicio",
                    onClick = {
                        onNavigateToHome()
                        onCloseDrawer()
                    },
                    isSelected = currentRoute == Screen.Home.route
                ),
                DrawerMenuItem(
                    icon = { Icon(Icons.Default.Person, "Perfil") },
                    label = "Perfil",
                    onClick = {
                        onNavigateToProfile()
                        onCloseDrawer()
                    },
                    isSelected = currentRoute == Screen.Profile.route
                )
            )

            menuItems.forEach { item ->
                NavigationDrawerItem(
                    icon = { item.icon() },
                    label = { Text(item.label) },
                    selected = item.isSelected,
                    onClick = item.onClick
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            // Logout Button
            NavigationDrawerItem(
                icon = {
                    Icon(
                        Icons.AutoMirrored.Filled.ExitToApp,
                        contentDescription = "Cerrar sesión"
                    )
                },
                label = { Text("Cerrar sesión") },
                selected = false,
                onClick = {
                    onLogout()
                    onCloseDrawer()
                }
            )
        }
    }
}