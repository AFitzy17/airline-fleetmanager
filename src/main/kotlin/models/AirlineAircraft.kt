package models

data class AirlineAircraft(
    var airlineId: Int,
    var aircraftId: Int,
    var registration: String,
    var yearBought: Int,
    var hoursFlown: Int,
    var revenuePerYear: Double,
    var isRetired: Boolean
)