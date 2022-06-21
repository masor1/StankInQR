package com.masorone.stankinqrapp.presentation.screen_main

import android.view.LayoutInflater
import android.view.ViewGroup
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
            val dataList = machine.show().split("|")
            binding.machineItemId.text = dataList[0]
            binding.machineItemName.text = dataList[1]
        }
    }
}