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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import uniffi.molniya_native.AppCallback
import uniffi.molniya_native.ParseException
import uniffi.molniya_native.nativeVersion
import uniffi.molniya_native.parseTarget
import uniffi.molniya_native.takeFdOwnership
import uniffi.molniya_native.triggerCallback

@Composable
fun HomeScreen(onNavigate: (HomeDestination) -> Unit) {
    val versiRust = try {
        nativeVersion()
    } catch (e: Exception) {
        "yah gagal bhanggggggg: ${e.message}"
    }

    val inputUji = "nasgortutupyabos.com"

    val hasilUji = try {
        parseTarget(inputUji)
    } catch (e: ParseException.InvalidFormat) {
        "Rust Menolak (Format Salah): ${e.detail}"
    } catch (e: ParseException.MissingPort) {
        "Rust Menolak (Port Kosong): ${e.detail}"
    } catch (e: Exception) {
        "Error bos: ${e.message}"
    }

    var kasihSayangAtminRust by remember { mutableStateOf("Belum ada pesan ya bos dari atmin rust") }

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Engine Core Version: $versiRust",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.primary
        )
        Text(
            text = "Status: $hasilUji",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.error
        )
        Spacer(modifier = Modifier.height(32.dp))
        Text(
            text = "Status Pesan:",
            style = MaterialTheme.typography.titleMedium
        )
        Text(
            text = kasihSayangAtminRust,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.tertiary
        )
        Spacer(modifier = Modifier.height(32.dp))
        ElevatedButton(
            onClick = {
                val myCallback = object : AppCallback {
                    override fun onEvent(message: String) {
                        kasihSayangAtminRust = message
                    }
                }

                triggerCallback(myCallback)
            },
            modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
        ) {
            Text("Test Callback (Rust Panggil Kotlin)")
        }
        Spacer(Modifier.height(32.dp))
        ElevatedButton(
            onClick = {
                val dummyVPNFD = 15
                val responseRust = takeFdOwnership(dummyVPNFD)
                kasihSayangAtminRust = responseRust
            },
            modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
        ) {
            Text("Simulasi Transfer FD")
        }
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