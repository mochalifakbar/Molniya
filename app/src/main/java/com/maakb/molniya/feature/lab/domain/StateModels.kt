package com.maakb.molniya.feature.lab.domain

sealed interface SessionState {
    data object Idle : SessionState
    data object Preparing : SessionState
    data object WaitingForConsent : SessionState
    data object Starting : SessionState
    data object Running : SessionState
    data object Stopping : SessionState
    data class Failed(val error: String) : SessionState
}

sealed interface FlowState {
    data object New : FlowState
    data object Connecting : FlowState
    data object Established : FlowState
    data object Closing : FlowState
    data object Closed : FlowState
    data class Failed(val reason: String) : FlowState
}

sealed interface ProbeState {
    data object Queued : ProbeState
    data object Resolving : ProbeState
    data object Connecting : ProbeState
    data object Negotiating : ProbeState
    data object Verifying : ProbeState
    data object Succeeded : ProbeState
    data object Cancelled : ProbeState
    data class Failed(val errorCode: Int, val message: String) : ProbeState
}