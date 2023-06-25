package com.masorone.stankinqrapp.core.main

interface SuspendFetchAll<R> {

    suspend fun fetch(): R
}
