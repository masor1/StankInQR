package com.masorone.stankinqrapp.features.machine.main.presentation.screen_all_machines

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.masorone.stankinqrapp.R
import com.masorone.stankinqrapp.databinding.FragmentAllMachinesBinding
import com.masorone.stankinqrapp.features.machine.main.presentation.MachineUi
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AllMachinesFragment : Fragment() {

    private var _binding: FragmentAllMachinesBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<FetchAllMachinesViewModel>()
    private lateinit var adapter: MachineRVAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAllMachinesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.fetch(savedInstanceState == null)
        binding.openQrCodeScannerButton.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_QRCodeScannerFragment)
        }
        adapter = MachineRVAdapter { machineUi ->
            findNavController().navigate(
                R.id.action_mainFragment_to_machineDescriptionFragment,
                Bundle().apply {
                    when (machineUi) {
                        is MachineUi.Success -> putParcelable(MachineUi.SUCCESS, machineUi)
                        is MachineUi.Error -> putParcelable(MachineUi.ERROR, machineUi)
                    }
                }
            )
        }
        binding.allMachinesRecyclerView.adapter = adapter
        viewModel.observe(viewLifecycleOwner) { viewState ->
            when (viewState) {
                is FetchAllMachinesViewModel.ViewState.Loading -> {
                    binding.allMachinesProgressBar.visibility = View.VISIBLE
                    binding.allMachinesRecyclerView.visibility = View.GONE
                    binding.openQrCodeScannerButton.visibility = View.GONE
                }
                is FetchAllMachinesViewModel.ViewState.Result -> {
                    adapter.submitList(viewState.machines)
                    binding.allMachinesProgressBar.visibility = View.GONE
                    binding.allMachinesRecyclerView.visibility = View.VISIBLE
                    binding.openQrCodeScannerButton.visibility = View.VISIBLE
                }
            }
        }

        requireActivity().onBackPressedDispatcher.addCallback(
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    requireActivity().finish()
                }
            }
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
