package com.masorone.stankinqrapp.domain

interface MachineRepository {

    suspend fun fetchByID(id: String): MachineDomain
    suspend fun fetchList(): List<MachineDomain>
}