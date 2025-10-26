package fr.openbonifacio.notes.core.util

import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

private val formatter: DateTimeFormatter = DateTimeFormatter
    .ofPattern("dd/MM/yyyy HH:mm:ss")
    .withZone(ZoneId.systemDefault())
actual object TimeProvider {
    actual fun nowMillis(): Long = System.currentTimeMillis()

    actual fun formatMillis(millis: Long): String = try {
        formatter.format(Instant.ofEpochMilli(millis))
    } catch (t: Throwable) {
        "-"
    }
}

