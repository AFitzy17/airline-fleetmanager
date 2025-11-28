package controllers

import models.Aircraft
import persistence.Serializer
import kotlin.jvm.Throws

class AircraftController(serializerType: Serializer) {
    private var airframes = mutableListOf<Aircraft>()
    private var serializer: Serializer = serializerType

    @Throws(Exception::class)
    fun load() {
        airframes = serializer.read() as MutableList<Aircraft>

        lastId =
            if(airframes.isEmpty()) 0 else (airframes.maxOf { it.aircraftId } + 1)
    }

    @Throws
    fun store() {
        serializer.write(airframes)
    }

    private var lastId = 0

    private fun getId() = lastId++

    fun addAircraft(aircraft: Aircraft) {
        aircraft.aircraftId = getId()
        airframes.add(aircraft)
    }

    // utility method to determine if an index is valid in a list.
    fun isValidListIndex(
        index: Int,
        list: List<Any>,
    ): Boolean {
        return (index >= 0 && index < list.size)
    }

    fun isValidIndex(index: Int): Boolean {
        return isValidListIndex(index, airframes)
    }

    fun findAircraft(index: Int): Aircraft? {
        return if (isValidListIndex(index, airframes)) {
            airframes[index]
        } else {
            null
        }
    }

    fun updateAircraft(
        indexToUpdate: Int,
        aircraft: Aircraft?,
    ): Boolean {
        val aircraftFound = findAircraft(indexToUpdate)

        if ((aircraftFound != null) && (aircraft != null)) {
            aircraftFound.aircraftIataCode = aircraft.aircraftIataCode
            aircraftFound.manufacturer = aircraft.manufacturer
            aircraftFound.model = aircraft.model
            aircraftFound.capacity = aircraft.capacity
            aircraftFound.rangeNm = aircraft.rangeNm
            aircraftFound.yearIntroduced = aircraft.yearIntroduced
            aircraftFound.inProduction = aircraft.inProduction
            return true
        }
        return false
    }

    fun deleteAircraft(indexToDelete: Int): Aircraft? {
        return if (isValidListIndex(indexToDelete, airframes)) {
            airframes.removeAt(indexToDelete)
        } else {
            null
        }
    }

    fun listAllAircraft(): String {
        return if (airframes.isEmpty()) {
            "There are currently no aircraft stored.\n"
        } else {
            var listOfAircraft = ""
            for (i in airframes.indices) {
                listOfAircraft += """                                  
                    >  -------------------------------------
                    >  Aircraft ID: ${airframes[i].aircraftId}
                    >  Aircraft IATA Code: ${airframes[i].aircraftIataCode}
                    >  Manufacturer: ${airframes[i].manufacturer}
                    >  Model: ${airframes[i].model}
                    >  Seat Capacity: ${airframes[i].capacity}
                    >  Range: ${airframes[i].rangeNm} Nautical Miles
                    >  Year Introduced: ${airframes[i].yearIntroduced}
                    >  In Production? ${airframes[i].inProduction}
                    >  -------------------------------------
                    >  
                """.trimMargin(">")
            }
            listOfAircraft
        }
    }

    fun numberOfAircraft() = airframes.size

    fun findByIATACode(iataCode: String): String {
        val aircraftFound = airframes.filter { it.aircraftIataCode == iataCode }

        return if (aircraftFound.isEmpty()) {
            "No aircraft found with IATA Code: ${iataCode}\n"
        } else {
            aircraftFound.joinToString (separator = "") { aircraft ->
                """
                    >
                    > -------------------------------------
                    > Aircraft ID: ${aircraft.aircraftId}
                    > Aircraft IATA Code: ${aircraft.aircraftIataCode}
                    > Manufacturer: ${aircraft.manufacturer}
                    > Model: ${aircraft.model}
                    > Seat Capacity: ${aircraft.capacity}
                    > Range: ${aircraft.rangeNm}
                    > Year Introduced: ${aircraft.yearIntroduced}
                    > In Production? ${aircraft.inProduction}
                    > -------------------------------------
                    > 
                """.trimMargin(">")
            }
        }
    }

}
