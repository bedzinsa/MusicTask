package com.arunasbedzinskas.musictask.usecase

import com.arunasbedzinskas.musictask.database.dao.SongDao
import com.arunasbedzinskas.musictask.datastore.SongDataStore
import com.arunasbedzinskas.musictask.ext.toTrackLength
import com.arunasbedzinskas.musictask.models.data.StorageTypeDataModel
import com.arunasbedzinskas.musictask.models.enums.StorageType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combineTransform
import java.time.Duration

fun interface GetStorageTypesDataUseCase {

    operator fun invoke(): Flow<List<StorageTypeDataModel>>
}

internal class GetStorageTypesDataUseCaseImpl(
    private val songDataStore: SongDataStore,
    private val songDao: SongDao
) : GetStorageTypesDataUseCase {


    override fun invoke(): Flow<List<StorageTypeDataModel>> =
        songDataStore.getSongs().combineTransform(songDao.getAll()) { memorySongs, daoSongs ->
            val memoryTotalLength = memorySongs.sumOf { it.length }
            val daoTotalLength = daoSongs.sumOf { it.length }
            emit(
                listOf(
                    StorageTypeDataModel(
                        StorageType.Memory,
                        Duration.ofSeconds(memoryTotalLength).toTrackLength()
                    ),
                    StorageTypeDataModel(
                        StorageType.Filesystem,
                        Duration.ofSeconds(daoTotalLength).toTrackLength()
                    )
                )
            )
        }
}
