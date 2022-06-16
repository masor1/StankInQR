package com.masorone.stankinqrapp.domain

class FetchByIdUseCase(
    private val repository: MachineRepository
) {

    suspend operator fun invoke(id: String) = repository.fetchByID(id)
}