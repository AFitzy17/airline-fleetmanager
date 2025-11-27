package models

data class Airline(
    var airlineId: Int,
    var iataCode: String,
    var airlineName: String,
    var countryOfOrigin: String,
    var yearFounded: Int,
    var isActive: Boolean,
)
