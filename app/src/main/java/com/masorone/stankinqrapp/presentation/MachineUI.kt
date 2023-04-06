package com.masorone.stankinqrapp.presentation

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

sealed class MachineUI {

    abstract fun show(): String

    abstract fun areItemsTheSame(newItem: MachineUI): Boolean

    abstract fun areContentsTheSame(newItem: MachineUI): Boolean

    @Parcelize
    data class Success(
        private val id: String,
        private val name: String,
        private val imageUrl: String,
        private val description: String,
    ) : MachineUI(), Parcelable {

        override fun show() = "$id|$name|$imageUrl|$description"

        override fun areItemsTheSame(newItem: MachineUI) = id == (newItem as Success).id

        override fun areContentsTheSame(newItem: MachineUI) = this == newItem as Success
    }

    @Parcelize
    data class Error(
        private val message: String
    ) : MachineUI(), Parcelable {

        override fun show() = message

        override fun areItemsTheSame(newItem: MachineUI) = true

        override fun areContentsTheSame(newItem: MachineUI) = true
    }

    companion object {

        const val SUCCESS = "machineSuccess"
        const val ERROR = "machineError"
    }
}