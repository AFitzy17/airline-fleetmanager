package controllers

import models.Airline
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import persistence.XMLSerializer
import java.io.File

class AirlineControllerTest {
    private var aerLingus: Airline? = null
    private var ryanair: Airline? = null
    private var emirates: Airline? = null
    private var qatar: Airline? = null
    private var delta: Airline? = null

    private var populatedAirlines: AirlineController? = AirlineController(XMLSerializer(File("airlines.xml")))
    private var emptyAirlines: AirlineController? = AirlineController(XMLSerializer(File("empty-airlines.xml")))

    @BeforeEach
    fun setup() {
        aerLingus = Airline(0, "EI", "Aer Lingus", "Ireland", 1936, true)
        ryanair = Airline(1, "FR", "Ryanair", "Ireland", 1984, true)
        emirates = Airline(2, "EK", "Emirates", "UAE", 1985, true)
        qatar = Airline(3, "QR", "Qatar Airways", "Qatar", 1993, true)
        delta = Airline(4, "DL", "Delta Airlines", "USA", 1924, false)

        // Add airlines to populated controller
        populatedAirlines!!.addAirline(aerLingus!!)
        populatedAirlines!!.addAirline(ryanair!!)
        populatedAirlines!!.addAirline(emirates!!)
        populatedAirlines!!.addAirline(qatar!!)
        populatedAirlines!!.addAirline(delta!!)
    }

    @AfterEach
    fun tearDown() {
        aerLingus = null
        ryanair = null
        emirates = null
        qatar = null
        delta = null
        populatedAirlines = null
        emptyAirlines = null
    }

    @Nested
    inner class AddAirlineTests {
        @Test
        fun `adding an airline to an empty list adds to the List`() {
            val newAirline = Airline(0, "BA", "British Airways", "UK", 1974, true)
            assertEquals(0, emptyAirlines!!.numberOfAirlines())
            emptyAirlines!!.addAirline(newAirline)
            assertEquals(1, emptyAirlines!!.numberOfAirlines())
        }

        @Test
        fun `adding an airline to a populated list adds to the List`() {
            val newAirline = Airline(0, "BA", "British Airways", "UK", 1974, true)
            assertEquals(5, populatedAirlines!!.numberOfAirlines())
            populatedAirlines!!.addAirline(newAirline)
            assertEquals(6, populatedAirlines!!.numberOfAirlines())
        }
    }

    @Nested
    inner class ListAirlinesTests {
        @Test
        fun `listAllAirlines returns all airlines when populated`() {
            assertEquals(5, populatedAirlines!!.numberOfAirlines())
            assertTrue(populatedAirlines!!.listAllAirlines().contains("Aer Lingus"))
            assertTrue(populatedAirlines!!.listAllAirlines().contains("Ryanair"))
        }

        @Test
        fun `listAllAirlines returns no airlines message when empty`() {
            assertEquals(0, emptyAirlines!!.numberOfAirlines())
            assertTrue(emptyAirlines!!.listAllAirlines().lowercase().contains("no airlines"))
        }

        @Test
        fun `listActiveAirlines returns only active airlines`() {
            assertTrue(populatedAirlines!!.listActiveAirlines().contains("Aer Lingus"))
            assertFalse(populatedAirlines!!.listActiveAirlines().contains("Delta Airlines"))
        }
    }

    @Nested
    inner class FindAirlineTests {
        @Test
        fun `findAirline returns correct airline for valid index`() {
            assertEquals("Aer Lingus", populatedAirlines!!.findAirline(0)!!.airlineName)
        }

        @Test
        fun `findByIATACode returns correct airline`() {
            assertTrue(populatedAirlines!!.findByIATACode("FR").contains("Ryanair"))
        }

        @Test
        fun `findByIATACode returns no airline`() {
            assertEquals("No airline found with IATA Code: ZZ\n", populatedAirlines!!.findByIATACode("ZZ"))
        }
    }

    @Nested
    inner class UpdateAirlineTests {
        @Test
        fun `updateAirline updates airline details`() {
            assertTrue(populatedAirlines!!.updateAirline(0, Airline(0, "EI", "Aer Bingus", "Ireland", 1936, true)))
            assertEquals("Aer Bingus", populatedAirlines!!.findAirline(0)!!.airlineName)
        }

        @Test
        fun `updateAirline returns false for invalid index`() {
            assertFalse(populatedAirlines!!.updateAirline(99, aerLingus))
        }
    }

    @Nested
    inner class DeleteAirlineTests {
        @Test
        fun `deleteAirline removes airline from list`() {
            assertEquals(5, populatedAirlines!!.numberOfAirlines())
            populatedAirlines!!.deleteAirline(0)
            assertEquals(4, populatedAirlines!!.numberOfAirlines())
        }

        @Test
        fun `deleteAirline returns null for invalid index`() {
            assertNull(populatedAirlines!!.deleteAirline(99))
        }
    }

    @Nested
    inner class PersistenceTests {
        @Test
        fun `saving and loading an empty collection in XML doesn't crash app`() {
            val storingAirlines = AirlineController(XMLSerializer(File("airlines.xml")))
            AirlineController(XMLSerializer(File("airlines.xml"))).store()

            val loadedAirlines = AirlineController(XMLSerializer(File("airlines.xml")))
            loadedAirlines.load()

            assertEquals(0, storingAirlines.numberOfAirlines())
            assertEquals(0, loadedAirlines.numberOfAirlines())
            assertEquals(storingAirlines.numberOfAirlines(), loadedAirlines.numberOfAirlines())
        }

        @Test
        fun `saving and loading a populated collection in XML doesn't lose data`() {
            val storingAirlines = AirlineController(XMLSerializer(File("airlines.xml")))
            storingAirlines.addAirline(aerLingus!!)
            storingAirlines.addAirline(ryanair!!)
            storingAirlines.addAirline(emirates!!)
            storingAirlines.store()

            val loadedAirlines = AirlineController(XMLSerializer(File("airlines.xml")))
            loadedAirlines.load()

            assertEquals(3, storingAirlines.numberOfAirlines())
            assertEquals(3, loadedAirlines.numberOfAirlines())
            assertEquals(storingAirlines.findAirline(0), loadedAirlines.findAirline(0))
            assertEquals(storingAirlines.findAirline(1), loadedAirlines.findAirline(1))
            assertEquals(storingAirlines.findAirline(2), loadedAirlines.findAirline(2))
        }
    }
}
