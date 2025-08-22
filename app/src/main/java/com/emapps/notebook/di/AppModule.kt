package com.emapps.notebook.di

import com.emapps.notebook.data.local.dao.UserDao
import com.emapps.notebook.data.repository.UserRepositoryImpl
import com.emapps.notebook.domain.repository.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideUserRepository(userDao: UserDao): UserRepository {
        return UserRepositoryImpl(userDao)
    }
}