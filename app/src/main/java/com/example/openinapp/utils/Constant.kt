package com.example.openinapp.utils

import kotlinx.coroutines.flow.flow
import retrofit2.Response

fun <T> toResultFlow(call: suspend () -> Response<T>): kotlinx.coroutines.flow.Flow<ApiState<T>> =
    flow {
        emit(ApiState.Loading)
        val response = call()
        try {
            if (response.isSuccessful) {
                response.body()?.let { data ->
                    emit(ApiState.Success(data))
                }
            } else {
                response.errorBody()?.let { error ->
                    error.close()
                    emit(ApiState.Failure(error.toString()))
                }
            }
        } catch (e: Exception) {
            emit(ApiState.Failure(e.message.toString()))
        }
    }