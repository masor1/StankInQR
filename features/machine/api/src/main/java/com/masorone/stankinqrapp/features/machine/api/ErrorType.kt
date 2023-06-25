package com.masorone.stankinqrapp.features.machine.api

sealed interface ErrorType {

    object NotFound: ErrorType

    object ServiceUnavailable: ErrorType

    object Generic: ErrorType
}