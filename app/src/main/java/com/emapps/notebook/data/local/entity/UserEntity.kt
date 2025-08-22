package com.emapps.notebook.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.emapps.notebook.data.local.database.DatabaseConstants.USER_TABLE_NAME
import com.emapps.notebook.domain.model.User

@Entity(tableName = USER_TABLE_NAME)
data class UserEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val age: Int,
    val jobTitle: String,
    val gender: String
) {
    fun toUser() = User(
        name = this@UserEntity.name,
        age = this@UserEntity.age,
        jobTitle = this@UserEntity.jobTitle,
        gender = this@UserEntity.gender
    )
}
