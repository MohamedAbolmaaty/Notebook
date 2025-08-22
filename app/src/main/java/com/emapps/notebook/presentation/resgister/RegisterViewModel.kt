package com.emapps.notebook.presentation.resgister

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.emapps.notebook.domain.model.User
import com.emapps.notebook.domain.usecase.InsertUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed interface RegisterIntent {
    data class UpdateName(val name: String) : RegisterIntent
    data class UpdateAge(val age: Int) : RegisterIntent
    data class UpdateJobTitle(val jobTitle: String) : RegisterIntent
    data class UpdateGender(val gender: String) : RegisterIntent
    object Register : RegisterIntent
}

data class RegisterState(
    val name: String = "",
    val age: Int = 0,
    val jobTitle: String = "",
    val gender: String = "",
    val isSaving: Boolean = false,
    val saveSuccess: Boolean = false,
    val error: String? = null
)

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val insertUserUseCase: InsertUserUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(RegisterState())
    val state: StateFlow<RegisterState> = _state.asStateFlow()

    fun onIntent(intent: RegisterIntent) {
        when (intent) {
            is RegisterIntent.UpdateName -> _state.update { it.copy(name = intent.name) }
            is RegisterIntent.UpdateAge -> _state.update { it.copy(age = intent.age) }
            is RegisterIntent.UpdateJobTitle -> _state.update { it.copy(jobTitle = intent.jobTitle) }
            is RegisterIntent.UpdateGender -> _state.update { it.copy(gender = intent.gender) }
            is RegisterIntent.Register -> register()
        }
    }

    private fun register() {
        viewModelScope.launch {
            _state.update { it.copy(isSaving = true, saveSuccess = false) }
            try {
                val user = User(
                    name = _state.value.name,
                    age = _state.value.age,
                    jobTitle = _state.value.jobTitle,
                    gender = _state.value.gender
                )
                insertUserUseCase(user)
                _state.update { it.copy(isSaving = false, saveSuccess = true, error = null,
                    name = "", age = 0, jobTitle = "", gender = "") }
            } catch (e: Exception) {
                _state.update {
                    it.copy(isSaving = false, saveSuccess = false, error = e.message)
                }
            }
        }
    }
}