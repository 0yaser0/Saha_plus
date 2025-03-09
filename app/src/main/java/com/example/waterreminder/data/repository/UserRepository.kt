package com.example.waterreminder.data.repository

import com.example.waterreminder.data.local.dao.UserDao
import com.example.waterreminder.data.local.model.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UserRepository(private val userDao: UserDao) {

    suspend fun insertUser(user: User): Long = withContext(Dispatchers.IO) {
        userDao.insertUser(user)
    }

    suspend fun getUserById(id: String): User? = withContext(Dispatchers.IO) {
        userDao.getUserById(id)
    }

    suspend fun getAllUsers(): List<User> = withContext(Dispatchers.IO) {
        userDao.getAllUsers()
    }

    suspend fun updateUser(user: User) = withContext(Dispatchers.IO) {
        userDao.updateUser(user)
    }

    suspend fun deleteUser(user: User) = withContext(Dispatchers.IO) {
        userDao.deleteUser(user)
    }

    suspend fun getUserByFirebaseUid(uid: String): User? {
        return userDao.getUserByFirebaseUid(uid)
    }
}
