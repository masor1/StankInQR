package com.masorone.stankinqrapp.features.machine.main.data.cloud.model

import com.google.gson.annotations.SerializedName
import com.masorone.stankinqrapp.features.machine.api.ErrorType
import com.masorone.stankinqrapp.features.machine.api.model.Machine
import retrofit2.HttpException
import com.masorone.stankinqrapp.core.main.Map

sealed interface MachineCloud : Map<Machine> {

    data class Success(
        @SerializedName("id") private val id: String,
        @SerializedName("name") private val name: String,
        @SerializedName("imageUrl") private val imageUrl: String,
        @SerializedName("description") private val description: String
    ) : MachineCloud {

        override fun map(): Machine = Machine.Success(id, name, imageUrl, description)
    }

    data class Error(
        private val exception: Exception
    ) : MachineCloud {

        override fun map(): Machine = Machine.Error(
            when (exception) {
                is IndexOutOfBoundsException -> ErrorType.NotFound
                is HttpException -> ErrorType.ServiceUnavailable
                else -> ErrorType.Generic
            }
        )
    }
}