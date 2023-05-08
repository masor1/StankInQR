package com.masorone.stankinqrapp.core

interface SuspendFetchAll<R> {

    suspend fun fetch(): R
}
