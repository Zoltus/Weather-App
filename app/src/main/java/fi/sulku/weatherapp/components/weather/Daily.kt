package fi.sulku.weatherapp.components.weather

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import fi.sulku.weatherapp.R
import fi.sulku.weatherapp.viewmodels.WeatherViewModel

@Composable
fun Daily(vm: WeatherViewModel) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .background(
                color = Color(0xFFBFDBFE),
                shape = RoundedCornerShape(16.dp) // Rounded corners
            )
    ) {
        Text(stringResource(id = R.string.weather_daily_forecast), fontWeight = FontWeight.Bold)
    }
}