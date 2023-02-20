package mx.com.satoritech.broadcastreceiver.ui.screen

import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.wifi.WifiManager
import android.os.BatteryManager
import android.util.Log
import androidx.compose.runtime.*
import androidx.core.content.ContextCompat.getSystemService

@Composable
fun BroadcastEventManager(
    context: Context,
    action: String,
    systemEvent: (Intent?) -> Unit
){
    //Evento actual en el sistema
    val currentOnSystemEvent by rememberUpdatedState(systemEvent)

    DisposableEffect(context, systemEvent) {
        val broadcastReceiver = object : BroadcastReceiver(){
            override fun onReceive(context: Context?, intent: Intent?) {
                currentOnSystemEvent(intent)
            }
        }
        IntentFilter(action).also {
            context.registerReceiver(broadcastReceiver, it)
        }
        onDispose {
            context.unregisterReceiver(broadcastReceiver)
        }
    }
}


@Composable
fun BatteryEvent(context: Context){

    var battery by remember { mutableStateOf(0) }

    BroadcastEventManager(context = context, action = Intent.ACTION_BATTERY_CHANGED){
        val batteryLevel = it?.getIntExtra(BatteryManager.EXTRA_LEVEL, 0)
        batteryLevel?.let { level ->
            battery = level
        }
    }

    BatteryComposable(battery)
}

@Composable
fun AirPlaneModeEvent(context: Context){

    var isAirPlaneModeOn by remember { mutableStateOf(false) }

    BroadcastEventManager(context = context, action = Intent.ACTION_AIRPLANE_MODE_CHANGED){
        val airPlaneMode = it?.getBooleanExtra("state", false) ?: return@BroadcastEventManager
        isAirPlaneModeOn = airPlaneMode
    }

    AirPlaneComposable(isAirPlaneModeOn)
}


@Composable
fun WifiEvent(context: Context){

    var wifiStatus by remember { mutableStateOf(0) }

    BroadcastEventManager(context = context, action = WifiManager.WIFI_STATE_CHANGED_ACTION){
        val wifiState = it?.getIntExtra(WifiManager.EXTRA_WIFI_STATE,  WifiManager.WIFI_STATE_UNKNOWN)
        wifiState?.let { wifi ->
            wifiStatus = wifi
        }
    }

    WifiComposable(wifiStatus)
}

@SuppressLint("MissingPermission")
@Composable
fun BluetoothEvent(context: Context){

    val bluetoothManager: BluetoothManager? = getSystemService(context, BluetoothManager::class.java)
    val bluetoothAdapter: BluetoothAdapter? = bluetoothManager?.adapter

    bluetoothAdapter?.startDiscovery()

    val deviceList = ArrayList<BluetoothDevice>(listOf())

    BroadcastEventManager(context = context, action = BluetoothDevice.ACTION_FOUND){
       when(it?.action){
           BluetoothDevice.ACTION_FOUND -> {
               val device: BluetoothDevice? = it.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE)
               // hecho por kis bb <3
               if(!deviceList.contains(device)) {
                   device?.let { item -> deviceList.add(item) }
               }
           }
       }
    }

    BluetoothComposable(deviceList)
}