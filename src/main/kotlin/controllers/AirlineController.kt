package controllers

import models.Airline
import persistence.Serializer

/**
 * Controller class for managing a collection of Airline objects.
 * Provides CRUD operations, search, listing, and persistence functionality.
 *
 * @property serializer The serializer used for saving and loading airline data.
 */
class AirlineController(serializerType: Serializer) {
    private var airlines = mutableListOf<Airline>()
    private var serializer: Serializer = serializerType
    private var lastId = 0

    /**
     * Generates a unique ID for a new airline.
     *
     * @return The next available ID.
     */
    private fun getId() = lastId++

    /**
     * Loads airline data from the serializer into memory.
     *
     * @throws Exception if loading fails.
     */
    @Throws
    fun load() {
        airlines = serializer.read() as MutableList<Airline>

        lastId =
            if (airlines.isEmpty()) 0 else (airlines.maxOf { it.airlineId } + 1)
    }

    /**
     * Stores the current list of airlines to the serializer.
     *
     * @throws Exception if storing fails.
     */
    @Throws
    fun store() {
        serializer.write(airlines)
    }

    /**
     * Adds a new airline to the collection and assigns it a unique ID.
     *
     * @param airline The Airline object to add.
     */
    fun addAirline(airline: Airline) {
        airline.airlineId = getId()
        airlines.add(airline)
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
     * Checks if an index is valid for the airline list.
     *
     * @param index The index to check.
     * @return True if the index is valid, false otherwise.
     */
    fun isValidIndex(index: Int): Boolean {
        return isValidListIndex(index, airlines)
    }

    /**
     * Finds an airline by its index in the list.
     *
     * @param index The index of the airline.
     * @return The Airline object if found, null otherwise.
     */
    fun findAirline(index: Int): Airline? {
        return if (isValidListIndex(index, airlines)) {
            airlines[index]
        } else {
            null
        }
    }

    /**
     * Updates an existing airline's details.
     *
     * @param indexToUpdate The index of the airline to update.
     * @param airline The new Airline data.
     * @return True if the update was successful, false otherwise.
     */
    fun updateAirline(
        indexToUpdate: Int,
        airline: Airline?,
    ): Boolean {
        val airlineFound = findAirline(indexToUpdate)

        if ((airlineFound != null) && (airline != null)) {
            airlineFound.iataCode = airline.iataCode
            airlineFound.airlineName = airline.airlineName
            airlineFound.countryOfOrigin = airline.countryOfOrigin
            airlineFound.yearFounded = airline.yearFounded
            airlineFound.isActive = airline.isActive
            return true
        }
        return false
    }

    /**
     * Deletes an airline from the list by index.
     *
     * @param indexToDelete The index of the airline to delete.
     * @return The removed Airline object if successful, null otherwise.
     */
    fun deleteAirline(indexToDelete: Int): Airline? {
        return if (isValidListIndex(indexToDelete, airlines)) {
            airlines.removeAt(indexToDelete)
        } else {
            null
        }
    }

    /**
     * Lists all airlines in the collection.
     *
     * @return A formatted string of all airline details or a message if empty.
     */
    fun listAllAirlines(): String {
        return if (airlines.isEmpty()) {
            "There are currently no airlines stored.\n"
        } else {
            var listOfAirlines = ""
            for (i in airlines.indices) {
                listOfAirlines +=
                    """
                    > -------------------------------------
                    > Airline ID: ${airlines[i].airlineId}
                    > Airline IATA Code: ${airlines[i].iataCode}
                    > Airline Name: ${airlines[i].airlineName}
                    > Country of Origin: ${airlines[i].countryOfOrigin}
                    > Year Founded: ${airlines[i].yearFounded}
                    > Is Airline Active? ${airlines[i].isActive}
                    > -------------------------------------
                    > 
                """.trimMargin(">")
            }
            listOfAirlines
        }
    }

    /**
     * Gets the total number of airlines in the collection.
     *
     * @return The number of airlines.
     */
    fun numberOfAirlines() = airlines.size

    /**
     * Finds airlines by their IATA code.
     *
     * @param iataCode The IATA code to search for.
     * @return A formatted string of matching airlines or a message if none found.
     */
    fun findByIATACode(iataCode: String): String {
        val airlineFound = airlines.filter { it.iataCode == iataCode }

        return if (airlineFound.isEmpty()) {
            "No airline found with IATA Code: ${iataCode}\n"
        } else {
            airlineFound.joinToString(separator = "") { airline ->
                """
                    > 
                    > -------------------------------------
                    > Airline ID: ${airline.airlineId}
                    > Airline IATA Code: ${airline.iataCode}
                    > Airline Name: ${airline.airlineName}
                    > Country of Origin: ${airline.countryOfOrigin}
                    > Year Founded: ${airline.yearFounded}
                    > Is Airline Active? ${airline.isActive}
                    > -------------------------------------
                    > 
                """.trimMargin(">")
            }
        }
    }

    /**
     * Lists all active airlines.
     *
     * @return A formatted string of active airlines or a message if none found.
     */
    fun listActiveAirlines(): String {
        val airlineFound = airlines.filter { it.isActive }

        return if (airlineFound.isEmpty()) {
            "There are currently no active airlines.\n"
        } else {
            airlineFound.joinToString(separator = "") { airline ->
                """
                    > 
                    > -------------------------------------
                    > Airline ID: ${airline.airlineId}
                    > Airline IATA Code: ${airline.iataCode}
                    > Airline Name: ${airline.airlineName}
                    > Country of Origin: ${airline.countryOfOrigin}
                    > Year Founded: ${airline.yearFounded}
                    > Is Airline Active? ${airline.isActive}
                    > -------------------------------------
                    >  
                """.trimMargin(">")
            }
        }
    }

    /**
     * Lists airlines founded before a specific year.
     *
     * @param year The cutoff year.
     * @return A formatted string of matching airlines or a message if none found.
     */
    fun listAirlinesFoundedBefore(year: Int): String {
        val airlineFound = airlines.filter { it.yearFounded < year }

        return if (airlineFound.isEmpty()) {
            "No airlines founded before ${year}\n"
        } else {
            airlineFound.joinToString(separator = "") { airline ->
                """
                    > 
                    > -------------------------------------
                    > Airline ID: ${airline.airlineId}
                    > Airline IATA Code: ${airline.iataCode}
                    > Airline Name: ${airline.airlineName}
                    > Country of Origin: ${airline.countryOfOrigin}
                    > Year Founded: ${airline.yearFounded}
                    > Is Airline Active? ${airline.isActive}
                    > -------------------------------------
                    > 
                """.trimMargin(">")
            }
        }
    }

    /**
     * Finds an airline by its unique ID.
     *
     * @param id The airline ID.
     * @return The Airline object if found, null otherwise.
     */
    fun getAirlineById(id: Int): Airline? = airlines.find { it.airlineId == id }
}
