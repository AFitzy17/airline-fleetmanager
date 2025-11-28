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
            > | FIND Functions:                       |
            > |                                       |
            > |     4. Find Aircraft                  |
            > |     5. Find Airlines                  |
            > |     6. Find Airline Fleets            |
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
                aircraftListMenu()
            }
            5 -> {
                airlineListMenu()
            }
            6 -> {
                fleetListMenu()
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
            > |     9. Find Aircraft                  |
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
            9 -> {
                aircraftListMenu()
            }
            0 -> {
                exitToMenu()
            }
            else -> println("Invalid choice: $choice. Please try again.")
        }
    } while (choice != 0)
}

fun aircraftListMenu() {
    var choice: Int

    do {
        choice =
            readNextInt(
                """
            > --------------------------------------------
            > |  AIRCRAFT SEARCH MENU                    |
            > --------------------------------------------
            > |     1. List All Aircraft                 |
            > |     2. Find Aircraft by IATA Code        |
            > --------------------------------------------
            > |     9. Aircraft Management               |
            > --------------------------------------------
            > |     0. Back to Main Menu                 |
            > --------------------------------------------
            > ==> """.trimMargin(">"),
            )

        when (choice) {
            1 -> {
                listAllAircraft()
            }
            2 -> {
                findAircraftByIata()
            }
            9 -> {
                aircraftMenu()
            }
            0 -> {
                exitToMenu()
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
            > |     9. Find Airlines                  |
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
            9 -> {
                airlineListMenu()
            }
            0 -> {
                exitToMenu()
            }
            else -> println("Invalid choice: $choice. Please try again.")
        }
    } while (choice != 0)
}

fun airlineListMenu() {
    var choice: Int

    do {
        choice =
            readNextInt(
                """
            > --------------------------------------------
            > |  AIRLINE SEARCH MENU                     |
            > --------------------------------------------
            > |     1. List All Airlines                 |
            > |     2. Find Airline by IATA Code         |
            > --------------------------------------------
            > |     9. Airline Management                |
            > --------------------------------------------
            > |     0. Back to Main Menu                 |
            > --------------------------------------------
            > ==> """.trimMargin(">"),
            )

        when (choice) {
            1 -> {
                listAllAirlines()
            }
            2 -> {
                findAirlineByIata()
            }
            9 -> {
                airlineMenu()
            }
            0 -> {
                exitToMenu()
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
            > |     9. Find Airline Fleets            |
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
            9 -> {
                fleetListMenu()
            }
            0 -> {
                exitToMenu()
            }
            else -> println("Invalid choice: $choice. Please try again.")
        }
    } while (choice != 0)
}

fun fleetListMenu() {
    var choice: Int

    do {
        choice =
            readNextInt(
                """
            > --------------------------------------------
            > |  FLEET SEARCH MENU                       |
            > --------------------------------------------
            > |     1. Find All Aircraft in Fleet        |
            > |     2. Find Aircraft By Registration     |
            > --------------------------------------------
            > |     9. Fleet Management                  |
            > --------------------------------------------
            > |     0. Back to Main Menu                 |
            > --------------------------------------------
            > ==> """.trimMargin(">"),
            )

        when (choice) {
            1 -> {
                listAllAircraftInFleet()
            }
            2 -> {
                findAircraftByRegistration()
            }
            9 -> {
                fleetMenu()
            }
            0 -> {
                exitToMenu()
            }
            else -> println("Invalid choice: $choice. Please try again.")
        }
    } while (choice != 0)
}

fun addAircraft() {
    // Add aircraft
    val iataCode = readNextLine("Enter the Aircraft IATA Code: ")
    val manufacturer = readNextLine("Enter the Aircraft Manufacturer: ")
    val model = readNextLine("Enter the aircraft model: ")
    val capacity = readNextInt("Enter the seat capacity: ")
    val rangeNm = readNextInt("Enter the aircraft flight range in nautical miles: ")
    val yearIntroduced = readNextInt("Enter the year it started production: ")
    val inProduction = readNextBoolean("Enter whether the aircraft is still in production: ")

    val aircraft = Aircraft(0, iataCode, manufacturer, model, capacity, rangeNm, yearIntroduced, inProduction)
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
    if ((airlineController.numberOfAirlines() > 0) && (aircraftController.numberOfAircraft() > 0)) {
        listAllAirlines()
        listAllAircraft()
        val airlineId = readNextInt("Enter airline id: ")
        val aircraftId = readNextInt("Enter aircraft id: ")
        val registration = readNextLine("Enter the registration (e.g. EI-DEE): ")
        val yearBought = readNextInt("Enter the year the aircraft was bought: ")
        val hoursFlown = readNextInt("Enter the hours this aircraft has flown: ")
        val revenuePerYear = readNextDouble("Enter the average revenue earned per year (in millions): ")
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
    } else {
        println("You need to add an airline and an aircraft before you can add aircraft to fleets.")
    }
}

fun listAllAircraft() {
    println("Aircraft: \n\n${aircraftController.listAllAircraft()}")
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
    listAllAirlines()
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
            val iataCode = readNextLine("Enter the aircraft IATA Code (i.e. A320): ")
            val manufacturer = readNextLine("Please enter the manufacturer name: ")
            val model = readNextLine("Please enter the model of aircraft: ")
            val capacity = readNextInt("Please enter the seat capacity of the aircraft: ")
            val rangeNm = readNextInt("Enter the aircraft flight range in nautical miles: ")
            val yearIntroduced = readNextInt("Enter the year it started production: ")
            val inProduction = readNextBoolean("Enter whether the aircraft is still in production: ")

            if (aircraftController.updateAircraft(
                    indexToUpdate,
                    Aircraft(indexToUpdate, iataCode, manufacturer, model, capacity, rangeNm, yearIntroduced, inProduction),
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
    if ((airlineController.numberOfAirlines() <= 0) || (aircraftController.numberOfAircraft() <= 0)) {
        println("You need to add an airline and an aircraft before updating aircraft in a fleet.")
    } else {
        listAllAirlines()
        val airlineToUpdate = readNextInt("Please enter the airline ID of which you wish to update the aircraft: ")
        if (airlineAircraftController.numberOfAircraftInAirline(airlineToUpdate) > 0) {
            listAircraftInFleet(airlineToUpdate)
            val aircraftToUpdate = readNextInt("Please enter the aircraft ID of the aircraft you want to update: ")
            val registration = readNextLine("Enter the registration (e.g. EI-DEE): ")
            val yearBought = readNextInt("Enter the year the aircraft was bought: ")
            val hoursFlown = readNextInt("Enter the hours this aircraft has flown: ")
            val revenuePerYear = readNextDouble("Enter the average revenue earned per year (in millions): ")
            val isRetired = readNextBoolean("Enter whether this aircraft has been retired or not: ")
            airlineAircraftController.updateAircraftInAirline(airlineToUpdate, aircraftToUpdate, registration, yearBought, hoursFlown, revenuePerYear, isRetired)
        } else {
            println("There are no aircraft in this fleet.")
        }
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
    if ((airlineController.numberOfAirlines() <= 0) || (aircraftController.numberOfAircraft() <= 0)) {
        println("You need to add an airline and an aircraft before deleting aircraft in a fleet.")
    } else {
        listAllAirlines()
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
}

fun save() {
    try {
        airlineController.store()
        aircraftController.store()
        airlineAircraftController.store()
        println("\nSave Successful!\n")
    } catch (e: Exception) {
        System.err.println("Erorr writing to file: $e")
    }
}

fun load() {
    try {
        airlineController.load()
        aircraftController.load()
        airlineAircraftController.load()
        println("Load Successful!")
    } catch (e: Exception) {
        System.err.println("Error reading from file: $e")
    }
}

fun exitApp() {
    println("Exiting app...")
    exit(0)
}

fun exitToMenu() {
    println("\nReloading...\n")
    mainMenu()
}

fun findAircraftByIata() {
    val iataCode = readNextLine("Enter the Aircraft IATA code you want to search: ")
    println("${aircraftController.findByIATACode(iataCode)}")
}

fun findAirlineByIata() {
    val iataCode = readNextLine("Enter the Airline IATA code you wish to search for: ")
    println("${airlineController.findByIATACode(iataCode)}")
}

fun findAircraftByRegistration() {
    val registration = readNextLine("Enter the Aircraft registration (i.e. EI-DEE): ")
    println(airlineAircraftController.findAircraftByRegistration(registration))
}