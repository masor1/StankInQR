package com.masorone.stankinqrapp.features.machine.main.presentation.screen_real_time_information

import android.annotation.SuppressLint
import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.masorone.stankinqrapp.R
import com.masorone.stankinqrapp.core.android.DispatchersList
import com.masorone.stankinqrapp.features.machine.api.FetchRealTimeDataUseCase
import com.masorone.stankinqrapp.features.machine.api.model.RealTimeData
import com.masorone.stankinqrapp.features.machine.main.base.QRJsonString
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@SuppressLint("ResourceType")
@HiltViewModel
class RealTimeInformationViewModel @Inject constructor(
    private val dispatchersList: DispatchersList,
    private val fetchRealTimeDataUseCase: FetchRealTimeDataUseCase
) : ViewModel() {

    val state = MutableStateFlow<ViewState>(ViewState.Loading)

    @SuppressLint("ResourceType")
    fun startFetchData(model: QRJsonString.Model.Information) {
        val baseData = BaseData(
            id = "ID: ${model.id}",
            name = "Модель: ${model.name}",
            company = "Компания: ${model.company}",
            type = "Тип: ${model.type}",
            imageId = if (model.id == 1) R.drawable.machine_st_10_y else R.drawable.machine_vf_2ss
        )
        viewModelScope.launch(dispatchersList.io()) {
            delay(1000)
            state.value = ViewState.OnlyQrCodeInformation(baseData)
            fetchRealTimeDataUseCase.fetchById(model.id).collect { realTimeData ->
                state.value = ViewState.FullInformation(baseData, realTimeData)
            }
        }
    }

    sealed interface ViewState {

        object Loading : ViewState

        data class OnlyQrCodeInformation(
            val baseData: BaseData
        ) : ViewState

        data class FullInformation(
            val baseData: BaseData,
            val realTimeData: RealTimeData
        ) : ViewState
    }

    data class BaseData(
        val id: String,
        val name: String,
        val company: String,
        val type: String,
        @StringRes val imageId: Int
    )
}
