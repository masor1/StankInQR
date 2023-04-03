package com.masorone.stankinqrapp.presentation.screen_main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.masorone.stankinqrapp.R
import com.masorone.stankinqrapp.databinding.FragmentMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<MainViewModel>()
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
        super.onViewCreated(view, savedInstanceState)
        binding.mainOpenQrCodeScannerButton.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_QRCodeScannerFragment)
        }
        adapter = MachineRVAdapter { id ->
            Toast.makeText(requireContext(), "$id", Toast.LENGTH_SHORT).show()
        }
        binding.mainRecyclerView.adapter = adapter
        viewModel.listOfMachine.observe(viewLifecycleOwner) { listOfMachineUI ->
            adapter.submitList(listOfMachineUI)
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