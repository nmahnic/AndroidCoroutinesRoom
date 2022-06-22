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

class MainViewModel : ViewModel() {

    val userDeleted = MutableLiveData<Boolean>()
    val signout = MutableLiveData<Boolean>()
    private var userDao: UserDao? = null

    fun loadDatabase(userDao: UserDao){
        this.userDao = userDao
    }

    fun onSignout() {
        LoginState.logout()
        signout.value = true
    }

    fun onDeleteUser() {
        viewModelScope.launch(Dispatchers.IO) {

            val user = userDao?.deleteUser(LoginState.getUserId()!!)
            withContext(Dispatchers.Main){
                userDeleted.value = true
            }
        }
    }

}