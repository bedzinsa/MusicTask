package com.arunasbedzinskas.musictask.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.createGraph
import com.arunasbedzinskas.musictask.R
import com.arunasbedzinskas.musictask.SCREEN_CATEGORY
import com.arunasbedzinskas.musictask.SCREEN_HOME
import com.arunasbedzinskas.musictask.SCREEN_STORAGE
import com.arunasbedzinskas.musictask.ui.screen.CategoryScreen
import com.arunasbedzinskas.musictask.ui.screen.HomeScreen
import com.arunasbedzinskas.musictask.ui.screen.StorageScreen
import com.arunasbedzinskas.musictask.ui.theme.AppTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { AppTheme { Main() } }
    }
}

@Composable
private fun Main() {
    val navController = rememberNavController()
    val navGraph = navController.createGraph(SCREEN_HOME) {
        composable(SCREEN_HOME) { HomeScreen() }
        composable(SCREEN_CATEGORY) { CategoryScreen() }
        composable(SCREEN_STORAGE) { StorageScreen() }
    }

    Scaffold(
        topBar = { TopBar() },
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
private fun TopBar(title: String = stringResource(R.string.app_name)) {
    TopAppBar(
        title = {
            Text(
                text = title,
                style = MaterialTheme.typography.labelLarge
            )
        }
    )
}

@Preview
@Composable
fun MainPreview() {
    AppTheme { Main() }
}
