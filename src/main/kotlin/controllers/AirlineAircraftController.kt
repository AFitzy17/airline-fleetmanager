package controllers

import models.AirlineAircraft

class AirlineAircraftController {

    private val airlineAicraft = mutableListOf<AirlineAircraft>()

    fun addAircraftToAirline(airlineId: Int, aircraftId: Int, registration: String, yearBought: Int, hoursFlown: Int, revenuePerYear: Double, isRetired: Boolean) {
        airlineAicraft.add(AirlineAircraft(airlineId, aircraftId, registration, yearBought, hoursFlown, revenuePerYear, isRetired))
    }

    fun listAircraftInAirline(airlineId: Int) = airlineAicraft.filter { it.airlineId == airlineId }
}