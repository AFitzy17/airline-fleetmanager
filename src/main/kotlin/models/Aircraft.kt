package models

/**
 * Represents an aircraft with its specifications and production status.
 *
 * @property aircraftId Unique identifier for the aircraft.
 * @property aircraftIataCode The IATA code associated with the aircraft type.
 * @property manufacturer The company that manufactured the aircraft.
 * @property model The model name of the aircraft.
 * @property capacity The seating capacity of the aircraft.
 * @property rangeNm The range of the aircraft in nautical miles.
 * @property yearIntroduced The year the aircraft model was introduced.
 * @property inProduction Indicates whether the aircraft is currently in production.
 */
data class Aircraft(
    var aircraftId: Int,
    var aircraftIataCode: String,
    var manufacturer: String,
    var model: String,
    var capacity: Int,
    var rangeNm: Int,
    var yearIntroduced: Int,
    var inProduction: Boolean,
)
