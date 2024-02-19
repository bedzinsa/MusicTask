package com.arunasbedzinskas.musictask.dispatchers

import kotlinx.coroutines.CoroutineDispatcher

class AppDispatchers(
    val default: CoroutineDispatcher,
    val main: CoroutineDispatcher,
    val io: CoroutineDispatcher
)