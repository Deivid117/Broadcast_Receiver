package mx.com.satoritech.broadcastreceiver.broadcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.wifi.WifiManager
import android.widget.Toast
import androidx.compose.runtime.mutableStateOf

class BroadcastManager : BroadcastReceiver() {

    // Class extends BroadcastReceiver
    override fun onReceive(context: Context?, intent: Intent?) {
        val wifiState = intent?.getIntExtra(WifiManager.EXTRA_WIFI_STATE,  WifiManager.WIFI_STATE_UNKNOWN)
        wifiState?.let { wifi ->

            when(wifi){
                WifiManager.WIFI_STATE_ENABLED -> {
                    Toast.makeText(context, "Wifi Activado", Toast.LENGTH_SHORT).show()
                }
                WifiManager.WIFI_STATE_DISABLED -> {
                    Toast.makeText(context, "Wifi Desactivado", Toast.LENGTH_SHORT).show()
                }
                WifiManager.WIFI_STATE_UNKNOWN -> {
                    Toast.makeText(context, "Estado Desconocido", Toast.LENGTH_SHORT).show()
                }
                else -> {
                }
            }
        }
    }

    // Object extends BroadcastReceiver
    val wifiBroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            val wifiState = intent?.getIntExtra(WifiManager.EXTRA_WIFI_STATE,  WifiManager.WIFI_STATE_UNKNOWN)
            wifiState.let { wifi ->
                when(wifi){
                    WifiManager.WIFI_STATE_ENABLED -> {
                        Toast.makeText(context, "Wifi Activado", Toast.LENGTH_SHORT).show()
                    }
                    WifiManager.WIFI_STATE_DISABLED -> {
                        Toast.makeText(context, "Wifi Desactivado", Toast.LENGTH_SHORT).show()
                    }
                    WifiManager.WIFI_STATE_UNKNOWN -> {
                        Toast.makeText(context, "Estado Desconocido", Toast.LENGTH_SHORT).show()
                    }
                    else -> {
                    }
                }
            }
        }
    }
}