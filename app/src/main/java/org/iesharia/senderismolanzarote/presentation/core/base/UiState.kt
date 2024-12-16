package org.iesharia.senderismolanzarote.presentation.core.base

sealed interface UiState<out T> {
    object Initial : UiState<Nothing>
    object Loading : UiState<Nothing>
    data class Success<T>(val data: T) : UiState<T>
    data class Error(val message: String) : UiState<Nothing>
}