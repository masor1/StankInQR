package com.masorone.stankinqrapp.core.android

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer

interface Communication<T> {

    fun observe(owner: LifecycleOwner, observer: Observer<T>)

    fun show(data: T)

    abstract class Base<T : Any>(
        private val liveData: MutableLiveData<T> = MutableLiveData()
    ) : Communication<T> {

        override fun observe(owner: LifecycleOwner, observer: Observer<T>) {
            liveData.observe(owner, observer)
        }

        override fun show(data: T) {
            liveData.postValue(data)
        }
    }

    abstract class Fake<T : Any> : Communication<T> {

        open lateinit var state: T

        override fun observe(owner: LifecycleOwner, observer: Observer<T>) = Unit

        override fun show(data: T) {
            state = data
        }
    }
}
