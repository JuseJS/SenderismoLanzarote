package org.iesharia.senderismolanzarote.data.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import org.iesharia.senderismolanzarote.domain.model.auth.AuthSession
import org.iesharia.senderismolanzarote.presentation.common.theme.ThemeConfig
import javax.inject.Inject
import javax.inject.Singleton

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "app_preferences")

@Singleton
class AppDataStore @Inject constructor(private val context: Context) {
    private val isInitializedKey = booleanPreferencesKey("is_db_initialized")
    private val themeConfigKey = stringPreferencesKey("theme_config")
    private val userIdKey = intPreferencesKey("user_id")
    private val authTokenKey = stringPreferencesKey("auth_token")

    val isInitialized: Flow<Boolean> = context.dataStore.data
        .map { preferences ->
            preferences[isInitializedKey] ?: false
        }

    val themeConfig: Flow<ThemeConfig> = context.dataStore.data
        .map { preferences ->
            when (preferences[themeConfigKey]) {
                "dark" -> ThemeConfig.Dark
                "light" -> ThemeConfig.Light
                else -> ThemeConfig.FollowSystem
            }
        }

    val isAuthenticated: Flow<Boolean> = context.dataStore.data
        .map { preferences ->
            preferences[userIdKey] != null && preferences[authTokenKey] != null
        }

    val authSession: Flow<AuthSession?> = context.dataStore.data
        .map { preferences ->
            val userId = preferences[userIdKey]
            val token = preferences[authTokenKey]
            if (userId != null && token != null) {
                AuthSession(userId, token)
            } else null
        }

    suspend fun setInitialized() {
        context.dataStore.edit { preferences ->
            preferences[isInitializedKey] = true
        }
    }

    suspend fun setNotInitialized() {
        context.dataStore.edit { preferences ->
            preferences[isInitializedKey] = false
        }
    }

    suspend fun setThemeConfig(config: ThemeConfig) {
        context.dataStore.edit { preferences ->
            preferences[themeConfigKey] = when (config) {
                is ThemeConfig.Dark -> "dark"
                is ThemeConfig.Light -> "light"
                is ThemeConfig.FollowSystem -> "system"
            }
        }
    }

    suspend fun getCurrentUserId(): Int? {
        return context.dataStore.data.first()[userIdKey]
    }

    suspend fun getCurrentToken(): String? {
        return context.dataStore.data.first()[authTokenKey]
    }

    suspend fun saveAuthSession(userId: Int, token: String) {
        context.dataStore.edit { preferences ->
            preferences[userIdKey] = userId
            preferences[authTokenKey] = token
        }
    }

    suspend fun clearAuthSession() {
        context.dataStore.edit { preferences ->
            preferences.remove(userIdKey)
            preferences.remove(authTokenKey)
        }
    }
}