package com.masorone.stankinqrapp.presentation.screen_qr_code_scanner

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.budiyev.android.codescanner.*
import com.masorone.stankinqrapp.App
import com.masorone.stankinqrapp.R
import com.masorone.stankinqrapp.databinding.FragmentQrCodeScannerBinding
import com.masorone.stankinqrapp.presentation.MachineUI
import com.masorone.stankinqrapp.presentation.ViewModelFactory
import javax.inject.Inject

class QRCodeScannerFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private lateinit var codeScanner: CodeScanner
    private lateinit var viewModel: QRCodeScannerViewModel

    private var _binding: FragmentQrCodeScannerBinding? = null
    private val binding get() = _binding!!

    private val appComponent by lazy {
        (requireActivity().application as App).appComponent
    }

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

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentQrCodeScannerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        appComponent.inject(this)
        super.onViewCreated(view, savedInstanceState)
        codeScanner = CodeScanner(requireContext(), binding.scanner)
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
        setupViewModel()
        setupCodeScanner()
        handleCodeScanner()
        observeCodeScannerValue()
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(
            this,
            viewModelFactory
        )[QRCodeScannerViewModel::class.java]
    }

    private fun setupCodeScanner() {
        codeScanner.autoFocusMode = AutoFocusMode.CONTINUOUS
        codeScanner.scanMode = ScanMode.SINGLE
    }

    private fun handleCodeScanner() {
        codeScanner.decodeCallback = DecodeCallback { qrCode ->
            requireActivity().runOnUiThread {
                binding.progressLayout.visibility = View.VISIBLE
            }
            viewModel.fetch(qrCode)
        }
        codeScanner.errorCallback = ErrorCallback { throwable ->
            viewModel.showError(throwable)
        }
        binding.scanner.setOnClickListener {
            codeScanner.startPreview()
        }
    }

    private fun observeCodeScannerValue() {
        viewModel.valueFromScanner.observe(viewLifecycleOwner) { machine ->
            findNavController().apply {
                navigate(
                    R.id.action_QRCodeScannerFragment_to_machineDescriptionFragment,
                    Bundle().apply {
                        when (machine) {
                            is MachineUI.Success -> putParcelable(MachineUI.SUCCESS, machine)
                            is MachineUI.Error -> putParcelable(MachineUI.ERROR, machine)
                        }
                    }
                )
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