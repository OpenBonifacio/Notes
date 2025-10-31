package fr.openbonifacio.notes.core.util

import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
fun generatedUUID(): String{
    return Uuid.random().toString()
}