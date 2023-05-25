package com.example.flashlight_app_using_bard

import android.content.Context
import android.content.pm.PackageManager
import android.hardware.camera2.CameraManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Surface(color = MaterialTheme.colors.background) {
                FlashlightApp()
            }
        }
    }
}

@Composable
fun FlashlightApp() {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    var isFlashlightOn by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            onClick = {
                coroutineScope.launch {
                    if (isFlashlightOn) {
                        turnOffFlashlight(context)
                    } else {
                        turnOnFlashlight(context)
                    }
                    isFlashlightOn = !isFlashlightOn
                }
            },
            modifier = Modifier
                .size(120.dp)
                .background(if (isFlashlightOn) Color.Red else Color.Green),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color.Transparent,
                contentColor = MaterialTheme.colors.onBackground
            )
        ) {
            Text(text = if (isFlashlightOn) "OFF" else "ON")
        }
    }
}

private fun turnOnFlashlight(context: Context) {
    val packageManager = context.packageManager
    val cameraManager = ContextCompat.getSystemService(context, CameraManager::class.java)
    if (packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH) && cameraManager != null) {
        try {
            val cameraId = cameraManager.cameraIdList[0]
            cameraManager.setTorchMode(cameraId, true)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}

private fun turnOffFlashlight(context: Context) {
    val packageManager = context.packageManager
    val cameraManager = ContextCompat.getSystemService(context, CameraManager::class.java)
    if (packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH) && cameraManager != null) {
        try {
            val cameraId = cameraManager.cameraIdList[0]
            cameraManager.setTorchMode(cameraId, false)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    FlashlightApp()
}
