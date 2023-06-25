package com.masorone.stankinqrapp.app.features.machine.fetch_real_time_data.impl

import com.masorone.stankinqrapp.features.machine.api.FetchRealTimeDataUseCase
import com.masorone.stankinqrapp.features.machine.api.model.RealTimeData
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import kotlin.random.Random

class BaseFetchRealTimeDataUseCase @Inject constructor() : FetchRealTimeDataUseCase {

    override fun fetchById(id: Int): Flow<RealTimeData> = flow {
        while (true) {
            delay(1500)
            val engineHours = Random.nextInt(1, 5000)
            emit(
                RealTimeData(
                    machineSpindleRotationSpeed = "Скорость вращения шпинделя:\n ${Random.nextInt(3000, 7000)} об/мин",
                    machineFeedRate = "Скорость подачи:\n ${String.format("%.2f", Random.nextDouble(0.2, 5.0).toFloat())} мм/мин",
                    machineNumberOfProcessedParts = "Количество обработанных деталей:\n ${Random.nextInt(1, 10)} штук",
                    machineTemperature = "Температура:\n ${String.format("%.2f", Random.nextDouble(15.0, 30.0).toFloat())} градусов Цельсия",
                    machineVibration = "Вибрация:\n ${String.format("%.2f", Random.nextDouble(0.1, 0.9).toFloat())} мм/сек",
                    errorRate = "Погрешность:\n ${String.format("%.2f", Random.nextDouble(0.01, 0.05).toFloat())} мм",
                    engineHours = "Моточасы:\n $engineHours MH",
                    residualValue = "Остаточная стоимость:\n ${String.format("%.2f",(60000 - (60000 / 10 * (engineHours / 1000))).toFloat())} у.е."
                )
            )
        }
    }
}