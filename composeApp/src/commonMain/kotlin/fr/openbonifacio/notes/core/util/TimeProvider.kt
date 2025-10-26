package fr.openbonifacio.notes.core.util

expect object TimeProvider {
    fun nowMillis(): Long
}

