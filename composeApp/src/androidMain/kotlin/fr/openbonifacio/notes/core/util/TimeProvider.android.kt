package fr.openbonifacio.notes.core.util

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

@SuppressLint("ConstantLocale")
private val formatter = SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault()).apply {
    timeZone = TimeZone.getDefault()
}
actual object TimeProvider {
    actual fun nowMillis(): Long = System.currentTimeMillis()

    actual fun formatMillis(millis: Long): String {
        return try {
            formatter.format(Date(millis))
        } catch (t: Throwable) {
            "-"
        }
    }
}

