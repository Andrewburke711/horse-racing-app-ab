package utils

import ie.setu.models.Horse
import ie.setu.models.Race


fun formatListString(notesToFormat: List<Race>): String =
    notesToFormat
        .joinToString(separator = "\n") { note -> "$note" }

fun formatSetString(itemsToFormat: Set<Horse>): String =
    itemsToFormat
        .joinToString(separator = "\n") { item -> "\t$item" }


