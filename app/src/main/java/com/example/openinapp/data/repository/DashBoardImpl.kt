package com.example.openinapp.data.repository

import com.example.openinapp.data.model.DashBoardData
import com.example.openinapp.data.remote.OpeninAPI
import com.example.openinapp.domain.repository.DashBoardRepository
import com.example.openinapp.utils.ApiState
import com.example.openinapp.utils.toResultFlow
import kotlinx.coroutines.flow.Flow

class DashBoardImpl(private val api: OpeninAPI) : DashBoardRepository {
    override fun doNetworkCall(): Flow<ApiState<DashBoardData>> {
        return toResultFlow {
            api.getDashBoardDetails()
        }
    }
}