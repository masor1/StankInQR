package com.masorone.stankinqrapp.features.machine.api.model

data class RealTimeData(
    val machineSpindleRotationSpeed: String,
    val machineFeedRate: String,
    val machineNumberOfProcessedParts: String,
    val machineTemperature: String,
    val machineVibration: String,
    val errorRate: String,
    val engineHours: String,
    val residualValue: String
)
