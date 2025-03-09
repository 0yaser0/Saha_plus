package com.example.waterreminder.data.local.dao

import androidx.room.*
import com.example.waterreminder.data.local.model.User

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: User): Long

    @Query("SELECT * FROM user WHERE user_id = :id")
    suspend fun getUserById(id: String): User?

    @Query("SELECT * FROM user")
    suspend fun getAllUsers(): List<User>

    @Update
    suspend fun updateUser(user: User)

    @Delete
    suspend fun deleteUser(user: User)

    @Query("SELECT * FROM user WHERE user_id = :uid LIMIT 1")
    suspend fun getUserByFirebaseUid(uid: String): User?

}