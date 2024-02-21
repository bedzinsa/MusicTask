package com.arunasbedzinskas.musictask.di

import androidx.room.Room
import com.arunasbedzinskas.musictask.dataaccess.GenresWithSongsDataAccess
import com.arunasbedzinskas.musictask.dataaccess.GenresWithSongsDataAccessImpl
import com.arunasbedzinskas.musictask.database.Database
import com.arunasbedzinskas.musictask.database.Database.Companion.DB_NAME
import com.arunasbedzinskas.musictask.database.dao.SongDao
import com.arunasbedzinskas.musictask.datastore.MusicDataStore
import com.arunasbedzinskas.musictask.dispatchers.AppDispatchers
import com.arunasbedzinskas.musictask.models.enums.StorageType
import com.arunasbedzinskas.musictask.usecase.GetAllGenresUseCase
import com.arunasbedzinskas.musictask.usecase.GetAllGenresUseCaseImpl
import com.arunasbedzinskas.musictask.usecase.GetAllSongsUseCase
import com.arunasbedzinskas.musictask.usecase.GetAllSongsUseCaseImpl
import com.arunasbedzinskas.musictask.usecase.GetGenreUseCase
import com.arunasbedzinskas.musictask.usecase.GetGenreUseCaseImpl
import com.arunasbedzinskas.musictask.usecase.GetLimitGenresUseCase
import com.arunasbedzinskas.musictask.usecase.GetLimitGenresUseCaseImpl
import com.arunasbedzinskas.musictask.usecase.GetStorageTypesDataUseCase
import com.arunasbedzinskas.musictask.usecase.GetStorageTypesDataUseCaseImpl
import com.arunasbedzinskas.musictask.usecase.MapSongUIUseCase
import com.arunasbedzinskas.musictask.usecase.MapSongUIUseCaseImpl
import com.arunasbedzinskas.musictask.usecase.SaveSongToDataStoreUseCase
import com.arunasbedzinskas.musictask.usecase.SaveSongToDataStoreUseCaseImpl
import com.arunasbedzinskas.musictask.viewmodel.GenreViewModel
import com.arunasbedzinskas.musictask.viewmodel.HomeViewModel
import com.arunasbedzinskas.musictask.viewmodel.StorageViewModel
import com.google.gson.GsonBuilder
import kotlinx.coroutines.Dispatchers
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object Koin {

    fun modules() = listOf(
        genericModule(),
        dataAccessModule(),
        useCaseModule(),
        databaseModule(),
        viewModelsModule()
    )

    private fun genericModule() = module {
        single {
            AppDispatchers(
                Dispatchers.Default,
                Dispatchers.Main,
                Dispatchers.IO
            )
        }
        single { GsonBuilder().create() }

        single { MusicDataStore(get(), get()) }
    }

    private fun viewModelsModule() = module {
        viewModel { HomeViewModel(get(), get(), get()) }
        viewModel { (genreId: Int) -> GenreViewModel(genreId, get(), get()) }
        viewModel { (storageType: StorageType) -> StorageViewModel(storageType, get(), get(), get())}
    }

    private fun dataAccessModule() = module {
        single<GenresWithSongsDataAccess> { GenresWithSongsDataAccessImpl(get(), get()) }
    }

    private fun useCaseModule() = module {
        factory<GetAllGenresUseCase> { GetAllGenresUseCaseImpl(get(), get()) }
        factory<GetLimitGenresUseCase> { GetLimitGenresUseCaseImpl(get()) }
        factory<GetGenreUseCase> { GetGenreUseCaseImpl(get()) }
        factory<GetAllSongsUseCase> { GetAllSongsUseCaseImpl(get(), get(), get()) }
        factory<GetStorageTypesDataUseCase> { GetStorageTypesDataUseCaseImpl(get()) }
        factory<MapSongUIUseCase>{ MapSongUIUseCaseImpl(get()) }
        factory<SaveSongToDataStoreUseCase> { SaveSongToDataStoreUseCaseImpl(get(), get()) }
    }

    private fun databaseModule() = module {
        single {
            Room.databaseBuilder(
                androidContext(),
                Database::class.java,
                DB_NAME
            ).build()
        }

        single<SongDao> { get<Database>().songDao() }
    }
}
