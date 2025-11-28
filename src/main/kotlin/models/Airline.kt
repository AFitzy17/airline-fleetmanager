package models

/**
 * Represents an airline with its identifying details and operational status.
 *
 * @property airlineId Unique identifier for the airline.
 * @property iataCode The IATA code assigned to the airline.
 * @property airlineName The official name of the airline.
 * @property countryOfOrigin The country where the airline is based.
 * @property yearFounded The year the airline was established.
 * @property isActive Indicates whether the airline is currently active.
 */
data class Airline(
    var airlineId: Int,
    var iataCode: String,
    var airlineName: String,
    var countryOfOrigin: String,
    var yearFounded: Int,
    var isActive: Boolean,
)
