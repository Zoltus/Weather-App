package fi.sulku.weatherapp.components.weather

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import fi.sulku.weatherapp.R
import fi.sulku.weatherapp.models.WeatherData
import java.time.LocalDateTime

/**
 * Horizontal scrollable hourly forecast component.
 *
 * Displays the hourly forecast for the next 24 hours.
 *
 * @param weather The WeatherData to access the hourly weather information.
 * @see CreateHourlyCards
 */
@Composable
fun Hourly(weather: WeatherData) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .background(
                color = Color(0xFFBFDBFE),
                shape = RoundedCornerShape(16.dp) // Rounded corners
            )
    ) {
        Text(stringResource(id = R.string.weather_hourly_forecast))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState())
        ) {
            CreateHourlyCards(weather)
        }
    }
}

/**
 * Create hourly cards for the hourly forecast.
 *
 * Filters the hourly weather information to display only the next 24 hours
 * and then creates a HourlyCard for each hour.
 *
 * @param weather The WeatherData to access the hourly weather information.
 * @see HourlyCard
 */
@Composable
fun CreateHourlyCards(weather: WeatherData) {
    val context = LocalContext.current
    val currentTime = LocalDateTime.now().minusHours(1)
    val nextDayTime = currentTime.plusDays(1)
    List(weather.hourly.temps.size) {
        val time = LocalDateTime.parse(weather.hourly.time[it])
        //start from current hours and end 24h after:
        if (time.isAfter(currentTime) && time.isBefore(nextDayTime)) {
            val timeString = weather.convertToClockTime(context, weather.hourly.time[it])
            HourlyCard(
                time = timeString,
                temp = weather.hourly.temps[it],
                icon = weather.hourly.weather_code[it]
            )
        }
    }
}