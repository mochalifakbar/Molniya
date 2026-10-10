package com.maakb.molniya.feature.lab.ui

enum class ProbeMode {
    Manual, Automatic
}

data class ProbeAttemptUi(
    val target: String,
    val status: String
)

data class PayloadInspectorUiState(
    val rawTargets: String = "",
    val mode: ProbeMode = ProbeMode.Manual,
    val running: Boolean = false,
    val attempts: List<ProbeAttemptUi> = emptyList(),
    val validationError: String? = null
)

sealed interface PayloadInspectorEvent {
    data class TargetsChanged(val value: String) : PayloadInspectorEvent
    data object Start : PayloadInspectorEvent
    data object Stop : PayloadInspectorEvent
}