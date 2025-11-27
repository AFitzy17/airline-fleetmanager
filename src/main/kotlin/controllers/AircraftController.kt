package controllers

import models.Aircraft

class AircraftController {
    private val airframes = mutableListOf<Aircraft>()
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

    fun listAllAircraft() = airframes

    fun numberOfAircraft() = airframes.size
}
