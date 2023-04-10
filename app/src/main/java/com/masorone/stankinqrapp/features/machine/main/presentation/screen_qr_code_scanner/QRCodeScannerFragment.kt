package com.masorone.stankinqrapp.features.machine.main.presentation.screen_qr_code_scanner

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.budiyev.android.codescanner.*
import com.masorone.stankinqrapp.R
import com.masorone.stankinqrapp.databinding.FragmentQrCodeScannerBinding
import com.masorone.stankinqrapp.features.machine.main.presentation.MachineUi
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class QRCodeScannerFragment : Fragment() {

    private lateinit var codeScanner: CodeScanner
    private val viewModel by viewModels<QRCodeScannerViewModel>()

    private var _binding: FragmentQrCodeScannerBinding? = null
    private val binding get() = _binding!!

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
            }
            viewModel.fetch(qrCode.text)
        }
        codeScanner.errorCallback = ErrorCallback {
            viewModel.show()
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
                            is MachineUi.Success -> putParcelable(MachineUi.SUCCESS, machine)
                            is MachineUi.Error -> putParcelable(MachineUi.ERROR, machine)
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