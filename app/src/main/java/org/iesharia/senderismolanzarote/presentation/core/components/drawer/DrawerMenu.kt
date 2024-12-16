package org.iesharia.senderismolanzarote.presentation.core.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import org.iesharia.senderismolanzarote.presentation.navigation.Screen
import org.iesharia.senderismolanzarote.R
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DrawerMenu(
    navController: NavController,
    onLogout: () -> Unit,
    onCloseDrawer: () -> Unit,
    modifier: Modifier = Modifier
) {
    ModalDrawerSheet(modifier = modifier) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // Header con foto de perfil
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .padding(vertical = 16.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.default_profile),
                    contentDescription = "Foto de perfil",
                    modifier = Modifier
                        .size(120.dp)
                        .align(Alignment.Center),
                    contentScale = ContentScale.Crop
                )
            }

            Divider()

            NavigationDrawerItem(
                icon = { Icon(Icons.Default.Home, "Inicio") },
                label = { Text("Inicio") },
                selected = false,
                onClick = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Home.route) { inclusive = true }
                    }
                    onCloseDrawer()
                }
            )

            NavigationDrawerItem(
                icon = { Icon(Icons.Default.Person, "Perfil") },
                label = { Text("Perfil") },
                selected = false,
                onClick = {
                    navController.navigate(Screen.Profile.route)
                    onCloseDrawer()
                }
            )

            Spacer(modifier = Modifier.weight(1f))

            // Botón de cerrar sesión
            NavigationDrawerItem(
                icon = { Icon(Icons.AutoMirrored.Filled.ExitToApp, contentDescription = "Cerrar sesión") },
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