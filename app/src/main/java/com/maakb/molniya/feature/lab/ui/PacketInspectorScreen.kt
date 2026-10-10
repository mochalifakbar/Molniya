package com.maakb.molniya.feature.lab.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun PacketInspectorScreen(onBackClick: () -> Unit, viewModel: PayloadInspectorViewModel = viewModel()) {
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Packet Inspector - coming soon loh ya", style = MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = onBackClick) {
            Text("Balik loh ya")
        }
        val state by viewModel.uiState.collectAsState()

        Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
            Text(
                text = "Payload Inspector",
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            // Kotak input teks untuk target
            OutlinedTextField(
                value = state.rawTargets,
                // Saat diketik, UI melempar Event TargetsChanged ke ViewModel[cite: 3]
                onValueChange = { viewModel.onEvent(PayloadInspectorEvent.TargetsChanged(it)) },
                label = { Text("Masukkan Target (pisahkan dengan ; atau Enter)") },
                modifier = Modifier.fillMaxWidth().height(120.dp),
                isError = state.validationError != null
            )

            // Tampilkan pesan error jika ada
            if (state.validationError != null) {
                Text(
                    text = state.validationError!!,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Deretan tombol kontrol
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Button(
                    // Tombol memicu event Start[cite: 3]
                    onClick = { viewModel.onEvent(PayloadInspectorEvent.Start) },
                    enabled = !state.running
                ) {
                    Text("Start")
                }

                OutlinedButton(
                    // Tombol memicu event Stop[cite: 3]
                    onClick = { viewModel.onEvent(PayloadInspectorEvent.Stop) },
                    enabled = state.running
                ) {
                    Text("Stop")
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
            HorizontalDivider()
            Spacer(modifier = Modifier.height(8.dp))

            Text("Log / Attempts:", style = MaterialTheme.typography.titleMedium)

            // Daftar hasil percobaan menggunakan LazyColumn (efisien untuk list panjang)
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(state.attempts) { attempt ->
                    Card(
                        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
                    ) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Text(text = "Target: ${attempt.target}", style = MaterialTheme.typography.labelLarge)
                            Text(text = "Status: ${attempt.status}", style = MaterialTheme.typography.bodyMedium)
                        }
                    }
                }
            }
            }
    }
}