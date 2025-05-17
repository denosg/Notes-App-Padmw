package com.costelas.notes.ui.screens.home.inspiration

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.costelas.notes.common.models.GoTQuote
import com.costelas.notes.common.models.Result
import com.costelas.notes.domain.IGoTRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GoTViewModel @Inject constructor(
    private val repository: IGoTRepository
) : ViewModel() {

    private val _quote = mutableStateOf<Result<GoTQuote>>(Result.Loading())
    val quote: State<Result<GoTQuote>> = _quote

    init {
        fetchQuote()
    }

    fun fetchQuote() {
        viewModelScope.launch {
            _quote.value = Result.Loading()
            try {
                val response = repository.fetchRandomQuote()
                _quote.value = Result.Success(response)
            } catch (e: Exception) {
                _quote.value = Result.Error(e.message)
            }
        }
    }
}
