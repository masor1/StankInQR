package com.masorone.stankinqrapp.data.cloud

import com.google.gson.annotations.SerializedName
import com.masorone.stankinqrapp.data.MachineData

data class MachineDto(
    @SerializedName("id")
    private val id: String,
    @SerializedName("name")
    private val name: String,
    @SerializedName("imageUrl")
    private val imageUrl: String,
    @SerializedName("description")
    private val description: String
) {

    fun map() = MachineData.Success(id, name, imageUrl, description)
}