package com.masorone.stankinqrapp.data

import com.masorone.stankinqrapp.domain.ErrorType
import com.masorone.stankinqrapp.domain.MachineDomain
import retrofit2.HttpException

sealed class MachineData {

    abstract fun map(): MachineDomain

    data class Success(
        private val id: String,
        private val name: String,
        private val imageUrl: String
    ) : MachineData() {

        override fun map() = MachineDomain.Success(id, name, imageUrl)
    }

    data class Error(
        private val e: Exception
    ) : MachineData() {

        override fun map(): MachineDomain {
            val errorType = when (e) {
                is IndexOutOfBoundsException -> ErrorType.NOT_FOUND
                is HttpException -> ErrorType.SERVICE_UNAVAILABLE
                else -> ErrorType.GENERIC
            }
            return MachineDomain.Error(errorType)
        }
    }
}
