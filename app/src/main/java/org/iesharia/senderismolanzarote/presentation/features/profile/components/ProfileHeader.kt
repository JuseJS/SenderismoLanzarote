package org.iesharia.senderismolanzarote.presentation.features.profile.components

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import org.iesharia.senderismolanzarote.R
import org.iesharia.senderismolanzarote.domain.model.user.UserModel

@Composable
fun ProfileHeader(
    user: UserModel,
    isEditMode: Boolean,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Log.d("ProfileHeader", "User: $user")
            // Profile Image Placeholder
            Surface(
                modifier = Modifier
                    .size(120.dp)
                    .padding(bottom = 16.dp),
                shape = MaterialTheme.shapes.medium,
                color = MaterialTheme.colorScheme.secondaryContainer
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_account_circle),
                    contentDescription = "Foto de perfil",
                    contentScale = ContentScale.Crop
                )
            }

            if (!isEditMode) {
                Text(
                    text = "${user.firstName} ${user.lastName}",
                    style = MaterialTheme.typography.headlineMedium
                )
                Text(
                    text = user.email,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}