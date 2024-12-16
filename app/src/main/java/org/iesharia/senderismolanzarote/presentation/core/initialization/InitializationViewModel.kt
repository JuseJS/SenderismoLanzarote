package org.iesharia.senderismolanzarote.presentation.initialization

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.iesharia.senderismolanzarote.domain.usecase.sample.CheckAndInitializeDatabaseUseCase
import javax.inject.Inject

@HiltViewModel
class InitializationViewModel @Inject constructor(
    private val checkAndInitializeDatabaseUseCase: CheckAndInitializeDatabaseUseCase
) : ViewModel() {
    private val _isInitialized = MutableStateFlow(false)
    val isInitialized = _isInitialized.asStateFlow()

    private val _initializationError = MutableStateFlow<String?>(null)
    val initializationError = _initializationError.asStateFlow()

    init {
        initializeIfNeeded()
    }

    private fun initializeIfNeeded() {
        viewModelScope.launch {
            try {
                Log.d("InitVM", "Starting initialization")
                checkAndInitializeDatabaseUseCase()
                Log.d("InitVM", "Initialization completed")
                _isInitialized.value = true
            } catch (e: Exception) {
                Log.e("InitVM", "Error during initialization", e)
                _initializationError.value = e.message ?: "Error durante la inicialización"
                e.printStackTrace()
            }
        }
    }
}