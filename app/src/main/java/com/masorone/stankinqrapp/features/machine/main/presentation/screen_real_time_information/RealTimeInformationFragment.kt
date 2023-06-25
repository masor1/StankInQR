package com.masorone.stankinqrapp.features.machine.main.presentation.screen_real_time_information

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.annotation.RequiresApi
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.masorone.stankinqrapp.R
import com.masorone.stankinqrapp.databinding.FragmentRealTimeInformationBinding
import com.masorone.stankinqrapp.features.machine.main.base.QRJsonString
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class RealTimeInformationFragment : Fragment() {

    private var _binding: FragmentRealTimeInformationBinding? = null
    private val binding get() = _binding!!

    private val viewModel: RealTimeInformationViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRealTimeInformationBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("SetTextI18n", "ResourceType")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireActivity().onBackPressedDispatcher.addCallback(
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    findNavController().navigate(R.id.action_realTimeInformationFragment_to_QRCodeScannerFragment)
                }
            }
        )

        val model: QRJsonString.Model.Information? = arguments?.getParcelable("key")
        viewModel.startFetchData(model!!)

        binding.cardViewDocumentationLink.setOnClickListener {
            val url = if (model.id == 1) {
                "https://www.haascnc.com/content/haascnc/ru/Community/pre-install-guide/ST-10Y.html"
            } else {
                "https://www.haascnc.com/machines/vertical-mills/vf-series/models/small/vf-2ss.html"
            }
            startActivity(
                Intent(Intent.ACTION_VIEW, Uri.parse(url))
            )
        }

        lifecycleScope.launch(Dispatchers.IO) {
            viewModel.state.collect { viewState ->
                withContext(Dispatchers.Main) {
                    when (viewState) {
                        is RealTimeInformationViewModel.ViewState.Loading -> {
                            binding.progressBarLayout.isVisible = true
                        }
                        is RealTimeInformationViewModel.ViewState.OnlyQrCodeInformation -> {
                            binding.machineId.text = viewState.baseData.id
                            binding.machineName.text = viewState.baseData.name
                            binding.machineCompany.text = viewState.baseData.company
                            binding.machineType.text = viewState.baseData.type
                            binding.machineImage.setImageResource(viewState.baseData.imageId)
                            binding.progressBarLayout.isVisible = false
                            binding.cardViewRealTimeInformation.isVisible = false
                            binding.cardViewEconomy.isVisible = false
                        }
                        is RealTimeInformationViewModel.ViewState.FullInformation -> {
                            binding.machineSpindleRotationSpeed.text = viewState.realTimeData.machineSpindleRotationSpeed
                            binding.machineFeedRate.text = viewState.realTimeData.machineFeedRate
                            binding.machineNumberOfProcessedParts.text = viewState.realTimeData.machineNumberOfProcessedParts
                            binding.machineTemperature.text = viewState.realTimeData.machineTemperature
                            binding.machineVibration.text = viewState.realTimeData.machineVibration
                            binding.progressBarLayout.isVisible = false
                            binding.cardViewRealTimeInformation.isVisible = true
                            binding.cardViewEconomy.isVisible = true
                            binding.machinePo.text = viewState.realTimeData.errorRate
                            binding.machinePo.isVisible = model.id != 1
                            binding.machineEngineHours.text = viewState.realTimeData.engineHours
                            binding.machineResidualValue.text = viewState.realTimeData.residualValue
                        }
                    }
                }
            }
        }
    }
}