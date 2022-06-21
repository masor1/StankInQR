package com.masorone.stankinqrapp.domain

import javax.inject.Inject

class FetchMachineByIdUseCase @Inject constructor(
    private val repository: MachineRepository
) {

    suspend operator fun invoke(id: String) = repository.fetchByID(id)
}