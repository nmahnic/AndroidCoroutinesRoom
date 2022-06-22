package com.devtides.coroutinesroom.model

object LoginState {

    private var isLoggedIn = false
    private var user: User? = null

    fun logout() {
        isLoggedIn = false
        user = null
    }

    fun login(user: User){
        isLoggedIn = true
        this.user = user
    }

    fun isLoggedIn() = isLoggedIn

    fun getUserName() = user?.username

    fun getUserId() = user?.id
}