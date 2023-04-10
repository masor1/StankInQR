package com.masorone.stankinqrapp.features.machine.main.presentation.screen_all_machines

import androidx.recyclerview.widget.DiffUtil
import com.masorone.stankinqrapp.features.machine.main.presentation.MachineUi

class MachineDiffUtilItem : DiffUtil.ItemCallback<MachineUi>() {

    override fun areItemsTheSame(oldItem: MachineUi, newItem: MachineUi) =
        oldItem.areItemsTheSame(newItem)

    override fun areContentsTheSame(oldItem: MachineUi, newItem: MachineUi) =
        oldItem.areContentsTheSame(newItem)
}