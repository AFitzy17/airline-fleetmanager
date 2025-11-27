package controllers

import models.AirlineAircraft

class AirlineAircraftController {
    private val airlineAicraft = mutableListOf<AirlineAircraft>()

    fun addAircraftToAirline(
        airlineId: Int,
        aircraftId: Int,
        registration: String,
        yearBought: Int,
        hoursFlown: Int,
        revenuePerYear: Double,
        isRetired: Boolean,
    ) {
        airlineAicraft.add(AirlineAircraft(airlineId, aircraftId, registration, yearBought, hoursFlown, revenuePerYear, isRetired))
    }

    fun updateAircraftInAirline(
        airlineId: Int,
        aircraftId: Int,
        registration: String,
        yearBought: Int,
        hoursFlown: Int,
        revenuePerYear: Double,
        isRetired: Boolean
    ): Boolean {
        val aircraft = airlineAicraft.find { it.airlineId == airlineId && it.aircraftId == aircraftId }
        return if (aircraft != null) {
            aircraft.registration = registration
            aircraft.yearBought = yearBought
            aircraft.hoursFlown = hoursFlown
            aircraft.revenuePerYear = revenuePerYear
            aircraft.isRetired = isRetired
            true
        } else {
            false
        }
    }

    fun deleteAircraftInAirline(airlineId: Int, aircraftId: Int): AirlineAircraft? {
        val index = airlineAicraft.indexOfFirst { it.airlineId == airlineId && it.aircraftId == aircraftId }
        return if (index != -1) {
            airlineAicraft.removeAt(index)
            // returns the removed association
        } else {
            null
        }
    }

    fun numberOfAircraftInAirline(airlineId: Int): Int {
        return airlineAicraft.count { it.airlineId == airlineId }
    }

    fun listAircraftInAirline(airlineId: Int) = airlineAicraft.filter { it.airlineId == airlineId }
}
