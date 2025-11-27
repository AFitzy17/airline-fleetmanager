import controllers.AircraftController
import controllers.AirlineAircraftController
import controllers.AirlineController
import models.Aircraft
import models.Airline
import persistence.XMLSerializer
import utils.readNextBoolean
import utils.readNextDouble
import utils.readNextInt
import utils.readNextLine
import java.io.File
import java.lang.System.exit

val airlineController = AirlineController(XMLSerializer(File("airlines.xml")))
val aircraftController = AircraftController(XMLSerializer(File("aircraft.xml")))
val airlineAircraftController = AirlineAircraftController(XMLSerializer(File("fleet.xml")))

fun main() {
    mainMenu()
}

fun mainMenu() {
    var choice: Int

    do {
        choice =
            readNextInt(
                """
            > -----------------------------------------
            > |  FLEET MASTER V1.0                    |
            > -----------------------------------------
            > | CRUD Functions:                       |
            > |                                       |
            > |     1. Aircraft Management            |
            > |     2. Airline Management             |
            > |     3. Fleet Management               |
            > |                                       |
            > -----------------------------------------
            > | List Functions:                       |
            > |                                       |
            > |     4. View All Aircraft              |
            > |     5. View All Airlines              |
            > |     6. View All Airline Aircraft      |
            > |                                       |
            > -----------------------------------------
            > | SAVE/LOAD Functions:                  |
            > |                                       |
            > |    20. Save to XML files              |
            > |    21. Load from XML files            |
            > |                                       |
            > -----------------------------------------
            > |     0. Exit                           |
            > -----------------------------------------
            > ==> """.trimMargin(">"),
            )

        when (choice) {
            1 -> {
                aircraftMenu()
            }
            2 -> {
                airlineMenu()
            }
            3 -> {
                // Add an aircraft to an Airline
                fleetMenu()
            }
            4 -> {
                listAllAircraft()
            }
            5 -> {
                listAllAirlines()
            }
            6 -> {
                listAllAircraftInFleet()
            }
            20 -> {
                save()
            }
            21 -> {
                load()
            }
            0 -> {
                exitApp()
            }
            else -> println("Invalid choice: $choice. Please try again.")
        }
    } while (choice != 0)
}

fun aircraftMenu() {
    var choice: Int

    do {
        choice =
            readNextInt(
                """
            > -----------------------------------------
            > |  AIRCRAFT MANAGEMENT MENU             |
            > -----------------------------------------
            > |     1. Add Aircraft                   |
            > |     2. Update Aircraft                |
            > |     3. Delete Aircraft                |
            > -----------------------------------------
            > |     4. List all Aircraft              |
            > -----------------------------------------
            > |     0. Back to Main Menu              |
            > -----------------------------------------
            > ==> """.trimMargin(">"),
            )

        when (choice) {
            1 -> {
                addAircraft()
            }
            2 -> {
                updateAircraft()
            }
            3 -> {
                deleteAircraft()
            }
            4 -> {
                listAllAircraft()
            }
            0 -> {
                println("Reloading...")
            }
            else -> println("Invalid choice: $choice. Please try again.")
        }
    } while (choice != 0)
}

fun airlineMenu() {
    var choice: Int

    do {
        choice =
            readNextInt(
                """
            > -----------------------------------------
            > |  AIRLINE MANAGEMENT MENU              |
            > -----------------------------------------
            > |     1. Add Airline                    |
            > |     2. Update Airline                 |
            > |     3. Delete Airline                 |
            > -----------------------------------------
            > |     4. List all Airlines              |
            > -----------------------------------------
            > |     0. Back to Main Menu              |
            > -----------------------------------------
            > ==> """.trimMargin(">"),
            )

        when (choice) {
            1 -> {
                addAirline()
            }
            2 -> {
                updateAirline()
            }
            3 -> {
                deleteAirline()
            }
            4 -> {
                listAllAirlines()
            }
            0 -> {
                println("Reloading...")
            }
            else -> println("Invalid choice: $choice. Please try again.")
        }
    } while (choice != 0)
}

