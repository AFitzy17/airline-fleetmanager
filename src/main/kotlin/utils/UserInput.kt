package utils

/**
 * Reads an integer from the console or returns -1 if input is null or invalid.
 *
 * @return The integer value entered by the user, or -1 if invalid.
 */
fun readIntNotNull() = readlnOrNull()?.toIntOrNull() ?: -1

/**
 * Prompts the user and reads an integer from the console.
 * Keeps asking until a valid integer is entered.
 *
 * @param prompt The message displayed to the user.
 * @return The integer value entered by the user.
 */
fun readNextInt(prompt: String?): Int {
    do {
        try {
            print(prompt)
            return readln().toInt()
        } catch (e: NumberFormatException) {
            System.err.println("\tEnter a number please.")
        }
    } while (true)
}

/**
 * Prompts the user and reads a line of text from the console.
 *
 * @param prompt The message displayed to the user.
 * @return The string entered by the user.
 */
fun readNextLine(prompt: String?): String {
    print(prompt)
    return readln()
}

/**
 * Prompts the user and reads a single character from the console.
 * Keeps asking until a valid character is entered.
 *
 * @param prompt The message displayed to the user.
 * @return The character entered by the user.
 */
fun readNextChar(prompt: String?): Char {
    do {
        try {
            print(prompt)
            return readln().first()
        } catch (e: NoSuchElementException) {
            System.err.println("\tEnter a character please.")
        }
    } while (true)
}

/**
 * Prompts the user and reads a float value from the console.
 * Keeps asking until a valid float is entered.
 *
 * @param prompt The message displayed to the user.
 * @return The float value entered by the user.
 */
fun readNextDouble(prompt: String?): Double {
    do {
        try {
            print(prompt)
            return readln().toDouble()
        } catch (e: NumberFormatException) {
            System.err.println("\tEnter a number please.")
        }
    } while (true)
}

/**
 * Prompts the user and reads a float value from the console.
 * Keeps asking until a valid float is entered.
 *
 * @param prompt The message displayed to the user.
 * @return The float value entered by the user.
 */
fun readNextFloat(prompt: String?): Float {
    do {
        try {
            print(prompt)
            return readln().toFloat()
        } catch (e: NumberFormatException) {
            System.err.println("\tEnter a float please.")
        }
    } while (true)
}

/**
 * Prompts the user and reads a boolean value from the console.
 * Accepts true/false, yes/no, t/f, y/n, 1/0.
 *
 * @param prompt The message displayed to the user.
 * @return True or false based on user input.
 */
fun readNextBoolean(prompt: String?): Boolean {
    do {
        print(prompt)
        val input = readln().lowercase()
        when (input) {
            "true", "t", "yes", "y", "1" -> return true
            "false", "f", "no", "n", "0" -> return false
            else -> System.err.println("Please enter true or false, yes or no, 1 or 0")
        }
    } while (true)
}
