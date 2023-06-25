package com.masorone.stankinqrapp.features.machine.main.base

import android.os.Parcelable
import com.google.gson.Gson
import com.masorone.stankinqrapp.R
import com.masorone.stankinqrapp.core.android.ProvideResources
import kotlinx.parcelize.Parcelize
import org.json.JSONException
import org.json.JSONObject

interface QRJsonString {

    fun model(): Model

    class Base(
        private val jsonString: String,
        private val gson: Gson,
        private val resources: ProvideResources<String>
    ) : QRJsonString {

        override fun model(): Model = try {
            when (val type = JSONObject(jsonString).getString(Model.jsonType)) {
                Model.Information.stringType -> gson.fromJson(
                    jsonString,
                    Model.Information::class.java
                )
                else -> Model.Unknown(
                    resources.provide(
                        R.string.error_message_unknown_qr_code_type,
                        type
                    )
                )
            }
        } catch (e: JSONException) {
            val errorModel =
                Model.Error(resources.provide(R.string.error_message_json_cannot_be_used))
            try {
                if (jsonString.toInt() > 0) {
                    Model.NetworkInformation
                } else {
                    errorModel
                }
            } catch (e: NumberFormatException) {
                errorModel
            }
        }
    }

    sealed interface Model {

        object NetworkInformation : Model

        @Parcelize
        data class Information(
            val qrType: String,
            val id: Int,
            val name: String,
            val type: String,
            val company: String
        ) : Model, Parcelable {

            companion object {

                val stringType: String = Information::class.java.simpleName
            }
        }

        data class Unknown(private val message: String) : Model

        data class Error(private val message: String) : Model

        companion object {

            val jsonType: String = "qrType"
        }
    }
}
