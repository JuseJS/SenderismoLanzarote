package org.iesharia.senderismolanzarote.presentation.core.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import org.iesharia.senderismolanzarote.data.handler.ErrorHandler
import org.iesharia.senderismolanzarote.data.logger.ErrorLogger
import org.iesharia.senderismolanzarote.data.mapper.error.toErrorModel

abstract class BaseViewModel(
    protected val errorHandler: ErrorHandler,
    protected val errorLogger: ErrorLogger
) : ViewModel() {

    protected fun <T> handleLoadOperation(
        state: MutableStateFlow<UiState<T>>,
        loadData: suspend () -> T
    ) {
        viewModelScope.launch {
            state.value = UiState.Loading
            try {
                val result = loadData()
                state.value = UiState.Success(result)
            } catch (e: Exception) {
                val error = e.toErrorModel()
                errorLogger.logError(error)
                state.value = UiState.Error(error.message)
            }
        }
    }
}