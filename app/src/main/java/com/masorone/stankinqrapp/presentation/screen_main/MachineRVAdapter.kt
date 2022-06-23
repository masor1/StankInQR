package com.masorone.stankinqrapp.presentation.screen_main

import android.text.Layout
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.masorone.stankinqrapp.databinding.MachineItemBinding
import com.masorone.stankinqrapp.presentation.MachineUI

class MachineRVAdapter :
    ListAdapter<MachineUI, MachineRVAdapter.MachineViewHolder>(MachineDiffUtilItem()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MachineViewHolder {
        val binding = MachineItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return MachineViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MachineViewHolder, position: Int) {
        val machine = getItem(position)
        holder.bind(machine)
    }

    inner class MachineViewHolder(
        private val binding: MachineItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(machine: MachineUI) {
            if (machine is MachineUI.Success) {
                val dataList = machine.show().split("|")
                binding.machineItemId.text = dataList[0]
                binding.machineItemName.text = dataList[1]
            } else {
                binding.machineItemId.text = machine.show()
                binding.machineItemName.text = ""
            }
        }
    }
}