package com.masorone.stankinqrapp.core.android

import androidx.viewbinding.ViewBinding

interface ShowBinding<BINDING: ViewBinding> {

    fun show(binding: BINDING)
}
