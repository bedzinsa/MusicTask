package com.arunasbedzinskas.musictask.base

import com.arunasbedzinskas.musictask.dispatchers.AppDispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.core.module.Module
import org.koin.dsl.module
import org.koin.test.KoinTest

@OptIn(ExperimentalCoroutinesApi::class)
abstract class BaseUnitTest : KoinTest {

    val testScheduler = StandardTestDispatcher()

    @ExperimentalCoroutinesApi
    @get:Rule
    val mainRule = MainCoroutineRule(testScheduler)


    @Before
    fun prepare() {
        startKoin {
            modules(module {
                single {
                    AppDispatchers(
                        testScheduler,
                        testScheduler,
                        testScheduler
                    )
                }
                initDependencies()
            })
        }
    }

    @After
    fun cleanup() {
        stopKoin()
    }

    open fun Module.initDependencies() {}
}
