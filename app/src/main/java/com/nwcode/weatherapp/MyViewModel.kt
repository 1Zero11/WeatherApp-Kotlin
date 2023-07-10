package com.nwcode.weatherapp

import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import org.json.JSONObject
import java.io.IOException

class MyViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(WeatherUiState())
    val uiState: StateFlow<WeatherUiState> = _uiState.asStateFlow()

    private val _uiStateForecast = MutableStateFlow(ForecastUiState())
    val uiStateForecast: StateFlow<ForecastUiState> = _uiStateForecast.asStateFlow()

    private val _uiStateError = MutableStateFlow(false)
    val uiStateError: StateFlow<Boolean> = _uiStateError.asStateFlow()


    val lat = 59.9343 // Example latitude
    val lon = 30.3351 // Example longitude

    fun fetchForecast(){
        val count = 5*24;
        val url = "https://api.openweathermap.org/data/2.5/forecast?lat=$lat&lon=$lon&cnt=$count&appid=${BuildConfig.API_KEY}"

        val client = OkHttpClient()
        val request = Request.Builder()
            .url(url)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onResponse(call: Call, response: Response) {
                try {
                    val responseData = response.body?.string()
                    // Handle the response data here
                    Log.d("API Response", responseData ?: "")
                    val responseDataJson = JSONObject(responseData!!)
                    val hourlyJson = responseDataJson.getJSONArray("list")

                    val helper = CommonComposable();

                    val hourly = (0 until hourlyJson.length()).map { index ->
                        val jsonObject = hourlyJson.getJSONObject(index)
                        val time = helper.formatDate(jsonObject.getLong("dt"))
                        val temperature = jsonObject.getJSONObject("main").getDouble("temp")
                        time to temperature
                    }

                    val handler = Handler(Looper.getMainLooper())
                    handler.post {
                        _uiStateForecast.value = ForecastUiState(
                            hourly = hourly
                        )
                    }
                }
                catch (e: Exception){
                    Log.e("API Error", "Error while getting response")
                    _uiStateError.value = true;
                }
            }

            override fun onFailure(call: Call, e: IOException) {
                // Handle the error here
                Log.e("API Error", e.message ?: "")
                _uiStateError.value = true;
            }
        })
    }

    fun fetchData() {
        // Simulating an API call or data fetch
        // This is just a placeholder; you can replace it with your own logic


        val url = "https://api.openweathermap.org/data/2.5/onecall?lat=$lat&lon=$lon&appid=${BuildConfig.API_KEY}"

        val client = OkHttpClient()
        val request = Request.Builder()
            .url(url)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onResponse(call: Call, response: Response) {
                try {
                    val responseData = response.body?.string()
                    // Handle the response data here
                    Log.d("API Response", responseData ?: "")
                    val responseDataJson = JSONObject(responseData!!)
                    val current = responseDataJson.getJSONObject("current")
                    val weather = current.getJSONArray("weather")[0] as JSONObject

                    val handler = Handler(Looper.getMainLooper())
                    handler.post {
                        _uiState.value = WeatherUiState(
                            temperature = current.getDouble("temp"),
                            humidity = current.getInt("humidity"),
                            windSpeed = current.getDouble("wind_speed"),
                            pressure = current.getInt("pressure"),
                            dewPoint = current.getDouble("dew_point"),
                            sunrise = current.getLong("sunrise"),
                            sunset = current.getLong("sunset"),
                            weather = weather.getString("main"),
                        )
                    }
                }
                catch (e: Exception){
                    Log.e("Error while getting response", e.message ?: "")
                    _uiStateError.value = true;
                }
            }

            override fun onFailure(call: Call, e: IOException) {
                // Handle the error here
                Log.e("API Error", e.message ?: "")
                _uiStateError.value = true;
            }
        })



    }

    init {
        fetchData()
        fetchForecast()
    }
}

data class WeatherUiState(
    val temperature: Double = 0.0,
    val humidity: Int = 0,
    val windSpeed: Double = 0.0,
    val pressure: Int = 0,
    val sunrise: Long = 0,
    val sunset: Long = 0,
    val dewPoint: Double = 0.0,
    val weather: String = "",

)

data class ForecastUiState(
    val hourly: List<Pair<String, Double>> = listOf()
)