fun fleetMenu() {
    var choice: Int

    do {
        choice =
            readNextInt(
                """
            > -----------------------------------------
            > |  FLEET MANAGEMENT MENU                |
            > -----------------------------------------
            > |     1. Add Aircraft to Fleet          |
            > |     2. Update Aircraft in Fleet       |
            > |     3. Delete Aircraft in Fleet       |
            > -----------------------------------------
            > |     4. List all Aircraft in           |  
            >          an Airline Fleet               |
            > -----------------------------------------
            > |     0. Back to Main Menu              |
            > -----------------------------------------
            > ==> """.trimMargin(">"),
            )

        when (choice) {
            1 -> {
                addAircraftToFleet()
            }
            2 -> {
                updateAircraftInFleet()
            }
            3 -> {
                deleteAircraftInFleet()
            }
            4 -> {
                listAllAircraftInFleet()
            }
            0 -> {
                println("Reloading...")
            }
            else -> println("Invalid choice: $choice. Please try again.")
        }
    } while (choice != 0)
}

fun addAircraft() {
    // Add aircraft
    val manufacturer = readNextLine("Enter the Aircraft Manufacturer: ")
    val model = readNextLine("Enter the aircraft model: ")
    val capacity = readNextInt("Enter the seat capacity: ")
    val rangeNm = readNextInt("Enter the aircraft flight range in nautical miles: ")
    val yearIntroduced = readNextInt("Enter the year it started production: ")
    val inProduction = readNextBoolean("Enter whether the aircraft is still in production: ")

    val aircraft = Aircraft(0, manufacturer, model, capacity, rangeNm, yearIntroduced, inProduction)
    aircraftController.addAircraft(aircraft)
}

fun addAirline() {
    val iataCode = readNextLine("Enter the 2-letter Airline IATA Code: ")
    val airlineName = readNextLine("Enter the name of the Airline: ")
    val country = readNextLine("Enter the country of origin for the Airline: ")
    val yearFounded = readNextInt("Enter the year the Airline was founded: ")
    val isActive = readNextBoolean("Enter whether the Airline is still in operation: ")

    val airline = Airline(0, iataCode, airlineName, country, yearFounded, isActive)
    airlineController.addAirline(airline)
}

fun addAircraftToFleet() {
    val airlineId = readNextInt("Enter airline id: ")
    val aircraftId = readNextInt("Enter aircraft id: ")
    val registration = readNextLine("Enter the registration (e.g. EI-DEE): ")
    val yearBought = readNextInt("Enter the year the aircraft was bought: ")
    val hoursFlown = readNextInt("Enter the hours this aircraft has flown: ")
    val revenuePerYear = readNextDouble("Enter the average revenue earned per year: ")
    val isRetired = readNextBoolean("Enter whether this aircraft has been retired or not: ")
    airlineAircraftController.addAircraftToAirline(
        airlineId,
        aircraftId,
        registration,
        yearBought,
        hoursFlown,
        revenuePerYear,
        isRetired,
    )
}

fun listAllAircraft() {
    if (aircraftController.numberOfAircraft() > 0) {
        println("Airlines: \n${aircraftController.listAllAircraft()}")
    } else {
        println("There are currently no aircraft stored.")
    }
}

fun listAllAirlines() {
    if (airlineController.numberOfAirlines() > 0) {
        println("Airlines: \n${airlineController.listAllAirlines()}")
    } else {
        println("There are currently no airlines stored.")
    }
}

fun listAircraftInFleet(airlineId: Int) {
    println("Airline Fleet: \n${airlineAircraftController.listAircraftInAirline(airlineId)}")
}

fun listAllAircraftInFleet() {
    val airlineId = readNextInt("Please enter the airline ID of the Airline: ")
    if (airlineAircraftController.numberOfAircraftInAirline(airlineId)> 0) {
        listAircraftInFleet(airlineId)
    } else {
        println("There are no aircraft in the fleet.")
    }
}

fun updateAircraft() {
    listAllAircraft()
    if (aircraftController.numberOfAircraft() > 0) {
        // ask user for index to update
        val indexToUpdate = readNextInt("Enter the index number of the aircraft you wish to update: ")
        if (aircraftController.isValidIndex(indexToUpdate)) {
            val manufacturer = readNextLine("Please enter the manufacturer name: ")
            val model = readNextLine("Please enter the model of aircraft: ")
            val capacity = readNextInt("Please enter the seat capacity of the aircraft: ")
            val rangeNm = readNextInt("Enter the aircraft flight range in nautical miles: ")
            val yearIntroduced = readNextInt("Enter the year it started production: ")
            val inProduction = readNextBoolean("Enter whether the aircraft is still in production: ")

            if (aircraftController.updateAircraft(
                    indexToUpdate,
                    Aircraft(indexToUpdate, manufacturer, model, capacity, rangeNm, yearIntroduced, inProduction),
                )
            ) {
                println("Update successful!")
            } else {
                println("Update Failed.")
            }
        } else {
            println("There are no aircraft at this index number.")
        }
    }
}

