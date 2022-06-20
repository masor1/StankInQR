package com.masorone.stankinqrapp.presentation

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

sealed class MachineUI {

    abstract fun show(): String

    @Parcelize
    data class Success(
        private val id: String,
        private val name: String,
        private val imageUrl: String
    ) : MachineUI(), Parcelable {

        override fun show() = "$id|$name|$imageUrl"
    }

    @Parcelize
    data class Error(
        private val message: String
    ) : MachineUI(), Parcelable {

        override fun show() = message
    }

    companion object {

        const val SUCCESS = "machineSuccess"
        const val ERROR = "machineError"
    }
}