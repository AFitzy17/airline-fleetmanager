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

    fun listAircraft() = airframes
}