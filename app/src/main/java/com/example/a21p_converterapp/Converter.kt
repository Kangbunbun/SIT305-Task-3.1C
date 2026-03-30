object Converter {

    // Currency: 1 USD = rate[currency]
    private val usdRates = mapOf(
        "USD" to 1.0,
        "AUD" to 1.55,
        "EUR" to 0.92,
        "JPY" to 148.50,
        "GBP" to 0.78
    )

    fun convertCurrency(value: Double, from: String, to: String): Double {
        val fromRate = usdRates[from] ?: return Double.NaN
        val toRate = usdRates[to] ?: return Double.NaN
        val usd = value / fromRate
        return usd * toRate
    }

    fun convertTemperature(value: Double, from: String, to: String): Double {
        if (from == to) return value

        val celsius = when (from) {
            "°C" -> value
            "°F" -> (value - 32.0) / 1.8
            "K" -> value - 273.15
            else -> return Double.NaN
        }

        return when (to) {
            "°C" -> celsius
            "°F" -> (celsius * 1.8) + 32.0
            "K" -> celsius + 273.15
            else -> Double.NaN
        }
    }

    // Fuel/Distance groups:
    // efficiency: mpg <-> km/L
    // volume: US gallon <-> liter (L)
    // distance: nautical mile (nm) <-> kilometer (km)
    fun convertFuelDistance(value: Double, from: String, to: String): Double? {
        if (from == to) return value

        // efficiency
        if ((from == "mpg" && to == "km/L") || (from == "km/L" && to == "mpg")) {
            // 1 mpg = 0.425 km/L
            return if (from == "mpg") value * 0.425 else value / 0.425
        }

        // volume
        if ((from == "US gallon" && to == "L") || (from == "L" && to == "US gallon")) {
            // 1 US gallon = 3.785 L
            return if (from == "US gallon") value * 3.785 else value / 3.785
        }

        // distance
        if ((from == "nm" && to == "km") || (from == "km" && to == "nm")) {
            // 1 nautical mile = 1.852 km
            return if (from == "nm") value * 1.852 else value / 1.852
        }

        // incompatible units
        return null
    }
}