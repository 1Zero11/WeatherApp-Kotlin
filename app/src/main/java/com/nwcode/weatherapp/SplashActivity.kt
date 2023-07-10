package com.nwcode.weatherapp

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.nwcode.weatherapp.ui.theme.WeatherAppTheme

class SplashActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }, 3000)
        setContent {
            WeatherAppTheme {
                Box(modifier = Modifier.fillMaxSize()) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(100.dp).align(Alignment.Center)
                    )
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview2() {
    WeatherAppTheme {
        CircularProgressIndicator()
    }
}