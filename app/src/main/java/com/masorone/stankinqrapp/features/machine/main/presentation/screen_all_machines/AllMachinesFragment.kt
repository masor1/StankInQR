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

    private val viewModel by viewModels<FetchAllViewModel>()
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

        binding.openQrCodeScannerButton.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_QRCodeScannerFragment)
        }
        adapter = MachineRVAdapter { machineUi ->
            viewModel.navigate(machineUi)
        }
        binding.allMachinesRecyclerView.adapter = adapter
        viewModel.viewState.observe(viewLifecycleOwner) { viewState ->
            when (viewState) {
                is FetchAllViewModel.ViewState.Loading -> {
                    binding.allMachinesProgressBar.visibility = View.VISIBLE
                    binding.allMachinesRecyclerView.visibility = View.GONE
                    binding.openQrCodeScannerButton.visibility = View.GONE
                }
                is FetchAllViewModel.ViewState.Success -> {
                    viewState.machines.invokeSubmitList(adapter)
                    binding.allMachinesProgressBar.visibility = View.GONE
                    binding.allMachinesRecyclerView.visibility = View.VISIBLE
                    binding.openQrCodeScannerButton.visibility = View.VISIBLE
                }
                is FetchAllViewModel.ViewState.Navigate -> {
                    findNavController().navigate(
                        R.id.action_mainFragment_to_machineDescriptionFragment,
                        Bundle().apply {
                            when (val machines = viewState.machine) {
                                is MachineUi.Success -> putParcelable(MachineUi.SUCCESS, machines)
                                is MachineUi.Error -> putParcelable(MachineUi.ERROR, machines)
                            }
                        }
                    )
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