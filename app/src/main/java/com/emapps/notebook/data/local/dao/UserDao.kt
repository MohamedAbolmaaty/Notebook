package com.emapps.notebook.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.emapps.notebook.data.local.database.DatabaseConstants.USER_TABLE_NAME
import com.emapps.notebook.data.local.entity.UserEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: UserEntity)

    @Query("SELECT * FROM $USER_TABLE_NAME")
    fun getAllUsers(): Flow<List<UserEntity>>
}