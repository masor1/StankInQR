package com.masorone.stankinqrapp.presentation.screen_machine_description

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.masorone.stankinqrapp.R
import com.masorone.stankinqrapp.databinding.FragmentMachineDescriptionBinding
import com.masorone.stankinqrapp.presentation.MachineUI

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
        val machine = arguments?.getParcelable<MachineUI.Success>(MachineUI.SUCCESS)
            ?: arguments?.getParcelable<MachineUI.Error>(MachineUI.ERROR)

        when (machine) {
            is MachineUI.Success -> {
                val dataList = machine.show().split("|")
                binding.machineIdValue.text = dataList[0]
                binding.machineNameValue.text = dataList[1]
                Glide.with(requireActivity()).load(dataList[2]).into(binding.machineIcon)
                binding.machineDescription.text = dataList[3]
            }
            is MachineUI.Error -> {
                binding.machineDescriptionLayout.visibility = View.GONE
                binding.errorTextLayout.apply {
                    visibility = View.VISIBLE
                    text = machine.show()
                }
            }
        }

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