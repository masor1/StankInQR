package com.masorone.stankinqrapp.features.machine.api.model

import com.masorone.stankinqrapp.core.Map
import com.masorone.stankinqrapp.features.machine.main.presentation.MachinesUi

sealed interface Machines : Map<MachinesUi> {

    data class Success(
        private val machines: List<Machine>
    ) : Machines {

        override fun map(): MachinesUi = MachinesUi.Success(
            machines.map { machine ->
                machine.map()
            }
        )
    }

    data class Error(
        private val machine: Machine
    ) : Machines {

        override fun map(): MachinesUi = MachinesUi.Error(machine.map())
    }
}