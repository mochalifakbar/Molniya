package com.maakb.molniya

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.maakb.molniya.feature.inspector.ui.PayloadInspectorScreen
import com.maakb.molniya.feature.lab.ui.PacketInspectorScreen
import com.maakb.molniya.feature.tunnel.ui.TunnelClientScreen
import com.maakb.molniya.ui.HomeDestination
import com.maakb.molniya.ui.HomeScreen
import com.maakb.molniya.ui.theme.MolniyaTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MolniyaTheme {
                Surface {
                    MolniyaApp()
                }
            }
        }
    }
}

@Composable
fun MolniyaApp() {
    var currentScreen by remember { mutableStateOf<HomeDestination?>(null) }
    when (currentScreen) {
        null -> {
            HomeScreen(onNavigate = {
                destination -> currentScreen = destination
            })
        }

        HomeDestination.PAYLOAD_INSPECTOR -> {
            PayloadInspectorScreen(onBackClick = {currentScreen = null})
        }

        HomeDestination.PACKET_INSPECTOR -> {
            PacketInspectorScreen(onBackClick = {currentScreen = null})
        }

        HomeDestination.TUNNEL_CLIENT -> {
            TunnelClientScreen(onBackClick = {currentScreen = null})
        }
    }

}