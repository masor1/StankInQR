package com.masorone.stankinqrapp.features.machine.main.data.cloud.model

import com.masorone.stankinqrapp.core.Map
import com.masorone.stankinqrapp.features.machine.api.model.Machines

sealed interface MachinesCloud : Map<Machines> {

    data class Success(
        private val machines: List<MachineCloud>
    ) : MachinesCloud {

        override fun map(): Machines = Machines.Success(
            machines.map { machineCloud ->
                machineCloud.map()
            }
        )
    }

    data class Error(
        private val machine: MachineCloud
    ) : MachinesCloud {

        override fun map(): Machines = Machines.Error(machine.map())
    }
}