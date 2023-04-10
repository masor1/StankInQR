package com.masorone.stankinqrapp.features.machine.main.presentation.screen_machine_description

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.masorone.stankinqrapp.R
import com.masorone.stankinqrapp.databinding.FragmentMachineDescriptionBinding
import com.masorone.stankinqrapp.features.machine.main.presentation.MachineUi
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MachineDescriptionFragment : Fragment() {

    private var _binding: FragmentMachineDescriptionBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMachineDescriptionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val machine = arguments?.getParcelable<MachineUi.Success>(MachineUi.SUCCESS)
            ?: arguments?.getParcelable<MachineUi.Error>(MachineUi.ERROR)!!

        machine.show(binding)

        binding.machineIcon.setOnClickListener {
            findNavController().navigate(R.id.action_machineDescriptionFragment_to_QRCodeScannerFragment)
        }

        requireActivity().onBackPressedDispatcher.addCallback(
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    findNavController().navigate(R.id.action_machineDescriptionFragment_to_mainFragment)
                }
            }
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}