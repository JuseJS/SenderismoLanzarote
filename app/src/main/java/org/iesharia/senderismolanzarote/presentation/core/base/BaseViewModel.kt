package org.iesharia.senderismolanzarote.presentation.core.base

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow

abstract class BaseViewModel : ViewModel() {
    protected fun <T> handleError(
        error: Throwable,
        defaultMessage: String = "Error desconocido"
    ): UiState.Error {
        return UiState.Error(error.message ?: defaultMessage)
    }

    protected fun <T> MutableStateFlow<UiState<T>>.setLoading() {
        value = UiState.Loading
    }

    protected fun <T> MutableStateFlow<UiState<T>>.setSuccess(data: T) {
        value = UiState.Success(data)
    }

    protected fun <T> MutableStateFlow<UiState<T>>.setError(message: String) {
        value = UiState.Error(message)
    }
}