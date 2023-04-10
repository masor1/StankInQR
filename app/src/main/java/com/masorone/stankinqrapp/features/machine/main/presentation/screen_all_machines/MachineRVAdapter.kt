package com.masorone.stankinqrapp.features.machine.main.presentation.screen_all_machines

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.masorone.stankinqrapp.databinding.MachineItemBinding
import com.masorone.stankinqrapp.features.machine.main.presentation.MachineUi

class MachineRVAdapter(
    private val onItemClick: (machine: MachineUi) -> Unit
) : ListAdapter<MachineUi, MachineRVAdapter.MachineViewHolder>(MachineDiffUtilItem()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MachineViewHolder {
        val binding = MachineItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return MachineViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MachineViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class MachineViewHolder(
        private val binding: MachineItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(machine: MachineUi) {
            machine.show(binding)
            binding.root.setOnClickListener {
                onItemClick.invoke(machine)
            }
        }
    }
}