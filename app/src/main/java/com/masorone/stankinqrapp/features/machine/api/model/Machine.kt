package com.masorone.stankinqrapp.features.machine.api.model

import com.masorone.stankinqrapp.R
import com.masorone.stankinqrapp.core.Map
import com.masorone.stankinqrapp.features.machine.api.ErrorType
import com.masorone.stankinqrapp.features.machine.main.presentation.MachineUi

sealed interface Machine : Map<MachineUi> {

    data class Success(
        private val id: String,
        private val name: String,
        private val imageUrl: String,
        private val description: String
    ) : Machine {

        override fun map(): MachineUi = MachineUi.Success(id, name, imageUrl, description)
    }

    data class Error(
        private val errorType: ErrorType
    ) : Machine {

        override fun map(): MachineUi = MachineUi.Error(
            when (errorType) {
                ErrorType.ServiceUnavailable -> R.string.exception_service_unavailable
                ErrorType.NotFound -> R.string.exception_not_found
                ErrorType.Generic -> R.string.exception_generic
            }
        )
    }
}
