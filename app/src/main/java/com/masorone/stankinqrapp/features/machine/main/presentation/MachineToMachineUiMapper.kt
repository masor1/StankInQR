package com.masorone.stankinqrapp.features.machine.main.presentation

import com.masorone.stankinqrapp.R
import com.masorone.stankinqrapp.core.android.ProvideResources
import com.masorone.stankinqrapp.features.machine.api.ErrorType
import com.masorone.stankinqrapp.features.machine.api.model.Machine
import javax.inject.Inject

class MachineToMachineUiMapper @Inject constructor(
    private val provideResources: ProvideResources<String>
) : Mapper<Machine, MachineUi> {

    override fun map(input: Machine): MachineUi = when (input) {
        is Machine.Success -> MachineUi.Success(
            input.id,
            input.name,
            input.imageUrl,
            input.description
        )
        is Machine.Error -> MachineUi.Error(
            when (input.errorType) {
                ErrorType.ServiceUnavailable -> provideResources.provide(R.string.exception_service_unavailable)
                ErrorType.NotFound -> provideResources.provide(R.string.exception_not_found)
                ErrorType.Generic -> provideResources.provide(R.string.exception_generic)
            }
        )
    }
}
