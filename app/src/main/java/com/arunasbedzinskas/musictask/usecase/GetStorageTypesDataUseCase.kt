package com.arunasbedzinskas.musictask.usecase

import com.arunasbedzinskas.musictask.datastore.MusicDataStore
import com.arunasbedzinskas.musictask.ext.toTrackLength
import com.arunasbedzinskas.musictask.models.data.StorageTypeDataModel
import com.arunasbedzinskas.musictask.models.enums.StorageType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.transform
import java.time.Duration

fun interface GetStorageTypesDataUseCase {

    operator fun invoke(): Flow<List<StorageTypeDataModel>>
}

internal class GetStorageTypesDataUseCaseImpl(
    private val musicDataStore: MusicDataStore,
) : GetStorageTypesDataUseCase {


    override fun invoke(): Flow<List<StorageTypeDataModel>> =
        musicDataStore.getSongs().transform { memorySongs ->
            val totalLength = memorySongs.sumOf { it.length }
            emit(
                listOf(
                    StorageTypeDataModel(
                        StorageType.Memory,
                        Duration.ofSeconds(totalLength).toTrackLength()
                    ),
                    StorageTypeDataModel(StorageType.Filesystem, "0m 0s") // TODO
                )
            )
        }
}
