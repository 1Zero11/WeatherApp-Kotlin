package com.nwcode.weatherapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.nwcode.weatherapp.ui.theme.WeatherAppTheme

class ForecastActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WeatherAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Column(modifier = Modifier.fillMaxSize()) {
                        // Message List
                        MessageList()
                    }
                }
            }
        }
    }
}

@Composable
private fun MessageList(viewModel: MyViewModel = viewModel()) {
    val weatherUiState by viewModel.uiStateForecast.collectAsState()
    val helper: CommonComposable = CommonComposable()

    LazyColumn {
        items(weatherUiState.hourly) { message ->
            helper.PropertyRow(message.first, String.format("%.2f", message.second-273.15) + " °C")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview4() {
    WeatherAppTheme {
        MessageList()
    }
}