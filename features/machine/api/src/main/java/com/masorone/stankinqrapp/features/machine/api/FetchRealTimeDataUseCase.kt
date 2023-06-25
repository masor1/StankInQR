package com.masorone.stankinqrapp.features.machine.api

import com.masorone.stankinqrapp.features.machine.api.model.RealTimeData
import kotlinx.coroutines.flow.Flow

interface FetchRealTimeDataUseCase {

    fun fetchById(id: Int): Flow<RealTimeData>
}