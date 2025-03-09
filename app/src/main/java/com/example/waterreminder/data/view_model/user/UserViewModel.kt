package com.example.waterreminder.data.view_model.user

import androidx.lifecycle.ViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import com.example.waterreminder.data.local.model.User
import com.example.waterreminder.data.repository.UserRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class UserViewModel(private val repository: UserRepository) : ViewModel() {

    private val _user = MutableLiveData<User?>()
    val user: LiveData<User?> get() = _user

    fun insertUser(user: User) {
        viewModelScope.launch {
            repository.insertUser(user)
        }
    }

    fun getUserById(id: String) {
        viewModelScope.launch {
            _user.value = repository.getUserById(id)
        }
    }

    fun getCurrentUser(): String? {
        val auth: FirebaseAuth = Firebase.auth
        val firebaseUser = auth.currentUser
        val uid = firebaseUser?.uid

        viewModelScope.launch {
            uid?.let {
                _user.value = repository.getUserByFirebaseUid(it)
            }
        }

        return uid
    }

}