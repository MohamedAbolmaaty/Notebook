package com.emapps.notebook.domain.repository

import com.emapps.notebook.domain.model.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun insertUser(user: User)
    fun getAllUsers(): Flow<List<User>>
}