package org.iesharia.senderismolanzarote.presentation.initialization.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.iesharia.senderismolanzarote.domain.usecase.sample.CheckAndInitializeDatabaseUseCase
import javax.inject.Inject

private const val TAG = "InitVM"

@HiltViewModel
class InitializationViewModel @Inject constructor(
    private val checkAndInitializeDatabaseUseCase: CheckAndInitializeDatabaseUseCase
) : ViewModel() {
    private val _isInitialized = MutableLiveData(false)
    val isInitialized: LiveData<Boolean> = _isInitialized

    private val _initializationError = MutableLiveData<String?>(null)
    val initializationError: LiveData<String?> = _initializationError

    init {
        initializeIfNeeded()
    }

    private fun initializeIfNeeded() {
        viewModelScope.launch {
            try {
                Log.d(TAG, "Starting database initialization...")
                checkAndInitializeDatabaseUseCase()
                Log.d(TAG, "Database initialization completed successfully")
                _isInitialized.value = true
            } catch (e: Exception) {
                Log.e(TAG, "Error during database initialization", e)
                _initializationError.value = e.message ?: "Error durante la inicialización"
                e.printStackTrace()
            }
        }
    }
}