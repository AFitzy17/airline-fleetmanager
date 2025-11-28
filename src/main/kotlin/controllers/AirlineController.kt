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

    fun listAllAirlines(): String {
        return if (airlines.isEmpty()) {
            "There are currently no airlines stored.\n"
        } else {
            var listOfAirlines = ""
            for (i in airlines.indices) {
                listOfAirlines += """
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

    fun numberOfAirlines() = airlines.size

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

    fun getAirlineById(id: Int): Airline? = airlines.find { it.airlineId == id }
}


