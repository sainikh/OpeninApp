package com.example.openinapp.utils

sealed class ApiState<out T> {

    data class Success<out R>(val data: R) : ApiState<R>()
    data class Failure(val msg: String) : ApiState<Nothing>()
    object Loading : ApiState<Nothing>()

}