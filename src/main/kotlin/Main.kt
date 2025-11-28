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

/**
 * Entry point for the Fleet Master application.
 * Provides a console-based menu system for managing airlines, aircraft, and fleets.
 */
fun main() {
    mainMenu()
}

/**
 * Displays the main menu and handles navigation to submenus.
 * Options include managing aircraft, airlines, fleets, and performing save/load operations.
 */
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

/**
 * Displays the Aircraft Management menu and handles CRUD operations for aircraft.
 */
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

/**
 * Displays the Aircraft Search menu and handles listing and searching aircraft.
 */
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
            > |     3. Find Retired Aircraft             |
            > |     4. List Aircraft within              |
            > |        capacity range                    |   
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
            3 -> {
                listRetiredAircraft()
            }
            4 -> {
                listAircraftWithinSeatCapacity()
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

/**
 * Displays the Airline Management menu and handles CRUD operations for airlines.
 */
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

/**
 * Displays the Airline Search menu and handles listing and searching airlines.
 */
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
            > |     3. List Active Airlines              |
            > |     4. List Airlines Founded             |
            > |        Before a Chosen Year              |
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
            3 -> {
                listActiveAirlines()
            }
            4 -> {
                listAirlinesFoundedBefore()
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

/**
 * Displays the Fleet Management menu and handles adding, updating, and deleting aircraft in fleets.
 */
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

/**
 * Displays the Fleet Search menu and handles listing and searching aircraft in fleets.
 */
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
            > |     3. List Aircraft with Top Revenue    |
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
            3 -> {
                listTopRevenueFleet()
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

/**
 * Adds a new aircraft to the system by prompting the user for details.
 */
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

/**
 * Adds a new airline to the system by prompting the user for details.
 */
fun addAirline() {
    val iataCode = readNextLine("Enter the 2-letter Airline IATA Code: ")
    val airlineName = readNextLine("Enter the name of the Airline: ")
    val country = readNextLine("Enter the country of origin for the Airline: ")
    val yearFounded = readNextInt("Enter the year the Airline was founded: ")
    val isActive = readNextBoolean("Enter whether the Airline is still in operation: ")

    val airline = Airline(0, iataCode, airlineName, country, yearFounded, isActive)
    airlineController.addAirline(airline)
}

/**
 * Adds an aircraft to an airline's fleet by prompting the user for details.
 */
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

/**
 * Lists all aircraft stored in the system.
 */
fun listAllAircraft() {
    println("Aircraft: \n\n${aircraftController.listAllAircraft()}")
}

/**
 * Lists all retired aircraft.
 */
fun listRetiredAircraft() {
    println("Retired Aircraft: \n\n${aircraftController.listRetiredAircraft()}")
}

/**
 * Lists aircraft within a specified seat capacity range.
 */
fun listAircraftWithinSeatCapacity() {
    val minCapacity = readNextInt("Please enter the minimum seat capacity: ")
    val maxCapacity = readNextInt("Please enter the maxmimum seat capacity: ")
    if (aircraftController.numberOfAircraft() > 0) {
        println("Aircraft with seat capacity between $minCapacity and $maxCapacity: \n\n${aircraftController.listAircraftByCapacityRange(minCapacity, maxCapacity)}")
    } else {
        println("There are no aircraft stored.")
    }
}

/**
 * Lists all airlines stored in the system.
 */
fun listAllAirlines() {
    if (airlineController.numberOfAirlines() > 0) {
        println("Airlines: \n${airlineController.listAllAirlines()}")
    } else {
        println("There are currently no airlines stored.")
    }
}

/**
 * Lists all active airlines.
 */
fun listActiveAirlines() {
    if (airlineController.numberOfAirlines() >0) {
        println("Active Airlines: \n\n${airlineController.listActiveAirlines()}")
    } else {
        println("There are currently no airlines stored.")
    }
}

/**
 * Lists airlines founded before a specified year.
 */
fun listAirlinesFoundedBefore() {
    val year = readNextInt("Please enter the year to search before: ")
    if (airlineController.numberOfAirlines() > 0) {
        println("Airlines founded before ${year}: \n\n${airlineController.listAirlinesFoundedBefore(year)}")
    } else {
        println("There are no airlines stored.")
    }
}

/**
 * Lists all aircraft in a specific airline's fleet.
 *
 * @param airlineId The ID of the airline whose fleet should be listed.
 */
fun listAircraftInFleet(airlineId: Int) {
    println("Airline Fleet: \n${airlineAircraftController.listAircraftInAirline(airlineId)}")
}

/**
 * Lists all aircraft in a fleet for a chosen airline.
 */
fun listAllAircraftInFleet() {
    listAllAirlines()
    val airlineId = readNextInt("Please enter the airline ID of the Airline: ")
    if (airlineAircraftController.numberOfAircraftInAirline(airlineId)> 0) {
        listAircraftInFleet(airlineId)
    } else {
        println("There are no aircraft in the fleet.")
    }
}

/**
 * Lists aircraft in a fleet sorted by lifetime revenue.
 */
fun listTopRevenueFleet() {
    listAllAirlines()
    val airlineId = readNextInt("Enter the Airline ID: ")
    val asOfYear = readNextInt("Enter the current year: ")

    val airline = airlineController.getAirlineById(airlineId)
    val fleet = airlineAircraftController.getFleetForAirline(airlineId)

    if (fleet.isEmpty()) {
        println("No aircraft found for Airline ID: ${airlineId}")
    } else {
        val sortedFleet = fleet.sortedByDescending { (asOfYear - it.yearBought + 1) * it.revenuePerYear }

        println("Lifetime-Revenue Aircraft for ${airline?.airlineName} (as of ${asOfYear}):\n")

        for (fleet in sortedFleet) {
            val years = (asOfYear - fleet.yearBought + 1)
            val lifetimeRevenue = years * fleet.revenuePerYear
            val aircraft = aircraftController.findAircraftById(fleet.aircraftId)

            println("""
                >
                >------------------------------------
                > Airline ID: ${airline?.airlineId}
                > Airline: ${airline?.airlineName}
                > Registration: ${fleet.registration}
                > Year Bought: ${fleet.yearBought}
                > Years in Fleet: ${years}
                > Revenue per Year (millions): ${fleet.revenuePerYear}
                > Lifetime Revenue (millions): ${lifetimeRevenue}
                > Retired? ${fleet.isRetired}
                > 
                > Aircraft Details:
                > Manufacturer: ${aircraft?.manufacturer}
                > Model: ${aircraft?.model}
                > Capacity: ${aircraft?.capacity}
                > Range: ${aircraft?.rangeNm} NM
                > ------------------------------------
                > 
            """.trimMargin(">"))
        }
    }
}

/**
 * Updates details of an existing aircraft.
 */
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

/**
 * Updates details of an existing airline.
 */
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

/**
 * Updates details of an aircraft in a fleet.
 */
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

/**
 * Deletes an aircraft from the system.
 */
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

/**
 * Deletes an airline from the system.
 */
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

/**
 * Deletes an aircraft from a fleet.
 */
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

/**
 * Saves all data (airlines, aircraft, fleets) to XML files.
 */
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

/**
 * Loads all data (airlines, aircraft, fleets) from XML files.
 */
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

/**
 * Exits the application.
 */
fun exitApp() {
    println("Exiting app...")
    exit(0)
}

/**
 * Returns to the main menu.
 */
fun exitToMenu() {
    println("\nReloading...\n")
    mainMenu()
}

/**
 * Finds an aircraft by its IATA code.
 */
fun findAircraftByIata() {
    val iataCode = readNextLine("Enter the Aircraft IATA code you want to search: ")
    println("${aircraftController.findByIATACode(iataCode)}")
}

/**
 * Finds an airline by its IATA code.
 */
fun findAirlineByIata() {
    val iataCode = readNextLine("Enter the Airline IATA code you wish to search for: ")
    println("${airlineController.findByIATACode(iataCode)}")
}

/**
 * Finds an aircraft by its registration code.
 */
fun findAircraftByRegistration() {
    val registration = readNextLine("Enter the Aircraft registration (i.e. EI-DEE): ")
    println(airlineAircraftController.findAircraftByRegistration(registration))
}