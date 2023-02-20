package mx.com.satoritech.broadcastreceiver

import android.content.IntentFilter
import android.net.wifi.WifiManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import mx.com.satoritech.broadcastreceiver.broadcast.BroadcastManager
import mx.com.satoritech.broadcastreceiver.ui.screen.BroadcastEventScreen
import mx.com.satoritech.broadcastreceiver.ui.theme.BroadcastReceiverTheme

class MainActivity : ComponentActivity() {

    val wifiState: BroadcastManager = BroadcastManager()

    @OptIn(ExperimentalFoundationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Example with BroadcastReceiver Class
        IntentFilter(WifiManager.WIFI_STATE_CHANGED_ACTION).also {
            // Class extends BroadcastReceiver
            registerReceiver(wifiState, it)

            // Object extends BroadcastReceiver
            registerReceiver(wifiState.wifiBroadcastReceiver, it)
        }

        setContent {
            BroadcastReceiverTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    BroadcastEventScreen()
                }
            }
        }
    }

    override fun onStop() {
        super.onStop()
        // Class extends BroadcastReceiver
        unregisterReceiver(wifiState)

        // Object extends BroadcastReceiver
        unregisterReceiver(wifiState.wifiBroadcastReceiver)
    }

}
