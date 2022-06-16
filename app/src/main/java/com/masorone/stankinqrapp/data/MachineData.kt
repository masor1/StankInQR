package com.masorone.stankinqrapp.data

import com.masorone.stankinqrapp.domain.MachineDomain

sealed class MachineData {

    abstract fun map(): MachineDomain

    data class Success(
        private val id: String,
        private val name: String,
        private val imageUrl: String
    ) : MachineData() {

        override fun map() = MachineDomain.Success(id, name, imageUrl)
    }

    data class Error(
        private val e: Exception
    ) : MachineData() {

        override fun map() = MachineDomain.Error(e)
    }
}
