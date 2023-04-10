package com.masorone.stankinqrapp.core

interface SuspendFetchById<ID, R> {

    suspend fun fetch(id: ID): R
}