package org.iesharia.senderismolanzarote.presentation.common.theme

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.iesharia.senderismolanzarote.data.datastore.AppDataStore
import javax.inject.Inject

sealed interface ThemeConfig {
    object FollowSystem : ThemeConfig
    object Dark : ThemeConfig
    object Light : ThemeConfig
}

@HiltViewModel
class ThemeViewModel @Inject constructor(
    private val appDataStore: AppDataStore
) : ViewModel() {
    private val _themeConfig = MutableStateFlow<ThemeConfig>(ThemeConfig.FollowSystem)
    val themeConfig = _themeConfig.asStateFlow()

    init {
        loadThemeConfig()
    }

    private fun loadThemeConfig() {
        viewModelScope.launch {
            appDataStore.themeConfig.collect { config ->
                _themeConfig.value = config
            }
        }
    }

    fun setThemeConfig(config: ThemeConfig) {
        viewModelScope.launch {
            appDataStore.setThemeConfig(config)
        }
    }

    fun toggleTheme(isSystemInDark: Boolean) {
        val newConfig = when (_themeConfig.value) {
            is ThemeConfig.FollowSystem -> if (isSystemInDark)
                ThemeConfig.Light else ThemeConfig.Dark
            is ThemeConfig.Dark -> ThemeConfig.Light
            is ThemeConfig.Light -> ThemeConfig.Dark
        }
        setThemeConfig(newConfig)
    }
}