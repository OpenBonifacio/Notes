package fr.openbonifacio.notes.notes.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SyncNoteChangesDto(
    @SerialName("id") val id: Long,
    @SerialName("content") val content: String,
    @SerialName("updatedAt") val upatedAt: Long,
    @SerialName("createdAt") val createdAt: Long,
    @SerialName("deleted") val delete: Boolean
)