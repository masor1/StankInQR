package com.masorone.stankinqrapp.domain

sealed class MachineDomain {

    data class Success(
        private val id: String,
        private val name: String,
        private val imageUrl: String
    ) : MachineDomain()

    data class Error(
        private val errorType: ErrorType
    ) : MachineDomain()
}