package controllers

import models.Aircraft
import persistence.Serializer
import kotlin.jvm.Throws

/**
 * Controller class for managing a collection of Aircraft objects.
 * Provides CRUD operations, search, listing, and persistence functionality.
 *
 * @property serializer The serializer used for saving and loading aircraft data.
 */
class AircraftController(serializerType: Serializer) {
    private var airframes = mutableListOf<Aircraft>()
    private var serializer: Serializer = serializerType
    private var lastId = 0

    /**
     * Generates a unique ID for a new aircraft.
     *
     * @return The next available ID.
     */
    private fun getId() = lastId++

    /**
     * Loads aircraft data from the serializer into memory.
     *
     * @throws Exception if loading fails.
     */
    @Throws(Exception::class)
    fun load() {
        airframes = serializer.read() as MutableList<Aircraft>

        lastId =
            if (airframes.isEmpty()) 0 else (airframes.maxOf { it.aircraftId } + 1)
    }

    /**
     * Stores the current list of aircraft to the serializer.
     *
     * @throws Exception if storing fails.
     */
    @Throws
    fun store() {
        serializer.write(airframes)
    }

    /**
     * Adds a new aircraft to the collection and assigns it a unique ID.
     *
     * @param aircraft The Aircraft object to add.
     */
    fun addAircraft(aircraft: Aircraft) {
        aircraft.aircraftId = getId()
        airframes.add(aircraft)
    }

    /**
     * Checks if an index is valid for a given list.
     *
     * @param index The index to check.
     * @param list The list to validate against.
     * @return True if the index is valid, false otherwise.
     */
    fun isValidListIndex(
        index: Int,
        list: List<Any>,
    ): Boolean {
        return (index >= 0 && index < list.size)
    }

    /**
     * Checks if an index is valid for the aircraft list.
     *
     * @param index The index to check.
     * @return True if the index is valid, false otherwise.
     */
    fun isValidIndex(index: Int): Boolean {
        return isValidListIndex(index, airframes)
    }

    /**
     * Finds an aircraft by its index in the list.
     *
     * @param index The index of the aircraft.
     * @return The Aircraft object if found, null otherwise.
     */
    fun findAircraft(index: Int): Aircraft? {
        return if (isValidListIndex(index, airframes)) {
            airframes[index]
        } else {
            null
        }
    }

    /**
     * Updates an existing aircraft's details.
     *
     * @param indexToUpdate The index of the aircraft to update.
     * @param aircraft The new Aircraft data.
     * @return True if the update was successful, false otherwise.
     */
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

    /**
     * Deletes an aircraft from the list by index.
     *
     * @param indexToDelete The index of the aircraft to delete.
     * @return The removed Aircraft object if successful, null otherwise.
     */
    fun deleteAircraft(indexToDelete: Int): Aircraft? {
        return if (isValidListIndex(indexToDelete, airframes)) {
            airframes.removeAt(indexToDelete)
        } else {
            null
        }
    }

    /**
     * Lists all aircraft in the collection.
     *
     * @return A formatted string of all aircraft details or a message if empty.
     */
    fun listAllAircraft(): String {
        return if (airframes.isEmpty()) {
            "There are currently no aircraft stored.\n"
        } else {
            var listOfAircraft = ""
            for (i in airframes.indices) {
                listOfAircraft +=
                    """                                  
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

    /**
     * Gets the total number of aircraft in the collection.
     *
     * @return The number of aircraft.
     */
    fun numberOfAircraft() = airframes.size

    /**
     * Finds aircraft by their IATA code.
     *
     * @param iataCode The IATA code to search for.
     * @return A formatted string of matching aircraft or a message if none found.
     */
    fun findByIATACode(iataCode: String): String {
        val aircraftFound = airframes.filter { it.aircraftIataCode == iataCode }

        return if (aircraftFound.isEmpty()) {
            "No aircraft found with IATA Code: ${iataCode}\n"
        } else {
            aircraftFound.joinToString(separator = "") { aircraft ->
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

    /**
     * Counts the number of aircraft that are not in production.
     *
     * @return The number of retired aircraft.
     */
    fun numberOfAircraftNotInProduction(): Int {
        var numberOfAircraftNotInProduction = 0
        for (aircraft in airframes) {
            if (!aircraft.inProduction) {
                numberOfAircraftNotInProduction++
            }
        }
        return numberOfAircraftNotInProduction
    }

    /**
     * Lists all retired aircraft.
     *
     * @return A formatted string of retired aircraft or a message if none found.
     */
    fun listRetiredAircraft(): String {
        val retiredAircraft = airframes.filter { !it.inProduction }

        return if (retiredAircraft.isEmpty()) {
            "There are no aircraft that have stopped production.\n"
        } else {
            retiredAircraft.joinToString(separator = "") { aircraft ->
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

    /**
     * Lists aircraft within a specified capacity range.
     *
     * @param minCapacity The minimum seat capacity.
     * @param maxCapacity The maximum seat capacity.
     * @return A formatted string of matching aircraft or a message if none found.
     */
    fun listAircraftByCapacityRange(
        minCapacity: Int,
        maxCapacity: Int,
    ): String {
        val aircraftFound = airframes.filter { it.capacity in minCapacity..maxCapacity }

        return if (aircraftFound.isEmpty()) {
            "No aircraft with a capacity within the range specified: $minCapacity - $maxCapacity"
        } else {
            aircraftFound.joinToString(separator = "") { aircraft ->
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

    /**
     * Finds an aircraft by its unique ID.
     *
     * @param id The aircraft ID.
     * @return The Aircraft object if found, null otherwise.
     */
    fun findAircraftById(id: Int): Aircraft? = airframes.find { it.aircraftId == id }
}
