package persistence

import com.thoughtworks.xstream.XStream
import com.thoughtworks.xstream.io.xml.DomDriver
import models.Aircraft
import models.Airline
import models.AirlineAircraft
import java.io.File
import java.io.FileReader
import java.io.FileWriter

/**
 * Handles serialization and deserialization of objects to and from XML format using XStream.
 *
 * @property file The file where the XML data will be stored or read from.
 */
class XMLSerializer(private val file: File) : Serializer {
    /**
     * Reads and deserializes data from the XML file.
     *
     * @return The deserialized object.
     * @throws Exception If reading fails due to I/O or XML parsing issues.
     */
    @Throws(Exception::class)
    override fun read(): Any {
        val xStream = XStream(DomDriver())
        xStream.allowTypes(arrayOf(Aircraft::class.java, Airline::class.java, AirlineAircraft::class.java))
        val inputStream = xStream.createObjectInputStream(FileReader(file))
        val obj = inputStream.readObject() as Any
        inputStream.close()
        return obj
    }

    /**
     * Serializes and writes an object to the XML file.
     *
     * @param obj The object to serialize and store.
     * @throws Exception If writing fails due to I/O or XML formatting issues.
     */
    @Throws(Exception::class)
    override fun write(obj: Any?) {
        val xStream = XStream(DomDriver())
        val outputStream = xStream.createObjectOutputStream(FileWriter(file))
        outputStream.writeObject(obj)
        outputStream.close()
    }
}
