package com.masorone.stankinqrapp.core.main

interface SuspendFetchById<ID, R> {

    suspend fun fetch(id: ID): R
}
