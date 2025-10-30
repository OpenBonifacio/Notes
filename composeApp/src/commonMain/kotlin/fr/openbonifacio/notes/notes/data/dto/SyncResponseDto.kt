package fr.openbonifacio.notes.notes.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SyncResponseDto(
    @SerialName("status") val status: String,
    @SerialName("updated") val updated: List<SyncNoteChangesDto>
)