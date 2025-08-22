package com.emapps.notebook.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.emapps.notebook.domain.model.User
import com.emapps.notebook.domain.usecase.GetAllUsersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import javax.inject.Inject


sealed interface HomeIntent {
    object LoadUsers : HomeIntent
}

data class DisplayState(
    val users: List<User> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getAllUsersUseCase: GetAllUsersUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(DisplayState())
    val state: StateFlow<DisplayState> = _state.asStateFlow()

    init {
        loadUsers()
    }

    fun onIntent(intent: HomeIntent) {
        when (intent) {
            HomeIntent.LoadUsers -> loadUsers()
        }
    }

    private fun loadUsers() {
        getAllUsersUseCase()
            .onStart { _state.update { it.copy(isLoading = true) } }
            .onEach { users -> _state.update { it.copy(users = users, isLoading = false, error = null) } }
            .catch { e -> _state.update { it.copy(isLoading = false, error = e.message) } }
            .launchIn(viewModelScope)
    }
}