package com.example.openinapp.data.remote
import com.example.openinapp.data.model.DashBoardData
import retrofit2.Response
import retrofit2.http.GET

interface OpeninAPI {

    @GET("dashboardNew")
    suspend fun getDashBoardDetails() : Response<DashBoardData>
}