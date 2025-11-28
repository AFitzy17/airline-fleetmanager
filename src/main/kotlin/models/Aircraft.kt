package models

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
