package com.devtides.coroutinesroom.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devtides.coroutinesroom.AppDatabase
import com.devtides.coroutinesroom.model.LoginState
import com.devtides.coroutinesroom.model.User
import com.devtides.coroutinesroom.model.UserDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SignupViewModel : ViewModel() {

    val signupComplete = MutableLiveData<Boolean>()
    val error = MutableLiveData<String>()
    private var userDao: UserDao? = null

    fun loadDatabase(userDao: UserDao){
        this.userDao = userDao
    }

    fun signup(username: String, password: String, info: String) {

        viewModelScope.launch(Dispatchers.IO) {
            val user = userDao?.getUserByUsername(username)
            if(user != null){
                withContext(Dispatchers.Main){
                    error.value = "User already exists"
                }
            } else {
                val newUser = User(username, password.hashCode(), info)
                val userId = userDao?.insertUser(newUser)
                userId?.let { newUser.id = it }

                withContext(Dispatchers.Main) {
                    if(userId != null) {
                        LoginState.login(newUser)
                        signupComplete.value = true
                    } else {
                        error.value = "User cannot be inserted"
                    }
                }
            }
        }
    }

}