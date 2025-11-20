import controllers.AircraftController
import controllers.AirlineAircraftController
import models.Airline
import controllers.AirlineController
import models.Aircraft
import utils.readNextBoolean
import utils.readNextDouble
import utils.readNextInt
import utils.readNextLine

fun main() {
    val airlineController = AirlineController()
    val aircraftController = AircraftController()
    val airlineAircraftController = AirlineAircraftController()

    var choice: Int

    do {
        choice = readNextInt("""
            > -----------------------------------------
            > |  FLEET MASTER V0.1                    |
            > -----------------------------------------
            > |     1. Add Aircraft                   |
            > |     2. Add Airline                    |
            > |     3. Add Aircraft to Airline        |
            > |     4. View Aircraft                  |
            > |     5. View Airlines                  |
            > |     6. View Airline Aircraft          |
            > -----------------------------------------
            > |     0. Exit                           |
            > -----------------------------------------
            > ==> """.trimMargin(">"))

        when (choice) {
            1 -> {
                //Add aircraft
                val manufacturer = readNextLine("Enter the Aircraft Manufacturer: ")
                val model = readNextLine("Enter the aircraft model: ")
                val capacity = readNextInt("Enter the seat capacity: ")
                val rangeNm = readNextInt("Enter the aircraft range in nautical miles: ")
                val yearIntroduced = readNextInt("Enter the year it started production: ")
                val inProduction = readNextBoolean("Enter whether the aircraft is still production: ")
                val aircraft = Aircraft(0, manufacturer, model, capacity, rangeNm, yearIntroduced, inProduction)
                aircraftController.addAircraft(aircraft)
            }
            2 -> {
                //Add airline
                val name = readNextLine("Enter the name of the airline: ")
                val country = readNextLine("Enter the country of origin: ")
                val iataCode = readNextLine("Enter the IATA Code (2 letter airline identifier: ")
                val year = readNextInt("Enter the year the airline was founded: ")
                val isActive = readNextBoolean("Enter whether this airline is still in operation: ")
                val airline = Airline(0, name, country,iataCode, year, isActive)
                airlineController.addAirline(airline)
            }
            3 -> {
                //Add an aircraft to an Airline
                val airlineId = readNextInt("Enter airline id: ")
                val aircraftId = readNextInt("Enter aircraft id: ")
                val registration = readNextLine("Enter the registration (e.g. EI-DEE): ")
                val yearBought = readNextInt("Enter the year the aircraft was bought: ")
                val hoursFlown = readNextInt("Enter the hours this aircraft has flown: ")
                val revenuePerYear = readNextDouble("Enter the average revenue earned per year: ")
                val isRetired = readNextBoolean("Enter whether this aircraft has been retired or not: ")
                airlineAircraftController.addAircraftToAirline(airlineId, aircraftId, registration, yearBought, hoursFlown, revenuePerYear, isRetired)
            }
            4 -> {
                println("Aircraft: \n${aircraftController.listAircraft()}")
            }
            5 -> {
                println("Airlines: \n${airlineController.listAirlines()}")
            }
            6 -> {
                val airlineId = readNextInt("Enter airline id: ")
                println("Airline Fleet: \n${airlineAircraftController.listAircraftInAirline(airlineId)}")
            }
            0 -> {
                println("Exiting app...")
            }
            else -> println("Invalid choice: ${choice}. Please try again.")
        }
    } while (choice != 0)
}