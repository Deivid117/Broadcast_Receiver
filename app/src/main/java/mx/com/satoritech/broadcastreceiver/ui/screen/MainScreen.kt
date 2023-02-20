package mx.com.satoritech.broadcastreceiver.ui.screen

import android.annotation.SuppressLint
import android.bluetooth.BluetoothDevice
import android.net.wifi.WifiManager
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import mx.com.satoritech.broadcastreceiver.R
import mx.com.satoritech.broadcastreceiver.ui.theme.Background
import mx.com.satoritech.broadcastreceiver.ui.theme.Item

@ExperimentalFoundationApi
//@Preview(showSystemUi = true, showBackground = true)
@Composable
fun BroadcastEventScreen() {
    Surface(
        Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .background(Background)
                .fillMaxWidth()
                .padding(horizontal = 10.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
            ) {
            Text(text = "Broadcast Receiver Example", color = Color.White, fontSize = 30.sp)
            Spacer(modifier = Modifier.height(20.dp))
            BroadcastEventContent()
        }
    }
}

@ExperimentalFoundationApi
@Composable
fun BroadcastEventContent() {
    Card(
        shape = RoundedCornerShape(20.dp),
        backgroundColor = Background,
    ) {
        LazyVerticalGrid(
            modifier = Modifier.background(Color.Black.copy(alpha = 0.2f)),
            cells = GridCells.Fixed(2),
            contentPadding = PaddingValues(30.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp),
            horizontalArrangement = Arrangement.spacedBy(30.dp),
        ) {
            item {
                BatteryEvent(LocalContext.current)
            }
            item {
                AirPlaneModeEvent(LocalContext.current)
            }
            item {
                WifiEvent(LocalContext.current)
                //WifiComposable(wifiStatus = wifiInt.value)
            }
            item {
                BluetoothEvent(LocalContext.current)
            }
        }
    }
}

@Composable
fun BatteryComposable(battery: Int) {
    CardContent(
        text = "$battery%",
        icon = painterResource(id = R.drawable.ic_baseline_battery_alert_24)
    )
}

@Composable
fun AirPlaneComposable(isAirPlaneModeOn: Boolean){
    CardContent(
        text = if(isAirPlaneModeOn) "Activado" else "Desactivado",
        icon = if(isAirPlaneModeOn) painterResource(id = R.drawable.ic_baseline_airplanemode_active_24)
        else painterResource(id = R.drawable.ic_baseline_airplanemode_inactive_24)
    )
}

@Composable
fun WifiComposable(wifiStatus: Int) {

    val text = when(wifiStatus) {
        WifiManager.WIFI_STATE_ENABLED -> {
            "Activado"
        }
        WifiManager.WIFI_STATE_DISABLED -> {
            "Desactivado"
        }
        WifiManager.WIFI_STATE_UNKNOWN -> {
            "Desconocido"
        }
        else -> { "" }
    }

    CardContent(
        text = text,
        icon = painterResource(id = R.drawable.ic_baseline_wifi_24)
    )
}

@SuppressLint("MissingPermission")
@Composable
fun BluetoothComposable(deviceList: ArrayList<BluetoothDevice>) {
    
    var showDialog by remember {
        mutableStateOf(false)
    }
    
    CardContent(
        Modifier.clickable {
            showDialog = true
        },
        text = "Dispositivos", 
        icon = painterResource(id = R.drawable.ic_baseline_bluetooth_searching_24),
    )
    
    if(showDialog){
        Dialog(onDismissRequest = { showDialog = false }) {
            Column {
                Text(
                    text = "Dispositivos encontrados:",
                    color = Color.White
                )
                Spacer(modifier = Modifier.height(5.dp))
                deviceList.forEach { bluetoothDevice ->
                    Text(text = "${bluetoothDevice.name} ${bluetoothDevice.address}", color = Color.White)
                }
            }
        }
    }
}

@Composable
fun CardContent(
    modifier: Modifier = Modifier,
    text: String,
    icon: Painter
){
    Card(modifier, backgroundColor =  Item){
        Column(
            modifier = Modifier.padding(vertical = 15.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(painter = icon, contentDescription = "")
            Text(text = text)
        }
    }
}