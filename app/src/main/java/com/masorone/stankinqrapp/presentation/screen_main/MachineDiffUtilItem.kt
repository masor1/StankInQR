package com.masorone.stankinqrapp.presentation.screen_main

import androidx.recyclerview.widget.DiffUtil
import com.masorone.stankinqrapp.presentation.MachineUI

class MachineDiffUtilItem : DiffUtil.ItemCallback<MachineUI>() {

    override fun areItemsTheSame(oldItem: MachineUI, newItem: MachineUI) =
        oldItem.areItemsTheSame(newItem)

    override fun areContentsTheSame(oldItem: MachineUI, newItem: MachineUI) =
        oldItem.areContentsTheSame(newItem)
}