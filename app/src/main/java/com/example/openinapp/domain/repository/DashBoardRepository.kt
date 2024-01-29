package com.example.openinapp.domain.repository

import com.example.openinapp.data.model.DashBoardData
import com.example.openinapp.utils.ApiState
import kotlinx.coroutines.flow.Flow

interface DashBoardRepository {
     fun doNetworkCall() : Flow<ApiState<DashBoardData>>
}