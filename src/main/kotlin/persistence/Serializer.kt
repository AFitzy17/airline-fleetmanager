package persistence

/**
 * Defines the contract for serialization and deserialization of objects.
 * Implementations of this interface handle reading and writing data to a specific format (e.g., XML, JSON).
 */
interface Serializer {
    /**
     * Writes an object to the underlying storage in the chosen format.
     *
     * @param obj The object to serialize and store.
     * @throws Exception If writing fails due to I/O or format issues.
     */
    @Throws(Exception::class)
    fun write(obj: Any?)

    /**
     * Reads and deserializes data from the underlying storage.
     *
     * @return The deserialized object, or null if reading fails.
     * @throws Exception If reading fails due to I/O or format issues.
     */
    @Throws(Exception::class)
    fun read(): Any?
}
