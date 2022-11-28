package com.sc941737.lib.error

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.launch

data class ErrorEvent(
    val tag: String,
    val message: String,
)

interface ErrorTracker {
    val errorEvents: SharedFlow<ErrorEvent>

    fun reportError(errorEvent: ErrorEvent)
    fun filterByTag(tag: String): SharedFlow<ErrorEvent>
    suspend fun subscribeByTag(tag: String, block: (ErrorEvent) -> Unit)
}

class ErrorTrackerImpl(
    private val processScope: CoroutineScope,
) : ErrorTracker {
    private val _errorEvents = MutableSharedFlow<ErrorEvent>()
    override val errorEvents: SharedFlow<ErrorEvent> = _errorEvents

    override fun reportError(errorEvent: ErrorEvent) {
        processScope.launch { _errorEvents.emit(errorEvent) }
    }

    override fun filterByTag(tag: String): SharedFlow<ErrorEvent> =
        errorEvents
            .filter { event -> event.tag == tag }
            .shareIn(processScope, SharingStarted.WhileSubscribed())

    override suspend fun subscribeByTag(tag: String, block: (ErrorEvent) -> Unit) {
        errorEvents
            .filter { event -> event.tag == tag }
            .collectLatest {
                block(it)
            }
    }

}