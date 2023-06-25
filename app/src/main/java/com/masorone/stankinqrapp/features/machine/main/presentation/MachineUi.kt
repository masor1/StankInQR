package com.masorone.stankinqrapp.features.machine.main.presentation

import android.os.Parcelable
import android.view.View
import androidx.annotation.StringRes
import androidx.viewbinding.ViewBinding
import com.bumptech.glide.Glide
import com.masorone.stankinqrapp.core.android.ShowBinding
import com.masorone.stankinqrapp.databinding.FragmentMachineDescriptionBinding
import com.masorone.stankinqrapp.databinding.MachineItemBinding
import kotlinx.parcelize.Parcelize

sealed interface MachineUi : ShowBinding<ViewBinding> {

    fun areItemsTheSame(newItem: MachineUi): Boolean

    fun areContentsTheSame(newItem: MachineUi): Boolean

    sealed class Abstract : MachineUi {

        override fun areItemsTheSame(newItem: MachineUi): Boolean = true

        override fun areContentsTheSame(newItem: MachineUi): Boolean = true
    }

    @Parcelize
    data class Success(
        private val id: String,
        private val name: String,
        private val imageUrl: String,
        private val description: String
    ) : Abstract(), Parcelable {

        override fun show(binding: ViewBinding) {
            when (binding) {
                is MachineItemBinding -> {
                    binding.machineItemId.text = id
                    binding.machineItemName.text = name
                }
                is FragmentMachineDescriptionBinding -> {
                    binding.machineIdValue.text = id
                    binding.machineNameValue.text = name
                    Glide.with(binding.root).load(imageUrl).into(binding.machineIcon)
                    binding.machineDescription.text = description
                }
            }
        }

        override fun areItemsTheSame(newItem: MachineUi) = id == (newItem as Success).id

        override fun areContentsTheSame(newItem: MachineUi) = this == (newItem as Success)
    }

    @Parcelize
    data class Error(private val messageResId: String) : Abstract(), Parcelable {

        override fun show(binding: ViewBinding) {
            when (binding) {
                is MachineItemBinding -> {
                    binding.machineItemId.text = messageResId
                    binding.machineItemName.text = ""
                }
                is FragmentMachineDescriptionBinding -> {
                    binding.machineDescriptionLayout.visibility = View.GONE
                    binding.errorTextLayout.apply {
                        visibility = View.VISIBLE
                        text = messageResId
                    }
                }
            }
        }
    }

    companion object {

        const val SUCCESS = "com.masorone.stankinqrapp.features.machine.main.presentation.Success"
        const val ERROR = "com.masorone.stankinqrapp.features.machine.main.presentation.Error"
    }
}