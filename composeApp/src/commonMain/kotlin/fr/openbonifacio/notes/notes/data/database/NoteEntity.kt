package fr.openbonifacio.notes.notes.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid


enum class SyncStatus {
    SYNCED,
    PENDING_CREATE,
    PENDING_UPDATE,
    PENDING_DELETE
}

@Entity
data class NoteEntity(
    @PrimaryKey() val id: String,
    val title: String,
    val content: String,
    val createdAt: Long = 0L,
    val updatedAt: Long = 0L,
    val deleted: Boolean = false,
    val status: SyncStatus = SyncStatus.SYNCED
)