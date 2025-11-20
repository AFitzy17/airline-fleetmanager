package models

data class Airline(
    var airlineId: Int,
    var airlineName: String,
    var countryOfOrigin: String,
    var iataCode: String,
    var yearFounded: Int,
    var isActive: Boolean
)