package com.arunasbedzinskas.musictask.di

import androidx.room.Room
import com.arunasbedzinskas.musictask.dataaccess.GenresWithSongsDataAccess
import com.arunasbedzinskas.musictask.dataaccess.GenresWithSongsDataAccessImpl
import com.arunasbedzinskas.musictask.database.Database
import com.arunasbedzinskas.musictask.database.Database.Companion.DB_NAME
import com.arunasbedzinskas.musictask.database.dao.SongDao
import com.arunasbedzinskas.musictask.usecase.GetGenresWithSongsUseCase
import com.arunasbedzinskas.musictask.usecase.GetGenresWithSongsUseCaseImpl
import com.google.gson.GsonBuilder
import org.koin.android.ext.koin.androidContext
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
        single { GsonBuilder().create() }
    }

    private fun viewModelsModule() = module {
        // TODO
    }

    private fun dataAccessModule() = module {
        single<GenresWithSongsDataAccess> { GenresWithSongsDataAccessImpl(get(), get()) }
    }

    private fun useCaseModule() = module {
        factory<GetGenresWithSongsUseCase> { GetGenresWithSongsUseCaseImpl(get()) }
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