fun updateAirline() {
    listAllAirlines()
    if (airlineController.numberOfAirlines() > 0) {
        val indexToUpdate = readNextInt("Enter the index number of the airline you wish to update: ")
        if (airlineController.isValidIndex(indexToUpdate)) {
            val iataCode = readNextLine("Please enter the Airline's 2-letter IATA code: ")
            val airlineName = readNextLine("Please enter the Airline name: ")
            val country = readNextLine("Please enter the Airline's country of origin: ")
            val yearFounded = readNextInt("Please enter the year the Airline was founded: ")
            val isActive = readNextBoolean("Please enter whether the Airline is still in operation: ")

            if (airlineController.updateAirline(
                    indexToUpdate,
                    Airline(indexToUpdate, iataCode, airlineName, country, yearFounded, isActive),
                )
            ) {
                println("Update successful!")
            } else {
                println("Update Failed.")
            }
        } else {
            println("There are no airline at this index number.")
        }
    } else {
        println("There are no aircraft in this fleet.")
    }
}

fun updateAircraftInFleet() {
    val airlineToUpdate = readNextInt("Please enter the airline ID of which you wish to update the aircraft: ")
    if (airlineAircraftController.numberOfAircraftInAirline(airlineToUpdate) > 0) {
        listAircraftInFleet(airlineToUpdate)
        val aircraftToUpdate = readNextInt("Please enter the aircraft ID of the aircraft you want to update: ")
        val registration = readNextLine("Enter the registration (e.g. EI-DEE): ")
        val yearBought = readNextInt("Enter the year the aircraft was bought: ")
        val hoursFlown = readNextInt("Enter the hours this aircraft has flown: ")
        val revenuePerYear = readNextDouble("Enter the average revenue earned per year: ")
        val isRetired = readNextBoolean("Enter whether this aircraft has been retired or not: ")
        airlineAircraftController.updateAircraftInAirline(airlineToUpdate, aircraftToUpdate, registration, yearBought, hoursFlown, revenuePerYear, isRetired)
    } else {
        println("There are no aircraft in this fleet.")
    }
}

fun deleteAircraft() {
    listAllAircraft()
    if (aircraftController.numberOfAircraft() > 0) {
        // ask user for index to delete
        val indexToDelete = readNextInt("Enter the index of the aircraft you'd like to delete: ")
        val aircraftToDelete = aircraftController.deleteAircraft(indexToDelete)
        if (aircraftToDelete != null) {
            println("Deleted successfully!")
        } else {
            println("Failed to delete aircraft.")
        }
    }
}

fun deleteAirline() {
    listAllAirlines()
    if (airlineController.numberOfAirlines() > 0) {
        // ask user for index to delete
        val indexToDelete = readNextInt("Enter the index of the Airline you'd like to delete: ")
        val airlineToDelete = airlineController.deleteAirline(indexToDelete)
        if (airlineToDelete != null) {
            println("Deleted successfully!")
        } else {
            println("Failed to delete airline.")
        }
    }
}

fun deleteAircraftInFleet() {
    val airlineId = readNextInt("Please enter the airline ID of which you wish to delete the aircraft: ")
    if (airlineAircraftController.numberOfAircraftInAirline(airlineId) > 0) {
        listAircraftInFleet(airlineId)
        val aircraftId = readNextInt("Please enter the aircraft ID of the aircraft you want to delete: ")
        val aircraftToDelete = airlineAircraftController.deleteAircraftInAirline(airlineId, aircraftId)
        if (aircraftToDelete != null) {
            println("Aircraft deleted successfully!")
        } else {
            println("Failed to delete aircraft from fleet.")
        }
    }
}

fun save() {
    try {
        airlineController.store()
        aircraftController.store()
        airlineAircraftController.store()
    } catch (e: Exception) {
        System.err.println("Erorr writing to file: $e")
    }
}

fun load() {
    try {
        airlineController.load()
        aircraftController.load()
        airlineAircraftController.load()
    } catch (e: Exception) {
        System.err.println("Error reading from file: $e")
    }
}

fun exitApp() {
    println("Exiting app...")
    exit(0)
}
/*fun findByIataCode() {
    val iataCode = readNextLine("Enter the Airline iata code you wish to search for: ")
    println("${airlineController.findByIATACode(iataCode)}")
}*/