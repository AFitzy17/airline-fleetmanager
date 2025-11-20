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

    fun listAirlines() = airlines
}