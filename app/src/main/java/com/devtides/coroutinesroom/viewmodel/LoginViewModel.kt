package com.devtides.coroutinesroom.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devtides.coroutinesroom.model.LoginState
import com.devtides.coroutinesroom.model.User
import com.devtides.coroutinesroom.model.UserDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginViewModel : ViewModel() {

    val loginComplete = MutableLiveData<Boolean>()
    val error = MutableLiveData<String>()
    private var userDao: UserDao? = null

    fun loadDatabase(userDao: UserDao){
        this.userDao = userDao
    }

    fun login(username: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val user = userDao?.getUserByUsernameAndPassword(username, password.hashCode())

            withContext(Dispatchers.Main){
                if(user != null){
                    LoginState.login(user)
                    loginComplete.value = true
                } else {
                    error.value = "User not exist"
                }
            }
        }
    }

}