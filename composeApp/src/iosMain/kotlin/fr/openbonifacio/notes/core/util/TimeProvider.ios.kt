package fr.openbonifacio.notes.core.util

import platform.Foundation.NSDate
import platform.Foundation.NSDateFormatter
import platform.Foundation.NSLocale
import platform.Foundation.NSTimeZone
import platform.Foundation.currentLocale
import platform.Foundation.localTimeZone
import platform.Foundation.timeIntervalSince1970
import kotlin.getValue

private val formatter by lazy {
    NSDateFormatter().apply {
        dateFormat = "dd/MM/yyyy HH:mm:ss"
        locale = NSLocale.currentLocale()
        timeZone = NSTimeZone.localTimeZone
    }
}

actual object TimeProvider {
    actual fun nowMillis(): Long = (NSDate().timeIntervalSince1970 * 1000.0).toLong()

    actual fun formatMillis(millis: Long): String = try {
        formatter.stringFromDate(NSDate(millis / 1000.0))
    } catch (t: Throwable) {
        "-"
    }
}
