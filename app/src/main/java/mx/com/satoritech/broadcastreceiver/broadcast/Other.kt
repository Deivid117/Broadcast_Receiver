package mx.com.satoritech.broadcastreceiver.broadcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.os.PersistableBundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import mx.com.satoritech.broadcastreceiver.R
import mx.com.satoritech.broadcastreceiver.ui.screen.BroadcastEventScreen
import mx.com.satoritech.broadcastreceiver.ui.screen.CardContent
import mx.com.satoritech.broadcastreceiver.ui.theme.BroadcastReceiverTheme

class Other : ComponentActivity() {

    var batteryLevel = mutableStateOf(0)

    val batteryBroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            batteryLevel.value = intent?.getIntExtra("level", 0) ?: 0
        }
    }

    @OptIn(ExperimentalFoundationApi::class)
    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)

        IntentFilter(Intent.ACTION_BATTERY_CHANGED).also {
            registerReceiver(batteryBroadcastReceiver, it)
        }

        setContent {
            BroadcastReceiverTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    //BroadcastEventScreen(wifiState.wifiInt.value)
                }
            }
        }
    }

    override fun onStop() {
        super.onStop()

        unregisterReceiver(batteryBroadcastReceiver)
    }

    @Composable
    fun Battery(){
        CardContent(
            text = "$batteryLevel%",
            icon = painterResource(id = R.drawable.ic_baseline_battery_alert_24)
        )
    }
}