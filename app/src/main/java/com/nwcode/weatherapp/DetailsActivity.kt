package com.nwcode.weatherapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.nwcode.weatherapp.ui.theme.WeatherAppTheme

class DetailsActivity : ComponentActivity() {
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
    val weatherUiState by viewModel.uiState.collectAsState()
    val helper: CommonComposable = CommonComposable()

    Column {
        helper.PropertyRow("Температура", String.format("%.2f", weatherUiState.temperature-273.15) + " °C")
        helper.PropertyRow("Влажность", weatherUiState.humidity.toString() + " %")
        helper.PropertyRow("Скорость Ветра", String.format("%.2f",weatherUiState.windSpeed) + " м/с")
        helper.PropertyRow("Давление", weatherUiState.pressure.toString() + " гПа")
        helper.PropertyRow("Точка росы", String.format("%.2f",weatherUiState.temperature-273.15) + " °C")
        helper.PropertyRow("Погода", weatherUiState.weather)
        helper.PropertyRow("Восход", helper.formatDate(weatherUiState.sunrise, false))
        helper.PropertyRow("Закат", helper.formatDate(weatherUiState.sunset, false))
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview3() {
    WeatherAppTheme {
        MessageList()
    }
}