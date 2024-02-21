package com.arunasbedzinskas.musictask.usecase

import com.arunasbedzinskas.musictask.base.BaseUnitTest
import com.arunasbedzinskas.musictask.dataaccess.GenresWithSongsDataAccess
import com.arunasbedzinskas.musictask.datastore.SongDataStore
import com.arunasbedzinskas.musictask.ext.coVerifyNone
import com.arunasbedzinskas.musictask.ext.mockkRelaxed
import com.arunasbedzinskas.musictask.models.data.SongDataModel
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.koin.core.module.Module
import org.koin.test.inject

class SaveSongToDataStoreUseCaseUnitTest : BaseUnitTest() {

    private val useCase: SaveSongToDataStoreUseCaseImpl by inject()
    private val dataStore: SongDataStore by inject()

    private val testDataModel: SongDataModel = mockkRelaxed {
        every { id } returns 1
    }

    override fun Module.initDependencies() {
        single {
            mockkRelaxed<GenresWithSongsDataAccess> {
                coEvery { getAllSongs() } returns listOf(
                    mockkRelaxed { every { id } returns 2 },
                    testDataModel
                )
            }
        }
        single {
            mockkRelaxed<SongDataStore> {
                coEvery { addSong(any()) } returns Unit
            }
        }

        factory { SaveSongToDataStoreUseCaseImpl(get(), get()) }
    }

    @Test
    fun `given songId 1 when invoke then addSong called with songId 1`() =
        runTest {
            // Given
            val songId = 1

            // When
            useCase(songId)

            // Then
            coVerify { dataStore.addSong(testDataModel) }
        }

    @Test
    fun `given songId 3 when invoke then addSong not called`() =
        runTest {
            // Given
            val songId = 3

            // When
            useCase(songId)

            // Then
            coVerifyNone { dataStore.addSong(testDataModel) }
        }
}
