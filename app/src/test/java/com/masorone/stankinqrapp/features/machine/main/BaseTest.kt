package com.masorone.stankinqrapp.features.machine.main

import com.masorone.stankinqrapp.core.ProvideResources
import com.masorone.stankinqrapp.core.DispatchersList
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import com.masorone.stankinqrapp.R

abstract class BaseTest {

    @OptIn(ExperimentalCoroutinesApi::class)
    protected class FakeDispatchersList : DispatchersList {

        override fun io(): CoroutineDispatcher = UnconfinedTestDispatcher()

        override fun main(): CoroutineDispatcher = UnconfinedTestDispatcher()
    }

    protected class FakeProvideString : ProvideResources<String> {

        var providingString = ""

        override fun provide(id: Int, value: String): String {
            return when(id) {
                R.string.error_message_unknown_qr_code_type -> "Unknown QR code type: $value"
                R.string.error_message_json_cannot_be_used -> "This json cannot be used in this application"
                else -> providingString
            }
        }
    }
}