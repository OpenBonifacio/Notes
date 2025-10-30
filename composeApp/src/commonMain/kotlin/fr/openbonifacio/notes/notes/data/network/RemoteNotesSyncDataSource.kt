package fr.openbonifacio.notes.notes.data.network

import fr.openbonifacio.notes.core.domain.Result
import fr.openbonifacio.notes.core.domain.DataError
import fr.openbonifacio.notes.notes.data.dto.SyncRequestDto
import fr.openbonifacio.notes.notes.data.dto.SyncResponseDto

interface RemoteNotesSyncDataSource{
    suspend fun syncNotes(requestDto: SyncRequestDto): Result<SyncResponseDto, DataError.Remote>
}