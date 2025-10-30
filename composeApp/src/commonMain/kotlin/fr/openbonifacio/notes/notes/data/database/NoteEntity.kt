package fr.openbonifacio.notes.notes.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey


enum class SyncStatus {
    SYNCED,
    PENDING_CREATE,
    PENDING_UPDATE,
    PENDING_DELETE
}

@Entity
data class NoteEntity(
    @PrimaryKey(autoGenerate = true) val id: Long,
    val title: String,
    val content: String,
    val createdAt: Long = 0L,
    val updatedAt: Long = 0L,
    val deleted: Boolean = false,
    val status: SyncStatus = SyncStatus.SYNCED
)