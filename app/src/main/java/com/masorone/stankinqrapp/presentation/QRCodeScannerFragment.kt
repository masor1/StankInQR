package com.masorone.stankinqrapp.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.budiyev.android.codescanner.*
import com.masorone.stankinqrapp.data.MachineRepositoryBase
import com.masorone.stankinqrapp.data.cloud.MachineCloudDataStore
import com.masorone.stankinqrapp.data.cloud.MachineRetrofitBuilder
import com.masorone.stankinqrapp.databinding.FragmentQrCodeScannerBinding
import com.masorone.stankinqrapp.domain.FetchByIdUseCase
import retrofit2.converter.gson.GsonConverterFactory

class QRCodeScannerFragment : Fragment() {

    private var _binding: FragmentQrCodeScannerBinding? = null
    private val binding get() = _binding!!

    private val permReqLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) {
            if (it) {
                start()
            } else {
                Toast.makeText(
                    requireContext(),
                    "Необходимо разрешение доступа к камере, для работы приложения",
                    Toast.LENGTH_SHORT
                ).show()
                requestPermission()
            }
        }

    private lateinit var codeScanner: CodeScanner

    private val viewModel: QRCodeScannerViewModel by viewModels {
        ViewModelFactory(
            FetchByIdUseCase(
                MachineRepositoryBase(
                    MachineCloudDataStore.Base(
                        MachineRetrofitBuilder(
                            GsonConverterFactory.create()
                        ).apiService
                    )
                )
            )
        )
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
        codeScanner = CodeScanner(requireContext(), binding.scanner)
        permReqLauncher.launch(android.Manifest.permission.CAMERA)
    }

    private fun start() {
        setupCodeScanner()
        handleCodeScanner()
        observeCodeScannerValue()
    }

    private fun setupCodeScanner() {
        codeScanner.autoFocusMode = AutoFocusMode.CONTINUOUS
        codeScanner.scanMode = ScanMode.CONTINUOUS
    }

    private fun handleCodeScanner() {
        codeScanner.decodeCallback = DecodeCallback { qrCode ->
            viewModel.fetch(qrCode)
        }
        codeScanner.errorCallback = ErrorCallback { error ->
            viewModel.showError(error)
        }
    }

    private fun observeCodeScannerValue() {
        viewModel.valueFromScanner.observe(viewLifecycleOwner) {
            binding.valueFromScanner.text = it
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