package com.arunasbedzinskas.musictask.navigation

import android.os.Bundle
import androidx.compose.runtime.MutableState
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.createGraph
import androidx.navigation.navArgument
import com.arunasbedzinskas.musictask.EMPTY_STRING
import com.arunasbedzinskas.musictask.R
import com.arunasbedzinskas.musictask.models.enums.StorageType
import com.arunasbedzinskas.musictask.ui.screen.GenreScreen
import com.arunasbedzinskas.musictask.ui.screen.HomeScreen
import com.arunasbedzinskas.musictask.ui.screen.StorageScreen

object AppNavigation {

    object Route {

        const val Home = "home"
        const val Genre = "genre"
        const val Storage = "storage"
    }

    object Arg {

        const val TopBarTitle = "topBarTitle"
        const val GenreId = "genreId"
        const val StorageTypeId = "storageTypeId"
    }

    fun getGraph(
        navController: NavController,
        topBarTitleState: MutableState<String>
    ) = navController.createGraph(Route.Home) {

        composable(Route.Home) {
            topBarTitleState.value = stringResource(R.string.app_name)

            HomeScreen(
                onSeeAllClick = { genre ->
                    navController.navigate(
                        "${Route.Genre}?${Arg.GenreId}=${genre.id}&${Arg.TopBarTitle}=${genre.name}"
                    )
                },
                onStorageTypeClick = { storageType ->
                    navController.navigate(
                        "${Route.Storage}?${Arg.StorageTypeId}=${storageType.ordinal}&${Arg.TopBarTitle}=${storageType.name}"
                    )
                }
            )
        }

        composable(
            route = "${Route.Genre}?${Arg.GenreId}={${Arg.GenreId}}&${Arg.TopBarTitle}={${Arg.TopBarTitle}}",
            arguments = listOf(
                navArgument(Arg.GenreId) { type = NavType.IntType },
                navArgument(Arg.TopBarTitle) { type = NavType.StringType }
            ),
            content = {
                val args = it.arguments ?: Bundle()
                topBarTitleState.value = args.getString(Arg.TopBarTitle) ?: EMPTY_STRING

                GenreScreen(args.getInt(Arg.GenreId))
            }
        )

        composable(
            route = "${Route.Storage}?${Arg.StorageTypeId}={${Arg.StorageTypeId}}&${Arg.TopBarTitle}={${Arg.TopBarTitle}}",
            arguments = listOf(
                navArgument(Arg.StorageTypeId) { type = NavType.IntType },
                navArgument(Arg.TopBarTitle) { type = NavType.StringType }
            )
        ) {
            val args = it.arguments ?: Bundle()
            val storageType = StorageType.entries[args.getInt(Arg.StorageTypeId, 0)]
            topBarTitleState.value = args.getString(Arg.TopBarTitle) ?: EMPTY_STRING

            StorageScreen(storageType)
        }
    }
}