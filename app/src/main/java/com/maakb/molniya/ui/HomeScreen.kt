package com.maakb.molniya.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import uniffi.molniya_native.nativeVersion

@Composable
fun HomeScreen(onNavigate: (HomeDestination) -> Unit) {
    val versiRust = try {
        nativeVersion()
    } catch (e: Exception) {
        "yah gagal bhanggggggg: ${e.message}"
    }

    Text(
        text = "Engine Core Version: $versiRust",
        style = MaterialTheme.typography.titleMedium,
        color = MaterialTheme.colorScheme.primary
    )

    Spacer(modifier = Modifier.height(32.dp))

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ElevatedButton(
            onClick = { onNavigate(HomeDestination.PAYLOAD_INSPECTOR) },
            modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
        ) {
            Text("Payload Inspector")
        }
        ElevatedButton(
            onClick = { onNavigate(HomeDestination.PACKET_INSPECTOR) },
            modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
        ) {
            Text("Packet Inspector")
        }
        ElevatedButton(
            onClick = { onNavigate(HomeDestination.TUNNEL_CLIENT) },
            modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
        ) {
            Text("Tunnel Client")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    MaterialTheme {
        HomeScreen(onNavigate = {})
    }
}