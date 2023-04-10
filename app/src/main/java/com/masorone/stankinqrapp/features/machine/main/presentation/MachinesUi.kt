package com.masorone.stankinqrapp.features.machine.main.presentation

import com.masorone.stankinqrapp.features.machine.main.presentation.screen_all_machines.MachineRVAdapter

sealed interface MachinesUi {

    fun invokeSubmitList(adapter: MachineRVAdapter)

    data class Success(private val machines: List<MachineUi>) : MachinesUi {

        override fun invokeSubmitList(adapter: MachineRVAdapter) = adapter.submitList(machines)
    }

    data class Error(private val machine: MachineUi) : MachinesUi {

        override fun invokeSubmitList(adapter: MachineRVAdapter) =
            adapter.submitList(listOf(machine))
    }
}