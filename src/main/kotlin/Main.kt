import controllers.AircraftController
import controllers.AirlineAircraftController
import models.Airline
import controllers.AirlineController
import models.Aircraft
import utils.readNextBoolean
import utils.readNextDouble
import utils.readNextInt
import utils.readNextLine

val airlineController = AirlineController()
val aircraftController = AircraftController()
val airlineAircraftController = AirlineAircraftController()

fun main() {

    var choice: Int

    do {
        choice = readNextInt("""
            > -----------------------------------------
            > |  FLEET MASTER V0.2                    |
            > -----------------------------------------
            > | CRUD Functions:                       |
            > |                                       |
            > |     1. Add Aircraft                   |
            > |     2. Add Airline                    |
            > |     3. Add Aircraft to Airline        |
            > |                                       |
            > -----------------------------------------
            > | List Functions:                       |
            > |                                       |
            > |     4. View All Aircraft              |
            > |     5. View All Airlines              |
            > |     6. View All Airline Aircraft      |
            > |                                       |
            > -----------------------------------------
            > |     0. Exit                           |
            > -----------------------------------------
            > ==> """.trimMargin(">"))

        when (choice) {
            1 -> {
                aircraftMenu()
            }
            2 -> {
                airlineMenu()
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
                listAllAircraft()
            }
            5 -> {

            }
            6 -> {
                val airlineId = readNextInt("Enter airline id: ")
                println("Airline Fleet: \n${airlineAircraftController.listAircraftInAirline(airlineId)}")
            }
            7 -> {
                updateAircraft()
            }
            0 -> {
                println("Exiting app...")
            }
            else -> println("Invalid choice: ${choice}. Please try again.")
        }
    } while (choice != 0)
}

fun aircraftMenu() {

    var choice: Int

    do {
        choice = readNextInt("""
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
            > ==> """.trimMargin(">"))

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
            else -> println("Invalid choice: ${choice}. Please try again.")
        }
    } while (choice != 0)
}

fun airlineMenu() {

    var choice: Int

    do {
        choice = readNextInt("""
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
            > ==> """.trimMargin(">"))

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
            else -> println("Invalid choice: ${choice}. Please try again.")
        }
    } while (choice != 0)
}

fun addAircraft() {
    //Add aircraft
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
    val airlineName= readNextLine("Enter the name of the Airline: ")
    val country = readNextLine("Enter the country of origin for the Airline: ")
    val yearFounded = readNextInt("Enter the year the Airline was founded: ")
    val isActive = readNextBoolean("Enter whether the Airline is still in operation: ")

    val airline = Airline(0, iataCode, airlineName, country, yearFounded, isActive)
    airlineController.addAirline(airline)
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

fun updateAircraft() {
    listAllAircraft()
    if (aircraftController.numberOfAircraft() > 0) {
        //ask user for index to update
        val indexToUpdate = readNextInt("Enter the index number of the aircraft you wish to update: ")
        if (aircraftController.isValidIndex(indexToUpdate)) {
            val manufacturer = readNextLine("Please enter the manufacturer name: ")
            val model = readNextLine("Please enter the model of aircraft: ")
            val capacity = readNextInt("Please enter the seat capacity of the aircraft: ")
            val rangeNm = readNextInt("Enter the aircraft flight range in nautical miles: ")
            val yearIntroduced = readNextInt("Enter the year it started production: ")
            val inProduction = readNextBoolean("Enter whether the aircraft is still in production: ")

            if (aircraftController.updateAircraft(indexToUpdate, Aircraft(indexToUpdate, manufacturer, model, capacity, rangeNm, yearIntroduced, inProduction))) {
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

            if (airlineController.updateAirline(indexToUpdate, Airline(indexToUpdate, iataCode, airlineName, country, yearFounded, isActive))) {
                println("Update successful!")
            } else {
                println("Update Failed.")
            }
        } else {
            println("There are no airline at this index number.")
        }
    }
}

fun deleteAircraft() {
    listAllAircraft()
    if (aircraftController.numberOfAircraft() > 0) {
        //ask user for index to delete
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
        //ask user for index to delete
        val indexToDelete = readNextInt("Enter the index of the Airline you'd like to delete: ")
        val airlineToDelete = airlineController.deleteAirline(indexToDelete)
        if (airlineToDelete != null) {
            println("Deleted successfully!")
        } else {
            println("Failed to delete airline.")
        }
    }
}