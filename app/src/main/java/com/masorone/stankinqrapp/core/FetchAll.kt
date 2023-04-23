package com.masorone.stankinqrapp.core

interface FetchAll<R> {

    fun fetch(firstInstance: Boolean): R
}
