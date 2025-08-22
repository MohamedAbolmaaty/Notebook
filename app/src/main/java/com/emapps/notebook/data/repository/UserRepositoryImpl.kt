package com.emapps.notebook.data.repository

import com.emapps.notebook.data.local.dao.UserDao
import com.emapps.notebook.data.local.entity.UserEntity
import com.emapps.notebook.domain.model.User
import com.emapps.notebook.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userDao: UserDao
) : UserRepository {

    override suspend fun insertUser(user: User) {
        userDao.insertUser(UserEntity(
            name = user.name,
            age = user.age,
            jobTitle = user.jobTitle,
            gender = user.gender
        ))
    }

    override fun getAllUsers(): Flow<List<User>> {
        return userDao.getAllUsers().map { users ->
            users.map { it.toUser() }
        }
    }
}