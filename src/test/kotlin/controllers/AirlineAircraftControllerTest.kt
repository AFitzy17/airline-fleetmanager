
package controllers

import models.AirlineAircraft
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*
import persistence.XMLSerializer
import java.io.File

class AirlineAircraftControllerTest {
    private var airlineAircraft1: AirlineAircraft? = null
    private var airlineAircraft2: AirlineAircraft? = null
    private var airlineAircraft3: AirlineAircraft? = null

    private var populatedFleet: AirlineAircraftController? = AirlineAircraftController(XMLSerializer(File("airlineAircraft.xml")))
    private var emptyFleet: AirlineAircraftController? = AirlineAircraftController(XMLSerializer(File("empty-airlineAircraft.xml")))

    @BeforeEach
    fun setup() {
        airlineAircraft1 = AirlineAircraft(1, 101, "EI-ABC", 2010, 12000, 5.5, false)
        airlineAircraft2 = AirlineAircraft(1, 102, "EI-XYZ", 2015, 8000, 4.2, false)
        airlineAircraft3 = AirlineAircraft(2, 201, "FR-123", 2018, 5000, 3.8, false)

        populatedFleet!!.addAircraftToAirline(
            airlineAircraft1!!.airlineId,
            airlineAircraft1!!.aircraftId,
            airlineAircraft1!!.registration,
            airlineAircraft1!!.yearBought,
            airlineAircraft1!!.hoursFlown,
            airlineAircraft1!!.revenuePerYear,
            airlineAircraft1!!.isRetired
        )
        populatedFleet!!.addAircraftToAirline(
            airlineAircraft2!!.airlineId,
            airlineAircraft2!!.aircraftId,
            airlineAircraft2!!.registration,
            airlineAircraft2!!.yearBought,
            airlineAircraft2!!.hoursFlown,
            airlineAircraft2!!.revenuePerYear,
            airlineAircraft2!!.isRetired
        )
        populatedFleet!!.addAircraftToAirline(
            airlineAircraft3!!.airlineId,
            airlineAircraft3!!.aircraftId,
            airlineAircraft3!!.registration,
            airlineAircraft3!!.yearBought,
            airlineAircraft3!!.hoursFlown,
            airlineAircraft3!!.revenuePerYear,
            airlineAircraft3!!.isRetired
        )
    }

    @AfterEach
    fun tearDown() {
        airlineAircraft1 = null
        airlineAircraft2 = null
        airlineAircraft3 = null
        populatedFleet = null
        emptyFleet = null
    }

    @Nested
    inner class AddAircraftTests {
        @Test
        fun `adding an aircraft to an empty fleet adds to the List`() {
            assertEquals(0, emptyFleet!!.getFleetForAirline(1).size)
            emptyFleet!!.addAircraftToAirline(1, 103, "EI-NEW", 2020, 2000, 2.5, false)
            assertEquals(1, emptyFleet!!.getFleetForAirline(1).size)
        }

        @Test
        fun `adding an aircraft to a populated fleet adds to the List`() {
            assertEquals(2, populatedFleet!!.getFleetForAirline(1).size)
            populatedFleet!!.addAircraftToAirline(1, 104, "EI-ADD", 2021, 1000, 1.8, false)
            assertEquals(3, populatedFleet!!.getFleetForAirline(1).size)
        }
    }

    @Nested
    inner class ListAircraftTests {
        @Test
        fun `listAircraftInAirline returns all aircraft for airline when populated`() {
            val list = populatedFleet!!.listAircraftInAirline(1)
            assertTrue(list.contains("EI-ABC"))
            assertTrue(list.contains("EI-XYZ"))
        }

        @Test
        fun `listAircraftInAirline returns no aircraft message when empty`() {
            assertTrue(emptyFleet!!.listAircraftInAirline(99).contains("No aircraft"))
        }
    }

    @Nested
    inner class FindAircraftTests {
        @Test
        fun `findAircraftByRegistration returns correct aircraft`() {
            val found = populatedFleet!!.findAircraftByRegistration("EI-ABC")
            assertNotNull(found)
            assertEquals(101, found!!.aircraftId)
        }

        @Test
        fun `findAircraftByRegistration returns null for invalid registration`() {
            assertNull(populatedFleet!!.findAircraftByRegistration("INVALID"))
        }
    }

    @Nested
    inner class UpdateAircraftTests {
        @Test
        fun `updateAircraftInAirline updates aircraft details`() {
            val result = populatedFleet!!.updateAircraftInAirline(1, 101, "EI-UPDATED", 2012, 15000, 6.0, true)
            assertTrue(result)
            val updated = populatedFleet!!.findAircraftByRegistration("EI-UPDATED")
            assertNotNull(updated)
            assertTrue(updated!!.isRetired)
        }

        @Test
        fun `updateAircraftInAirline returns false for non-existent aircraft`() {
            assertFalse(populatedFleet!!.updateAircraftInAirline(99, 999, "NOPE", 2020, 0, 0.0, false))
        }
    }

    @Nested
    inner class DeleteAircraftTests {
        @Test
        fun `deleteAircraftInAirline removes aircraft from fleet`() {
            assertEquals(3, populatedFleet!!.getFleetForAirline(1).size + populatedFleet!!.getFleetForAirline(2).size)
            populatedFleet!!.deleteAircraftInAirline(1, 101)
            assertNull(populatedFleet!!.findAircraftByRegistration("EI-ABC"))
        }

        @Test
        fun `deleteAircraftInAirline returns null for invalid IDs`() {
            assertNull(populatedFleet!!.deleteAircraftInAirline(99, 999))
        }
    }

    @Nested
    inner class AdditionalLogicTests {
        @Test
        fun `numberOfAircraftInAirline returns correct count`() {
            assertEquals(2, populatedFleet!!.numberOfAircraftInAirline(1))
            assertEquals(1, populatedFleet!!.numberOfAircraftInAirline(2))
        }
    }

    @Nested
    inner class PersistenceTests {
        @Test
        fun `saving and loading an empty collection in XML doesn't crash app`() {
            val storingFleet = AirlineAircraftController(XMLSerializer(File("airlineAircraft.xml")))
            storingFleet.store()

            val loadedFleet = AirlineAircraftController(XMLSerializer(File("airlineAircraft.xml")))
            loadedFleet.load()

            assertEquals(0, storingFleet.getFleetForAirline(1).size)
            assertEquals(0, loadedFleet.getFleetForAirline(1).size)
        }

        @Test
        fun `saving and loading a populated collection in XML doesn't lose data`() {
            val storingFleet = AirlineAircraftController(XMLSerializer(File("airlineAircraft.xml")))
            storingFleet.addAircraftToAirline(1, 101, "EI-ABC", 2010, 12000, 5.5, false)
            storingFleet.addAircraftToAirline(1, 102, "EI-XYZ", 2015, 8000, 4.2, false)
            storingFleet.store()

            val loadedFleet = AirlineAircraftController(XMLSerializer(File("airlineAircraft.xml")))
            loadedFleet.load()

            assertEquals(2, storingFleet.numberOfAircraftInAirline(1))
            assertEquals(2, loadedFleet.numberOfAircraftInAirline(1))
            assertEquals(storingFleet.findAircraftByRegistration("EI-ABC"), loadedFleet.findAircraftByRegistration("EI-ABC"))
        }
    }
}
