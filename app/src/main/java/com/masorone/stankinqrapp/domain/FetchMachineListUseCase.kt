package com.masorone.stankinqrapp.domain

import javax.inject.Inject

class FetchMachineListUseCase @Inject constructor(
    private val repository: MachineRepository
) {

    suspend operator fun invoke(): List<MachineDomain> = repository.fetchList()
}