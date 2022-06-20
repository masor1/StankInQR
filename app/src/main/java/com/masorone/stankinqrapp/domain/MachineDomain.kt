package com.masorone.stankinqrapp.domain

import com.masorone.stankinqrapp.presentation.MachineUI

sealed class MachineDomain {

    abstract fun map(): MachineUI

    data class Success(
        private val id: String,
        private val name: String,
        private val imageUrl: String
    ) : MachineDomain() {
        override fun map() = MachineUI.Success(id, name, imageUrl)
    }

    data class Error(
        private val errorType: ErrorType
    ) : MachineDomain() {
        override fun map() = MachineUI.Error(
            when (errorType) {
                ErrorType.NOT_FOUND -> "Machine not found"
                ErrorType.SERVICE_UNAVAILABLE -> "Service unavailable"
                ErrorType.GENERIC -> "Try again later"
            }
        )
    }
}