package org.iesharia.senderismolanzarote.data.repository.auth

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.iesharia.senderismolanzarote.data.datastore.AppDataStore
import org.iesharia.senderismolanzarote.domain.model.auth.AuthSession
import org.iesharia.senderismolanzarote.domain.repository.user.UserRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepository @Inject constructor(
    private val appDataStore: AppDataStore,
    private val userRepository: UserRepository
) {
    val isAuthenticated: Flow<Boolean> = appDataStore.isAuthenticated

    val currentSession: Flow<AuthSession?> = appDataStore.authSession.map { session ->
        session?.let { AuthSession(userId = it.userId, token = it.token) }
    }

    suspend fun getCurrentUserId(): Int {
        return appDataStore.getCurrentUserId() ?: throw Exception("No hay sesión activa")
    }

    suspend fun getCurrentUserToken(): String {
        return appDataStore.getCurrentToken() ?: throw Exception("No hay sesión activa")
    }

    suspend fun saveAuthSession(userId: Int, token: String) {
        // Verificar que el usuario existe antes de guardar la sesión
        userRepository.getUserById(userId)?.let {
            appDataStore.saveAuthSession(userId, token)
        } ?: throw Exception("Usuario no encontrado")
    }

    suspend fun validateSession(token: String): Boolean {
        // Aquí podrías implementar la validación del token
        // Por ahora simplemente verificamos que exista
        return token.isNotBlank()
    }

    suspend fun refreshToken(userId: Int): String {
        // Aquí podrías implementar la lógica para refrescar el token
        // Por ahora generamos uno nuevo
        val newToken = java.util.UUID.randomUUID().toString()
        appDataStore.saveAuthSession(userId, newToken)
        return newToken
    }

    suspend fun clearAuthSession() {
        appDataStore.clearAuthSession()
    }
}