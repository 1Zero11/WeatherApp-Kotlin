package com.nwcode.weatherapp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.startActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.viewmodel.compose.viewModel
import com.nwcode.weatherapp.ui.theme.WeatherAppTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)

        // Fetch data using the ViewModel


        setContent {
            WeatherAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Column(modifier = Modifier.fillMaxSize()) {
                        // Header
                        Menu()

                        // Message List
                        MessageList()
                    }
                }
            }
        }

    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Composable
private fun MessageList(viewModel: MyViewModel = viewModel()) {
    val gameUiState by viewModel.uiState.collectAsState()
    val errorState by viewModel.uiStateError.collectAsState()
    val helper: CommonComposable = CommonComposable()

    Column {
        helper.PropertyRow("Температура", String.format("%.2f", gameUiState.temperature-273.15) + " °C")
        helper.PropertyRow("Влажность", gameUiState.humidity.toString() + " %")
        helper.PropertyRow("Скорость Ветра", String.format("%.2f",gameUiState.windSpeed) + " м/с")
        helper.PropertyRow("Давление", gameUiState.pressure.toString() + " гПа")
    }

    if(errorState){
        SnackbarError()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SnackbarError() {
        Scaffold(/* ... */) { contentPadding ->
            if (true) {
                Snackbar(
                    modifier = Modifier.padding(8.dp)
                ) { Text(text = "Ошибка при получении данных с API") }
            }
            Box(modifier = Modifier.padding(contentPadding)) { /* ... */ }

        }
}

@Composable
fun Menu(){
    val context = LocalContext.current

    Row(modifier = Modifier
        .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically) {
        //Header
        Text(
            text = "Погода в Санкт-Петербурге",
            style = TextStyle(
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            ),
            modifier = Modifier
                .padding(16.dp)
                .align(Alignment.CenterVertically)
        )
    }


    // Buttons
    Row(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Button(
            onClick = { launchActivity(context, DetailsActivity::class.java) },
            modifier = Modifier.padding(end = 8.dp)
        ) {
            Text(text = "Details")
        }
        Button(
            onClick = { launchActivity(context, ForecastActivity::class.java) },
            modifier = Modifier.padding(start = 8.dp)
        ) {
            Text(text = "Forecast")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    WeatherAppTheme {
        Greeting("Android")
    }
}


fun <T>launchActivity(context: Context, activityClass: Class<T>) {
    val intent = Intent(context, activityClass)
    context.startActivity(intent)
}
