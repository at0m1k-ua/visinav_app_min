package com.kpi.visinav_app_min

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

class InitialActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Получение сообщения об ошибке из Intent
        val errorMessage = intent.getStringExtra("ERROR_MESSAGE")
        if (!errorMessage.isNullOrEmpty()) {
            // Отображение тоста с сообщением
            Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show()
        }

        setContent {
            ConnectScreen { ipAddress ->
                val intent = Intent(this, ControlPanelActivity::class.java)
                intent.putExtra("IP_ADDRESS", ipAddress)
                startActivity(intent)
            }
        }
    }
}

@Composable
fun ConnectScreen(onConnect: (String) -> Unit) {
    var ipAddress by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Enter IP Address:")
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = ipAddress,
            onValueChange = { ipAddress = it },
            label = { Text("IP Address") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { onConnect(ipAddress) }) {
            Text(text = "Connect")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewConnectScreen() {
    ConnectScreen {}
}
