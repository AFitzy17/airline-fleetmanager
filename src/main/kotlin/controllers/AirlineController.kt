package controllers

import models.Airline

class AirlineController {
    private val airlines = mutableListOf<Airline>()
    private var lastId = 0

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


