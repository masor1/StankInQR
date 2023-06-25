package com.masorone.stankinqrapp.core.android

import android.content.Context
import androidx.annotation.StringRes
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

interface ProvideResources<R> {

    fun provide(id: Int, value: String = ""): R

    class ProvideString @Inject constructor(
        @ApplicationContext private val context: Context
    ) : ProvideResources<String> {

        override fun provide(@StringRes id: Int, value: String): String = if (value.isEmpty()) {
            context.resources.getString(id)
        } else {
            context.resources.getString(id, value)
        }
    }
}
