package com.masorone.stankinqrapp.features.machine.main.presentation

interface Mapper<I, O> {

    fun map(input: I): O
}
