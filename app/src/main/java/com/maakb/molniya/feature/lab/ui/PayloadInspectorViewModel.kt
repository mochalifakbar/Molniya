package com.maakb.molniya.feature.lab.ui

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class PayloadInspectorViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(PayloadInspectorUiState())
    val uiState = _uiState.asStateFlow()

    fun onEvent(event: PayloadInspectorEvent) {
        when(event) {
            is PayloadInspectorEvent.TargetsChanged -> {
                _uiState.update { it.copy(rawTargets = event.value, validationError = null) }
            }
            is PayloadInspectorEvent.Start -> {
                mulaiSimulasiJaringan()
            }
            is PayloadInspectorEvent.Stop -> {
                _uiState.update { it.copy(running = false) }
            }
        }
    }

    private fun mulaiSimulasiJaringan() {
        val currentTargets = _uiState.value.rawTargets
        if (currentTargets.isBlank()) {
            _uiState.update { it.copy(validationError = "Target tidak boleh kosong") }
            return
        }

        _uiState.update { it.copy(running = true, attempts = emptyList()) }

        val fakeAttempts = currentTargets.split(";", "\n")
            .map { it.trim() }
            .filter { it.isNotEmpty() }
            .map { target ->
                ProbeAttemptUi(target = target, status = "Simulasi Selesai (Fake)")
            }

        _uiState.update {
            it.copy(
                running = false,
                attempts = fakeAttempts
            )
        }
    }
}