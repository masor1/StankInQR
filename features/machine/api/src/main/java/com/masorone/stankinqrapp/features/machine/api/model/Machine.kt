package com.masorone.stankinqrapp.features.machine.api.model

import com.masorone.stankinqrapp.features.machine.api.ErrorType

sealed interface Machine {

    data class Success(
        val id: String,
        val name: String,
        val imageUrl: String,
        val description: String
    ) : Machine

    data class Error(
        val errorType: ErrorType
    ) : Machine
}
