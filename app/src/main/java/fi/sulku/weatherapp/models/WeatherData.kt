package fi.sulku.weatherapp.models

import android.content.Context
import fi.sulku.weatherapp.R
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import timber.log.Timber
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

/**
 * Ktor serializable class for the weather data.
 *
 * Contains the daily, current and hourly weather data
 * and helper methods to get weather information.
 *
 * @property daily The daily weather data.
 * @property current The current weather data.
 * @property hourly The hourly weather data.
 */
@Serializable
data class WeatherData(
    val daily: Daily,
    val current: Current,
    val hourly: Hourly
)

/**
 * Ktor serializable class for the weather data.
 * Contains the current weather data.
 *
 * @property time The time of the weather data.
 * @property temp The temperature in Celsius.
 * @property humidity The relative humidity in percentage.
 * @property feelsLike The apparent temperature in Celsius.
 * @property precipitation The precipitation amount in mm.
 * @property weatherCode The weather condition code.
 * @property windSpeed The wind speed in m/s.
 * @property pressure The surface pressure in hPa.
 */
@Serializable
data class Current(
    val time: String,
    @SerialName("temperature_2m")
    val temp: Double,
    @SerialName("relative_humidity_2m")
    val humidity: Int,
    @SerialName("apparent_temperature")
    val feelsLike: Double,
    val precipitation: Double,
    @SerialName("weather_code")
    val weatherCode: Int,
    @SerialName("wind_speed_10m")
    val windSpeed: Double,
    @SerialName("surface_pressure")
    val pressure: Double
)

/**
 * Ktor serializable class for the daily weather data.
 *
 * Contains the daily weather information for the next 14 days.
 *
 * @property time The time of the weather data.
 * @property weatherCode The weather condition code.
 * @property maxTemps The maximum temperature in Celsius.
 * @property minTemps The minimum temperature in Celsius.
 * @property sunrise The sunrise time.
 * @property sunset The sunset time.
 * @property uvIndex The maximum UV index.
 * @property rainAmount The precipitation amount in mm.
 * @property rainChance The precipitation probability in percentage.
 */
@Serializable
data class Daily(
    val time: List<String>,
    @SerialName("weather_code")
    val weatherCode: List<Int>,
    @SerialName("temperature_2m_max")
    val maxTemps: List<Double>,
    @SerialName("temperature_2m_min")
    val minTemps: List<Double>,
    val sunrise: List<String>,
    val sunset: List<String>,
    @SerialName("uv_index_max")
    val uvIndex: List<Double>,
    @SerialName("precipitation_sum")
    val rainAmount: List<Double>,
    @SerialName("precipitation_probability_max")
    val rainChance: List<Int>,
    @SerialName("wind_speed_10m_max")
    val windSpeed: List<Double>
)

/**
 * Ktor serializable class for the hourly weather data.
 *
 * Contains the hourly weather information for the 15 days. 1 past day and 14 forecast days.
 *
 * @property time The time of the weather data.
 * @property temps The temperature in Celsius.
 * @property feelsLike The apparent temperature in Celsius.
 * @property weatherCode The weather condition code.
 */
@Serializable
data class Hourly(
    val time: List<String>,
    @SerialName("temperature_2m")
    val temps: List<Double>,
    @SerialName("apparent_temperature")
    val feelsLike: List<Double>,
    @SerialName("weather_code")
    val weatherCode: List<Int>
)

/*
 EXTENSIONS
*/

/**
 * Checks if weather data needs to be updated.
 *
 * @return True if 15 minutes have passed since the last update.
 */
fun WeatherData.needsUpdate(): Boolean {
    val fetchCoolDownMinutes: Long = 1
    val date = LocalDateTime.parse(current.time).plusMinutes(fetchCoolDownMinutes)
    val now: LocalDateTime = LocalDateTime.now()
    return now.isAfter(date)
}

/**
 * Get weathers last updated time.
 *
 * @return The time of the last update in HH:mm format.
 */
fun WeatherData.getLastUpdated(): String {
    val date = LocalDateTime.parse(current.time)
    return date.format(DateTimeFormatter.ofPattern("HH:mm"))
}

/**
 * Get the current weather condition.
 *
 * @return The current weather condition text.
 */
fun WeatherData.getCurrentCondition(context: Context): String {
    return getCondition(context, current.weatherCode)
}

/**
 * Get the weather condition for the given weather code.
 *
 * @return The weather condition text.
 */
private fun getCondition(context: Context, weatherCode: Int): String {
    val translationId = when (weatherCode) {
        0 -> R.string.condition_clear_sky
        in 1..3 -> R.string.condition_partly_cloudy
        in 45..48 -> R.string.condition_fog
        in 51..55 -> R.string.condition_drizzle
        in 56..57 -> R.string.condition_freezing_drizzle
        in 61..65 -> R.string.condition_rain
        in 66..67 -> R.string.condition_freezing_rain
        in 71..75 -> R.string.condition_snow
        77 -> R.string.condition_snow_grains
        in 80..82 -> R.string.condition_rain_showers
        in 85..86 -> R.string.condition_snow_showers
        in 95..96 -> R.string.condition_thunderstorm
        99 -> R.string.condition_heavy_hail
        else -> R.string.condition_unknown
    }
    return context.getString(translationId)
}

/**
 * Get the weather condition icon id.
 */
fun WeatherData.getConditionIconId(): Int {
    return getConditionIconId(current.weatherCode)
}

/**
 * Get the weather condition icon id for the given weather code.
 *
 * @return The weather condition icon id.
 */
fun getConditionIconId(weatherCode: Int): Int {
    return when (weatherCode) {
        0 -> R.drawable.condition_clear_sky
        in 1..3 -> R.drawable.condition_partly_cloudy
        in 45..48 -> R.drawable.condition_fog
        in 51..55 -> R.drawable.condition_drizzle
        in 56..57 -> R.drawable.condition_freezing_drizzle
        in 61..65 -> R.drawable.condition_rain
        in 66..67 -> R.drawable.condition_freezing_rain
        in 71..75 -> R.drawable.condition_snow
        77 -> R.drawable.condition_snow_grains
        in 80..82 -> R.drawable.condition_rain_showers
        in 85..86 -> R.drawable.condition_snow_showers
        in 95..96 -> R.drawable.condition_thunderstorm
        99 -> R.string.condition_heavy_hail
        else ->  {
            Timber.w("Unknown weather code: $weatherCode")
            R.string.condition_unknown
        }
    }
}
