package com.emapps.notebook.domain.usecase

import com.emapps.notebook.domain.model.User
import com.emapps.notebook.domain.repository.UserRepository
import javax.inject.Inject

class InsertUserUseCase @Inject constructor(
    private val repository: UserRepository
) {
    suspend operator fun invoke(user: User) {
        repository.insertUser(user)
    }
}