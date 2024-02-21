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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.arunasbedzinskas.musictask.R
import com.arunasbedzinskas.musictask.navigation.AppNavigation
import com.arunasbedzinskas.musictask.ui.theme.AppTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { AppTheme { Main() } }
    }

    @Composable
    private fun Main() {
        val appName = stringResource(R.string.app_name)
        val topBarTitleState = rememberSaveable { mutableStateOf(appName) }
        val navController = rememberNavController()
        val navGraph = AppNavigation.getGraph(navController, topBarTitleState)

        Scaffold(topBar = { TopBar(topBarTitleState.value) }, content = { paddingValues ->
            NavHost(
                navController = navController,
                graph = navGraph,
                modifier = Modifier.padding(paddingValues)
            )
        })
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    private fun TopBar(title: String) {
        TopAppBar(
            title = {
                Text(
                    text = title, style = MaterialTheme.typography.headlineMedium
                )
            },
        )
    }
}
