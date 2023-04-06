package com.masorone.stankinqrapp.presentation.screen_main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.masorone.stankinqrapp.App
import com.masorone.stankinqrapp.R
import com.masorone.stankinqrapp.databinding.FragmentMainBinding
import com.masorone.stankinqrapp.presentation.MachineUI
import com.masorone.stankinqrapp.presentation.ViewModelFactory
import javax.inject.Inject

class MainFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    private val appComponent by lazy {
        (requireActivity().application as App).appComponent
    }

    private lateinit var viewModel: MainViewModel
    private lateinit var adapter: MachineRVAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        appComponent.inject(this)
        super.onViewCreated(view, savedInstanceState)
        binding.mainOpenQrCodeScannerButton.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_QRCodeScannerFragment)
        }
        adapter = MachineRVAdapter { id ->
            viewModel.navigate(id)
        }
        binding.mainRecyclerView.adapter = adapter
        viewModel = ViewModelProvider(
            this,
            viewModelFactory
        )[MainViewModel::class.java]
        viewModel.listOfMachine.observe(viewLifecycleOwner) { listOfMachineUI ->
            adapter.submitList(listOfMachineUI)
        }
        viewModel.machine.observe(viewLifecycleOwner) { machine ->
            findNavController().navigate(
                R.id.action_mainFragment_to_machineDescriptionFragment,
                Bundle().apply {
                    when (machine) {
                        is MachineUI.Success -> putParcelable(MachineUI.SUCCESS, machine)
                        is MachineUI.Error -> putParcelable(MachineUI.ERROR, machine)
                    }
                })
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