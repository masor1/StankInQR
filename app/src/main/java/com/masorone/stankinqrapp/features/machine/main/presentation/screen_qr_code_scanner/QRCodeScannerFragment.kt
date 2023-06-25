package com.masorone.stankinqrapp.features.machine.main.presentation.screen_qr_code_scanner

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.budiyev.android.codescanner.*
import com.google.gson.Gson
import com.masorone.stankinqrapp.R
import com.masorone.stankinqrapp.core.android.ProvideResources
import com.masorone.stankinqrapp.databinding.FragmentQrCodeScannerBinding
import com.masorone.stankinqrapp.features.machine.main.base.QRJsonString
import com.masorone.stankinqrapp.features.machine.main.presentation.MachineUi
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class QRCodeScannerFragment : Fragment() {

    @Inject
    lateinit var provideStringResources: ProvideResources<String>

    private var _binding: FragmentQrCodeScannerBinding? = null
    private val binding get() = _binding!!

    private lateinit var qrJsonString: QRJsonString

    private val viewModel by viewModels<QRCodeScannerViewModel>()
    private val codeScanner: CodeScanner by lazy {
        CodeScanner(requireContext(), binding.scanner)
    }

    private val permReqLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) {
            if (it) {
                start()
            } else {
                Toast.makeText(
                    requireContext(),
                    requireContext().getText(R.string.camera_permission),
                    Toast.LENGTH_SHORT
                ).show()
                requestPermission()
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentQrCodeScannerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        permReqLauncher.launch(android.Manifest.permission.CAMERA)

        requireActivity().onBackPressedDispatcher.addCallback(
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    findNavController().navigate(R.id.action_QRCodeScannerFragment_to_mainFragment)
                }
            }
        )
    }

    private fun start() {
        setupCodeScanner()
        handleCodeScanner()
        observeCodeScannerValue()
    }

    private fun setupCodeScanner() {
        codeScanner.autoFocusMode = AutoFocusMode.CONTINUOUS
        codeScanner.scanMode = ScanMode.SINGLE
    }

    private fun handleCodeScanner() {
        codeScanner.decodeCallback = DecodeCallback { qrCode ->
            requireActivity().runOnUiThread {
                binding.progressLayout.visibility = View.VISIBLE
                val model = QRJsonString.Base(
                    qrCode.text,
                    Gson(),
                    provideStringResources
                ).model()

                when (model) {
                    is QRJsonString.Model.Information -> {
                        findNavController().navigate(
                            R.id.action_QRCodeScannerFragment_to_realTimeInformationFragment,
                            Bundle().apply {
                                putParcelable("key", model)
                            }
                        )
                    }
                    is QRJsonString.Model.NetworkInformation -> viewModel.fetch(qrCode.text)
                    is QRJsonString.Model.Unknown -> {
                        showToast("Unknown")
                        viewModel.init()
                    }
                    is QRJsonString.Model.Error -> {
                        showToast("Error")
                        viewModel.init()
                    }
                }
            }
        }
        codeScanner.errorCallback = ErrorCallback {
            viewModel.show()
        }
        binding.scanner.setOnClickListener {
            codeScanner.startPreview()
        }
    }

    private fun showToast(text: String) {
        Toast.makeText(requireContext(), text, Toast.LENGTH_SHORT).show()
    }

    private fun observeCodeScannerValue() {
        viewModel.observe(viewLifecycleOwner) { viewState ->
            when (viewState) {
                is QRCodeScannerViewModel.ViewState.NetworkInformation -> {
                    findNavController().apply {
                        navigate(
                            R.id.action_QRCodeScannerFragment_to_machineDescriptionFragment,
                            Bundle().apply {
                                when (val machine = viewState.machineUi) {
                                    is MachineUi.Success -> putParcelable(
                                        MachineUi.SUCCESS,
                                        machine
                                    )
                                    is MachineUi.Error -> putParcelable(MachineUi.ERROR, machine)
                                }
                            }
                        )
                    }
                    viewModel.init()
                }
                is QRCodeScannerViewModel.ViewState.QrCodeScanning -> {
                    binding.progressLayout.isVisible = false
                    binding.scanner.isVisible = true
                }
                else -> {}
            }
        }
    }

    private fun requestPermission() {
        ActivityCompat.requestPermissions(
            requireActivity(),
            arrayOf(android.Manifest.permission.CAMERA),
            CAMERA_RC
        )
    }

    override fun onResume() {
        super.onResume()
        codeScanner.startPreview()
    }

    override fun onPause() {
        codeScanner.releaseResources()
        super.onPause()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {

        private const val CAMERA_RC = 100
    }
}
