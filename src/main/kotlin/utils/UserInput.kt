package utils

fun readIntNotNull() = readlnOrNull()?.toIntOrNull() ?: -1

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

fun readDoubleNotNull() = readlnOrNull()?.toDoubleOrNull() ?: -1.0

fun readNextDouble(prompt: String?): Double {
    do {
        try {
            print(prompt)
            return readln().toDouble()
        } catch (e: NumberFormatException) {
            System.err.println("\tEnter a decimal number please.")
        }
    } while (true)
}

fun readFloatNotNull() = readlnOrNull()?.toFloatOrNull() ?: -1.0f

fun readNextFloat(prompt: String?): Float {
    do {
        try {
            print(prompt)
            return readln().toFloat()
        } catch (e: NumberFormatException) {
            System.err.println("\tEnter a decimal number please.")
        }
    } while (true)
}


fun readNextLine(prompt: String?): String {
    print(prompt)
    return readln()
}


fun readNextChar(prompt: String?): Char {
    do {
        try {
            print(prompt)
            return readln().first()
        } catch (e: Exception) {
            System.err.println("\tEnter a character please.")
        }
    } while (true)
}

fun readNextBoolean(prompt: String?): Boolean {
    do {
        try {
            print(prompt)
            val input = readln().trim().lowercase()
            return when (input) {
                "true" -> true
                "false" -> false
                else -> throw IllegalArgumentException("Please enter 'true' or 'false'.")
            }
        } catch (e: IllegalArgumentException) {
            System.err.println("\tEnter 'true' or 'false' please.")
        }
    } while (true)
}