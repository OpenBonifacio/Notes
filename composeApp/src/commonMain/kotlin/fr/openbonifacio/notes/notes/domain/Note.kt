package fr.openbonifacio.notes.notes.domain

import fr.openbonifacio.notes.notes.data.database.SyncStatus

data class Note(
    var id: String,
    var title: String = "",
    var content: String = "",
    var isFavorite: Boolean = false,
    var createdAt: Long = 0L,
    var updatedAt: Long = 0L,
    var deleted: Boolean = false,
    var status: SyncStatus = SyncStatus.SYNCED
)
