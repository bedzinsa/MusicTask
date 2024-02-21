package com.arunasbedzinskas.musictask.viewmodel

import com.arunasbedzinskas.musictask.base.BaseUnitTest
import com.arunasbedzinskas.musictask.ext.mockkRelaxed
import com.arunasbedzinskas.musictask.models.ui.SongUIModel
import com.arunasbedzinskas.musictask.usecase.GetAllSongsUseCase
import io.mockk.coEvery
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.test.get
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class StorageViewModelUnitTest : BaseUnitTest() {

    private val mockedSongs = listOf<SongUIModel>(mockkRelaxed(), mockkRelaxed())

    override fun Module.initDependencies() {
        factory {
            mockkRelaxed<GetAllSongsUseCase> {
                coEvery { this@mockkRelaxed.invoke() } returns flowOf(mockedSongs)
            }
        }

        viewModel {
            StorageViewModel(
                get(),
                mockkRelaxed(),
                get()
            )
        }
    }

    @Test
    fun `given mockedSongs 2 when view model init then songsFlow same mocked songs emitted`() =
        runTest {
            // Given
            val storageViewModel: StorageViewModel = get()

            // When
            advanceUntilIdle() // Wait until coroutines are executed

            // Then
            val result = storageViewModel.songsFlow.value
            assertEquals(mockedSongs.size, result.size)
        }
}