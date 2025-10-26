package fr.openbonifacio.notes.core.util

actual object TimeProvider {
    actual fun nowMillis(): Long = System.currentTimeMillis()
}

