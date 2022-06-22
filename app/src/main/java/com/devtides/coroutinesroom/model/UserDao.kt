package com.devtides.coroutinesroom.model

import androidx.room.*

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: User): Long

    @Query(value = "DELETE FROM User WHERE id = :id")
    suspend fun deleteUser(id: Long)

    @Query(value = "SELECT * FROM User WHERE username = :username")
    suspend fun getUserByUsername(username: String): User

    @Query(value = "SELECT * FROM User WHERE username = :username AND password_hash = :password")
    suspend fun getUserByUsernameAndPassword(username: String, password: Int): User
}