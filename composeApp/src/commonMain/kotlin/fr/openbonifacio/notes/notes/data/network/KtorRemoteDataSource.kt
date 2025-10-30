package fr.openbonifacio.notes.notes.data.network

import fr.openbonifacio.notes.core.data.safeCall
import fr.openbonifacio.notes.core.domain.DataError
import fr.openbonifacio.notes.core.domain.Result
import fr.openbonifacio.notes.notes.data.dto.SyncRequestDto
import fr.openbonifacio.notes.notes.data.dto.SyncResponseDto
import io.ktor.client.HttpClient
import io.ktor.client.request.post
import io.ktor.client.request.setBody

private const val BASE_URL = "http://127.0.0.1:8001/api"

class KtorRemoteDataSource(
    private val client: HttpClient
) : RemoteNotesSyncDataSource{
    override suspend fun syncNotes(requestDto: SyncRequestDto): Result<SyncResponseDto, DataError.Remote> {
        return safeCall <SyncResponseDto>{
            client.post(
                urlString = "$BASE_URL/notes/sync",
            ){
                setBody(requestDto)
            }
        }
    }
}