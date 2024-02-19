package com.arunasbedzinskas.musictask.di

import androidx.room.Room
import com.arunasbedzinskas.musictask.dataaccess.GenresWithSongsDataAccess
import com.arunasbedzinskas.musictask.dataaccess.GenresWithSongsDataAccessImpl
import com.arunasbedzinskas.musictask.database.Database
import com.arunasbedzinskas.musictask.database.Database.Companion.DB_NAME
import com.arunasbedzinskas.musictask.database.dao.SongDao
import com.arunasbedzinskas.musictask.dispatchers.AppDispatchers
import com.arunasbedzinskas.musictask.usecase.GetGenresWith5SongsUseCase
import com.arunasbedzinskas.musictask.usecase.GetGenresWith5SongsUseCaseImpl
import com.arunasbedzinskas.musictask.usecase.GetGenresWithSongsUseCase
import com.arunasbedzinskas.musictask.usecase.GetGenresWithSongsUseCaseImpl
import com.arunasbedzinskas.musictask.usecase.GetStorageTypesDataUseCase
import com.arunasbedzinskas.musictask.usecase.GetStorageTypesDataUseCaseImpl
import com.arunasbedzinskas.musictask.viewmodel.HomeViewModel
import com.arunasbedzinskas.musictask.viewmodel.MainViewModel
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
    }

    private fun viewModelsModule() = module {
        viewModel { MainViewModel() }
        viewModel { HomeViewModel(get(), get(), get()) }
    }

    private fun dataAccessModule() = module {
        single<GenresWithSongsDataAccess> { GenresWithSongsDataAccessImpl(get(), get()) }
    }

    private fun useCaseModule() = module {
        factory<GetGenresWithSongsUseCase> { GetGenresWithSongsUseCaseImpl(get(), get()) }
        factory<GetGenresWith5SongsUseCase> { GetGenresWith5SongsUseCaseImpl(get()) }

        factory<GetStorageTypesDataUseCase> { GetStorageTypesDataUseCaseImpl() }
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
