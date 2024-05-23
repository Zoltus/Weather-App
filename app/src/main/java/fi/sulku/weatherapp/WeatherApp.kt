package fi.sulku.weatherapp

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import fi.sulku.weatherapp.components.bar.TopBar
import fi.sulku.weatherapp.components.weather.Current
import fi.sulku.weatherapp.components.weather.Details
import fi.sulku.weatherapp.components.weather.Hourly
import fi.sulku.weatherapp.services.LocationService
import fi.sulku.weatherapp.ui.theme.WeatherAppTheme
import fi.sulku.weatherapp.viewmodels.SettingsRepository
import fi.sulku.weatherapp.viewmodels.WeatherViewModel

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            //Initialize LocationService and SettingsRepository
            LocationService.initialize(this.application)
            SettingsRepository.initialize(getSharedPreferences("settings", Context.MODE_PRIVATE))

            // Observes location so everything recomposes if location changes
            val locale by SettingsRepository.locale.collectAsState()
            val localee = locale //todo cleanup

            //Reloads langues from configs so eveything updates correcly
            val context = LocalContext.current
            SettingsRepository.reloadConfig(context)

            val isDarkTheme by SettingsRepository.isDarkTheme.collectAsState()
            val weatherVm: WeatherViewModel = viewModel()

            //Set locale to viewmodels locale
            WeatherAppTheme(darkTheme = isDarkTheme) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    WeatherApp(weatherVm)
                }
            }
        }
    }
}


/**
 *
 */
@Composable
fun WeatherApp(vm: WeatherViewModel) {
    Column {
        TopBar(vm)
        WeatherSection(vm)
    }
}

/**
 * Weather section
 *
 * Displays all weather related stuff
 *
 * @param vm The WeatherViewModel to access the weather information.
 */
@Composable
fun WeatherSection(vm: WeatherViewModel) {
    val selectedWeather by vm.selectedWeather.collectAsState()
    selectedWeather?.let { weather ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Current(vm, weather)
            Spacer(modifier = Modifier.padding(10.dp))
            Hourly(weather)
            //Daily()
            Spacer(modifier = Modifier.padding(10.dp))
            Details(weather)
        }
    }
}