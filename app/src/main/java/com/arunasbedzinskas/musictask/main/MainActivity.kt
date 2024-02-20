package com.arunasbedzinskas.musictask.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.createGraph
import androidx.navigation.navArgument
import com.arunasbedzinskas.musictask.ARGS_GENRE_ID
import com.arunasbedzinskas.musictask.ARGS_GENRE_NAME
import com.arunasbedzinskas.musictask.EMPTY_STRING
import com.arunasbedzinskas.musictask.ROUTE_GENRE
import com.arunasbedzinskas.musictask.ROUTE_HOME
import com.arunasbedzinskas.musictask.ui.screen.GenreScreen
import com.arunasbedzinskas.musictask.ui.screen.HomeScreen
import com.arunasbedzinskas.musictask.ui.theme.AppTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { AppTheme { Main() } }
    }
}

@Composable
private fun Main() {
    var topBarTitle by rememberSaveable { mutableStateOf(EMPTY_STRING) }
    val navController = rememberNavController()

    val navGraph = navController.createGraph(ROUTE_HOME) {
        composable(ROUTE_HOME) {
            topBarTitle = EMPTY_STRING
            HomeScreen(
                onSeeAllClick = { genre ->
                    navController.navigate("genre?genreId=${genre.id}&genreName=${genre.name}")
                },
                onStorageTypeClick = {}
            )
        }
        composable(
            route = ROUTE_GENRE,
            arguments = listOf(navArgument(ARGS_GENRE_ID) { type = NavType.IntType }),
            content = {
                val args = it.arguments ?: Bundle()
                topBarTitle = args.getString(ARGS_GENRE_NAME) ?: EMPTY_STRING
                GenreScreen(args.getInt(ARGS_GENRE_ID))
            }
        )
    }

    Scaffold(
        topBar = { TopBar(topBarTitle) },
        content = { paddingValues ->
            NavHost(
                navController = navController,
                graph = navGraph,
                modifier = Modifier.padding(paddingValues)
            )
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TopBar(title: String) {
    AnimatedVisibility(
        visible = title.isNotBlank(),
        enter = slideInVertically { -it },
        exit = slideOutVertically{ -it }
    ) {
        TopAppBar(
            title = {
                Text(
                    text = title,
                    style = MaterialTheme.typography.headlineMedium
                )
            }
        )
    }
}

@Preview
@Composable
fun MainPreview() {
    AppTheme { Main() }
}
