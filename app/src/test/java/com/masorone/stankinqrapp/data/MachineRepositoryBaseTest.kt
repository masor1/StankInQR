package com.masorone.stankinqrapp.data

import com.masorone.stankinqrapp.data.cloud.MachineCloudDataSource
import com.masorone.stankinqrapp.domain.MachineDomain
import kotlinx.coroutines.runBlocking
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody
import org.junit.Assert.assertEquals
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Response

class MachineRepositoryBaseTest {

    @Test
    fun fetchByID() = runBlocking {
        val repository = MachineRepositoryBase(TestMachineCloudDataSource())
        val actual = repository.fetchByID("1")
        val expected = MachineDomain.Success("1", "name", "imgUrl")
        assertEquals(expected, actual)
    }

    @Test
    fun fetchList() {
    }

    private inner class TestMachineCloudDataSource(
        private val connection: Boolean = true
    ) : MachineCloudDataSource {

        private val httpException = HttpException(
            Response.error<ResponseBody>(
                500,
                ResponseBody.create("plain/text".toMediaTypeOrNull(), "some content")
            )
        )


        override suspend fun fetch(id: String): MachineData {
            return if (connection) {
                MachineData.Success(id, "name", "imgUrl")
            } else {
                MachineData.Error(httpException)
            }
        }

        override suspend fun fetch(): List<MachineData> {
            return if (connection) {
                listOf(
                    MachineData.Success("1", "name", "imgUrl"),
                    MachineData.Success("2", "name", "imgUrl"),
                    MachineData.Success("3", "name", "imgUrl"),
                )
            } else {
                listOf(MachineData.Error(httpException))
            }
        }
    }
}