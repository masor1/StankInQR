package com.masorone.stankinqrapp.features.machine.main.base

import com.google.gson.Gson
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class QRJsonStringTest {

    private lateinit var gson: Gson

    private val jsonNetworkInformation0 = "0"
    private val jsonNetworkInformation1 = "1"
    private val jsonNetworkInformation10 = "10"
    private val jsonNetworkInformationMinus10 = "-10"
    private val jsonNetworkInformation132 = "132"

    private val jsonInformation = "{" +
            "\"qrType\": \"Information\"," +
            "\"id\": 1," +
            "\"name\": \"name\"," +
            "\"type\": \"F\"," +
            "\"information\": \"information of item\"" +
            "}"

    private val jsonStatisticData = "{" +
            "\"qrType\": \"StatisticData\"," +
            "\"id\": 3,\n" +
            "\"name\": \"name 3\"," +
            "\"type\": \"T\"," +
            "\"information\": \"information of item\"" +
            "}"

    private val jsonUnknown1 = "{" +
            "\"qrType\": \"LolKek\"," +
            "\"id\": 3,\n" +
            "\"name\": \"name 3\"," +
            "\"type\": \"T\"," +
            "\"information\": \"information of item\"" +
            "}"

    private val jsonUnknown2 = "{" +
            "\"qrTyperes\": \"LolKek\"," +
            "\"id\": 3,\n" +
            "\"name\": \"name 3\"," +
            "\"type\": \"T\"," +
            "\"information\": \"information of item\"" +
            "}"

    private val nonJson = "{" +
            "\"qrTyper\": \"StatisticData\"," +
            "\"id\" item\"" +
            "}"

    @Before
    fun setUp() {
        gson = Gson()
    }

    @Test
    fun `fetch Information model from json string included information type`() {
        val qRJsonString = QRJsonString.Base(jsonInformation, gson)
        val actual = qRJsonString.model()
        val expected = QRJsonString.Model.Information(
            qrType = "Information",
            id = 1,
            name = "name",
            type = "F",
            information = "information of item"
        )
        assertEquals(expected, actual)
    }

    @Test
    fun `fetch StatisticData model from json string included statistic data type`() {
        val qRJsonString = QRJsonString.Base(jsonStatisticData, gson)
        val actual = qRJsonString.model()
        val expected = QRJsonString.Model.StatisticData(
            qrType = "StatisticData",
            id = 3,
            name = "name 3",
            type = "T",
            information = "information of item"
        )
        assertEquals(expected, actual)
    }

    @Test
    fun `fetch Unknown model from json string included network information type`() {
        var qRJsonString = QRJsonString.Base(jsonNetworkInformation0, gson)
        var actual = qRJsonString.model()
        var expected: QRJsonString.Model =
            QRJsonString.Model.Error("This json cannot be used in this application")
        assertEquals(expected, actual)

        qRJsonString = QRJsonString.Base(jsonNetworkInformation1, gson)
        actual = qRJsonString.model()
        expected = QRJsonString.Model.NetworkInformation
        assertEquals(expected, actual)

        qRJsonString = QRJsonString.Base(jsonNetworkInformation10, gson)
        actual = qRJsonString.model()
        expected = QRJsonString.Model.NetworkInformation
        assertEquals(expected, actual)

        qRJsonString = QRJsonString.Base(jsonNetworkInformationMinus10, gson)
        actual = qRJsonString.model()
        expected = QRJsonString.Model.Error("This json cannot be used in this application")
        assertEquals(expected, actual)

        qRJsonString = QRJsonString.Base(jsonNetworkInformation132, gson)
        actual = qRJsonString.model()
        expected = QRJsonString.Model.NetworkInformation
        assertEquals(expected, actual)
    }

    @Test
    fun `fetch Unknown model from json string included unknown type`() {
        val qRJsonString = QRJsonString.Base(jsonUnknown1, gson)
        val actual = qRJsonString.model()
        val expected = QRJsonString.Model.Unknown("unknown QR code type: LolKek")
        assertEquals(expected, actual)
    }

    @Test
    fun `fetch Unknown model from json string included error type`() {
        var qRJsonString = QRJsonString.Base(jsonUnknown2, gson)
        var actual = qRJsonString.model()
        var expected = QRJsonString.Model.Error("This json cannot be used in this application")
        assertEquals(expected, actual)

        qRJsonString = QRJsonString.Base(nonJson, gson)
        actual = qRJsonString.model()
        expected = QRJsonString.Model.Error("This json cannot be used in this application")
        assertEquals(expected, actual)
    }
}
