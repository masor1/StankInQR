package com.masorone.stankinqrapp.core

import com.masorone.stankinqrapp.features.machine.main.presentation.MachineUi

interface Navigate<MACHINE: MachineUi> {

    fun navigate(machine: MACHINE)
}