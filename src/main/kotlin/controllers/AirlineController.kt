package controllers

import models.Airline
import persistence.Serializer

class AirlineController(serializerType: Serializer) {
    private var airlines = mutableListOf<Airline>()
    private var serializer: Serializer = serializerType
    private var lastId = 0

    @Throws
    fun load() {
        airlines = serializer.read() as MutableList<Airline>

        lastId =
            if(airlines.isEmpty()) 0 else (airlines.maxOf { it.airlineId } + 1)
    }

    @Throws
    fun store() {
        serializer.write(airlines)
    }

    private fun getId() = lastId++

    fun addAirline(airline: Airline) {
        airline.airlineId = getId()
        airlines.add(airline)
    }

    // utility method to determine if an index is valid in a list.
    fun isValidListIndex(
        index: Int,
        list: List<Any>,
    ): Boolean {
        return (index >= 0 && index < list.size)
    }

    fun isValidIndex(index: Int): Boolean {
        return isValidListIndex(index, airlines)
    }

    fun findAirline(index: Int): Airline? {
        return if (isValidListIndex(index, airlines)) {
            airlines[index]
        } else {
            null
        }
    }

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

    fun deleteAirline(indexToDelete: Int): Airline? {
        return if (isValidListIndex(indexToDelete, airlines)) {
            airlines.removeAt(indexToDelete)
        } else {
            null
        }
    }

    fun listAllAirlines() = airlines

    fun numberOfAirlines() = airlines.size

    fun findByIATACode(iataCode: String) = airlines.filter { it.iataCode == iataCode }
}


