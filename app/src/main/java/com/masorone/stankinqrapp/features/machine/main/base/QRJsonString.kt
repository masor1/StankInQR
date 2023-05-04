package com.masorone.stankinqrapp.features.machine.main.base

import com.google.gson.Gson
import org.json.JSONException
import org.json.JSONObject

interface QRJsonString {

    fun model(): Model

    class Base(
        private val jsonString: String,
        private val gson: Gson
    ) : QRJsonString {

        override fun model(): Model = try {
            when (val type = JSONObject(jsonString).getString("qrType")) {
                "Information" -> gson.fromJson(jsonString, Model.Information::class.java)
                "StatisticData" -> gson.fromJson(jsonString, Model.StatisticData::class.java)
                else -> Model.Unknown("unknown QR code type: $type")
            }
        } catch (e: JSONException) {
            try {
                if (jsonString.toInt() > 0) {
                    Model.NetworkInformation
                } else {
                    Model.Error("This json cannot be used in this application")
                }
            } catch (e: NumberFormatException) {
                Model.Error("This json cannot be used in this application")
            }
        }
    }

    sealed interface Model {

        object NetworkInformation : Model

        data class Information(
            private val qrType: String,
            private val id: Int,
            private val name: String,
            private val type: String,
            private val information: String
        ) : Model

        data class StatisticData(
            private val qrType: String,
            private val id: Int,
            private val name: String,
            private val type: String,
            private val information: String
        ) : Model

        data class Unknown(private val message: String) : Model

        data class Error(private val message: String) : Model
    }
}