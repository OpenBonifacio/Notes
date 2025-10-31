package fr.openbonifacio.notes.notes.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SyncRequestDto(
    @SerialName("user_id") val userId: String,
    @SerialName("notes") val notes: List<SyncNoteChangesDto>
)