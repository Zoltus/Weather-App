package fi.sulku.weatherapp.components.weather

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import fi.sulku.weatherapp.R
import fi.sulku.weatherapp.models.WeatherData

/**
 * A component to display the weather details.
 *
 * Creates Vertical Grid with 2 columns to display the weather details.
 * Half on the left and half on the right side.
 *
 * @param weather The WeatherData to access the weather details.
 */
@Composable
fun Details(weather: WeatherData) {
    val context = LocalContext.current

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = Color(0xFFBFDBFE),
                shape = RoundedCornerShape(16.dp) // Rounded corners
            )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(stringResource(id = R.string.weather_details), fontWeight = FontWeight.Bold)
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(8.dp)
            ) {
                item {
                    Column {
                        Text(stringResource(id = R.string.weather_rain_amount), fontWeight = FontWeight.Bold)
                        Text(weather.daily.rainAmount[1].toString())
                        Text(stringResource(id = R.string.weather_humidity), fontWeight = FontWeight.Bold)
                        Text(weather.current.humidity.toString() + "%")
                        Text(stringResource(id = R.string.weather_uv), fontWeight = FontWeight.Bold)
                        Text(weather.daily.uv_index_max[1].toString())
                        Text(stringResource(id = R.string.weather_sunrise), fontWeight = FontWeight.Bold)
                        Text(weather.convertToClockTime(context, weather.daily.sunrise[1]))
                    }
                }
                item {
                    Column(horizontalAlignment = Alignment.End) {
                        Text(stringResource(id = R.string.weather_wind_speed), fontWeight = FontWeight.Bold)
                        Text(weather.current.windSpeed.toString())
                        //Text("Visibility")
                        //Text("")
                        Text(stringResource(id = R.string.weather_air_pressure), fontWeight = FontWeight.Bold)
                        Text("${weather.current.pressure} hPa")
                        Text(stringResource(id = R.string.weather_sunset), fontWeight = FontWeight.Bold)
                        Text(weather.convertToClockTime(context, weather.daily.sunset[1]))
                    }
                }
            }
        }
    }
}