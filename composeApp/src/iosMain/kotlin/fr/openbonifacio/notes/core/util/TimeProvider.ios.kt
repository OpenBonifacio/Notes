package fr.openbonifacio.notes.core.util

import platform.Foundation.NSDate
import platform.Foundation.timeIntervalSince1970

actual object TimeProvider {
    actual fun nowMillis(): Long = (NSDate().timeIntervalSince1970 * 1000.0).toLong()
}
