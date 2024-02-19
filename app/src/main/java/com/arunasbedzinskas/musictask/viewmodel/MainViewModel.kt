package com.arunasbedzinskas.musictask.viewmodel

import androidx.lifecycle.ViewModel
import com.arunasbedzinskas.musictask.EMPTY_STRING
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class MainViewModel : ViewModel() {

    private val _topBarStateFlow = MutableStateFlow(EMPTY_STRING)
    val topBarStateFlow: StateFlow<String>
        get() = _topBarStateFlow.asStateFlow()

    fun setTopBarTitle(title: String) = _topBarStateFlow.update { title }
}
