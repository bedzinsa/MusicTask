package com.arunasbedzinskas.musictask.ext

import androidx.activity.ComponentActivity
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.CreationExtras
import org.koin.androidx.compose.defaultExtras
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.currentKoinScope
import org.koin.core.parameter.ParametersDefinition
import org.koin.core.qualifier.Qualifier
import org.koin.core.scope.Scope

/**
 * Workaround to access shared activity ViewModel from a fragment.
 *
 * This will be fixed with Koin v3.6.0
 */
@Composable
inline fun <reified T : ViewModel> koinActivityViewModel(
    qualifier: Qualifier? = null,
    key: String? = null,
    extras: CreationExtras = defaultExtras(LocalContext.current as ComponentActivity),
    scope: Scope = currentKoinScope(),
    noinline parameters: ParametersDefinition? = null,
) = koinViewModel<T>(
    qualifier = qualifier,
    viewModelStoreOwner = LocalContext.current as ComponentActivity,
    key = key,
    extras = extras,
    scope = scope,
    parameters = parameters,
)
