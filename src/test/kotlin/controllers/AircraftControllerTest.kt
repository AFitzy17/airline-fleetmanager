
package controllers

import models.Aircraft
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*
import persistence.XMLSerializer
import java.io.File

class AircraftControllerTest {
    private var a320: Aircraft? = null
    private var b737: Aircraft? = null
    private var b747: Aircraft? = null
    private var a380: Aircraft? = null
    private var concorde: Aircraft? = null

    private var populatedAircraft: AircraftController? = AircraftController(XMLSerializer(File("aircraft.xml")))
    private var emptyAircraft: AircraftController? = AircraftController(XMLSerializer(File("empty-aircraft.xml")))

    @BeforeEach
    fun setup() {
        a320 = Aircraft(0, "320", "Airbus", "A320", 180, 3300, 1988, true)
        b737 = Aircraft(1, "737", "Boeing", "737", 189, 3500, 1968, true)
        b747 = Aircraft(2, "747", "Boeing", "747", 416, 7200, 1970, false)
        a380 = Aircraft(3, "380", "Airbus", "A380", 555, 8200, 2007, true)
        concorde = Aircraft(4, "CON", "Aerospatiale", "Concorde", 100, 3900, 1976, false)

        populatedAircraft!!.addAircraft(a320!!)
        populatedAircraft!!.addAircraft(b737!!)
        populatedAircraft!!.addAircraft(b747!!)
        populatedAircraft!!.addAircraft(a380!!)
        populatedAircraft!!.addAircraft(concorde!!)
    }

    @AfterEach
    fun tearDown() {
        a320 = null
        b737 = null
        b747 = null
        a380 = null
        concorde = null
        populatedAircraft = null
        emptyAircraft = null
    }

    @Nested
    inner class AddAircraftTests {
        @Test
        fun `adding an aircraft to an empty list adds to the List`() {
            val newAircraft = Aircraft(0, "787", "Boeing", "787 Dreamliner", 242, 7635, 2011, true)
            assertEquals(0, emptyAircraft!!.numberOfAircraft())
            emptyAircraft!!.addAircraft(newAircraft)
            assertEquals(1, emptyAircraft!!.numberOfAircraft())
        }

        @Test
        fun `adding an aircraft to a populated list adds to the List`() {
            val newAircraft = Aircraft(0, "787", "Boeing", "787 Dreamliner", 242, 7635, 2011, true)
            assertEquals(5, populatedAircraft!!.numberOfAircraft())
            populatedAircraft!!.addAircraft(newAircraft)
            assertEquals(6, populatedAircraft!!.numberOfAircraft())
        }
    }

    @Nested
    inner class ListAircraftTests {
        @Test
        fun `listAllAircraft returns all aircraft when populated`() {
            assertEquals(5, populatedAircraft!!.numberOfAircraft())
            assertTrue(populatedAircraft!!.listAllAircraft().contains("A320"))
            assertTrue(populatedAircraft!!.listAllAircraft().contains("Boeing"))
        }

        @Test
        fun `listAllAircraft returns no aircraft message when empty`() {
            assertEquals(0, emptyAircraft!!.numberOfAircraft())
            assertTrue(emptyAircraft!!.listAllAircraft().lowercase().contains("no aircraft"))
        }

        @Test
        fun `listRetiredAircraft returns only aircraft not in production`() {
            assertTrue(populatedAircraft!!.listRetiredAircraft().contains("747"))
            assertTrue(populatedAircraft!!.listRetiredAircraft().contains("Concorde"))
            assertFalse(populatedAircraft!!.listRetiredAircraft().contains("A320"))
        }

        @Test
        fun `listAircraftByCapacityRange returns aircraft within range`() {
            assertTrue(populatedAircraft!!.listAircraftByCapacityRange(150, 200).contains("A320"))
            assertTrue(populatedAircraft!!.listAircraftByCapacityRange(150, 200).contains("737"))
            assertFalse(populatedAircraft!!.listAircraftByCapacityRange(150, 200).contains("A380"))
        }

        @Test
        fun `listAircraftByCapacityRange returns no aircraft when range is too small`() {
            assertTrue(populatedAircraft!!.listAircraftByCapacityRange(10, 50).contains("No aircraft"))
        }
    }

    @Nested
    inner class FindAircraftTests {
        @Test
        fun `findAircraft returns correct aircraft for valid index`() {
            assertEquals("A320", populatedAircraft!!.findAircraft(0)!!.model)
        }

        @Test
        fun `findByIATACode returns correct aircraft`() {
            assertTrue(populatedAircraft!!.findByIATACode("737").contains("Boeing"))
        }

        @Test
        fun `findByIATACode returns no aircraft`() {
            assertEquals("No aircraft found with IATA Code: ZZ\n", populatedAircraft!!.findByIATACode("ZZ"))
        }
    }

    @Nested
    inner class UpdateAircraftTests {
        @Test
        fun `updateAircraft updates aircraft details`() {
            assertTrue(populatedAircraft!!.updateAircraft(0, Aircraft(0, "320", "Airbus", "A320neo", 186, 3500, 2016, true)))
            assertEquals("A320neo", populatedAircraft!!.findAircraft(0)!!.model)
        }

        @Test
        fun `updateAircraft returns false for invalid index`() {
            assertFalse(populatedAircraft!!.updateAircraft(99, a320))
        }
    }

    @Nested
    inner class DeleteAircraftTests {
        @Test
        fun `deleteAircraft removes aircraft from list`() {
            assertEquals(5, populatedAircraft!!.numberOfAircraft())
            populatedAircraft!!.deleteAircraft(0)
            assertEquals(4, populatedAircraft!!.numberOfAircraft())
        }

        @Test
        fun `deleteAircraft returns null for invalid index`() {
            assertNull(populatedAircraft!!.deleteAircraft(99))
        }
    }

    @Nested
    inner class AdditionalLogicTests {
        @Test
        fun `numberOfAircraftNotInProduction returns correct count`() {
            assertEquals(2, populatedAircraft!!.numberOfAircraftNotInProduction())
        }
    }

    @Nested
    inner class PersistenceTests {
        @Test
        fun `saving and loading an empty collection in XML doesn't crash app`() {
            val storingAircraft = AircraftController(XMLSerializer(File("aircraft.xml")))
            storingAircraft.store()

            val loadedAircraft = AircraftController(XMLSerializer(File("aircraft.xml")))
            loadedAircraft.load()

            assertEquals(0, storingAircraft.numberOfAircraft())
            assertEquals(0, loadedAircraft.numberOfAircraft())
            assertEquals(storingAircraft.numberOfAircraft(), loadedAircraft.numberOfAircraft())
        }

        @Test
        fun `saving and loading a populated collection in XML doesn't lose data`() {
            val storingAircraft = AircraftController(XMLSerializer(File("aircraft.xml")))
            storingAircraft.addAircraft(a320!!)
            storingAircraft.addAircraft(b737!!)
            storingAircraft.addAircraft(b747!!)
            storingAircraft.store()

            val loadedAircraft = AircraftController(XMLSerializer(File("aircraft.xml")))
            loadedAircraft.load()

            assertEquals(3, storingAircraft.numberOfAircraft())
            assertEquals(3, loadedAircraft.numberOfAircraft())
            assertEquals(storingAircraft.findAircraft(0), loadedAircraft.findAircraft(0))
            assertEquals(storingAircraft.findAircraft(1), loadedAircraft.findAircraft(1))
            assertEquals(storingAircraft.findAircraft(2), loadedAircraft.findAircraft(2))
        }
    }
}
