package com.sc941737.starwarstask.ui.selection

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class SelectionViewModel : ViewModel() {
    private val _isSearching = MutableStateFlow(false)
    val isSearching: StateFlow<Boolean> = _isSearching
    fun onClickSearch() = _isSearching.update { true }
    fun onClickCancelSearch() = _isSearching.update { false }
}
