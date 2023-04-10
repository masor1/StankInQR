package com.masorone.stankinqrapp.core

import androidx.viewbinding.ViewBinding

interface ShowBinding<BINDING: ViewBinding> {

    fun show(binding: BINDING)
}