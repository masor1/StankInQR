package com.masorone.stankinqrapp.core

interface FetchById<ID> {

    fun fetch(id: ID)
}