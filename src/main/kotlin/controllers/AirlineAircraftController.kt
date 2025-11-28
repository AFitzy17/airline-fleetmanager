package controllers

import models.AirlineAircraft
import persistence.Serializer


class AirlineAircraftController(serializerType: Serializer) {
    private var airlineAircraft = mutableListOf<AirlineAircraft>()
    private var serializer: Serializer = serializerType

    @Throws
    fun load() {
        airlineAircraft = serializer.read() as MutableList<AirlineAircraft>
    }

    @Throws
    fun store() {
        serializer.write(airlineAircraft)
    }

    fun addAircraftToAirline(
        airlineId: Int,
        aircraftId: Int,
        registration: String,
        yearBought: Int,
        hoursFlown: Int,
        revenuePerYear: Double,
        isRetired: Boolean,
    ) {
        airlineAircraft.add(AirlineAircraft(airlineId, aircraftId, registration, yearBought, hoursFlown, revenuePerYear, isRetired))
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
        val aircraft = airlineAircraft.find { it.airlineId == airlineId && it.aircraftId == aircraftId }
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
        val index = airlineAircraft.indexOfFirst { it.airlineId == airlineId && it.aircraftId == aircraftId }
        return if (index != -1) {
            airlineAircraft.removeAt(index)
        } else {
            null
        }
    }

    fun numberOfAircraftInAirline(airlineId: Int): Int {
        return airlineAircraft.count { it.airlineId == airlineId }
    }

    fun listAircraftInAirline(airlineId: Int): String {
        val fleet = airlineAircraft.filter { it.airlineId == airlineId }

        return if (fleet.isEmpty()) {
            "No aircraft found in fleet for Airline ID: ${airlineId}\n"
        } else {
            fleet.joinToString(separator = "") { aircraft ->
                """
                    > ------------------------------------
                    > Airline ID: ${aircraft.airlineId}
                    > Aircraft ID: ${aircraft.aircraftId}
                    > Registration: ${aircraft.registration}
                    > Year Bought: ${aircraft.yearBought}
                    > Hours Flown: ${aircraft.hoursFlown}
                    > Revenue Per Year (in millions): ${aircraft.revenuePerYear}
                    > Retired? ${aircraft.isRetired}
                    > ------------------------------------
                    > 
                """.trimMargin(">")
            }
        }
    }

    //fun listAircraftInAirline(airlineId: Int) = airlineAircraft.filter { it.airlineId == airlineId }

    fun findAircraftByRegistration(registration: String): AirlineAircraft? {
        return airlineAircraft.find { it.registration == registration }
    }
}
