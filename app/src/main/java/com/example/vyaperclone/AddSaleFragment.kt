package com.example.vyaperclone

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.vyaperclone.databinding.FragmentAddSaleBinding

class AddSaleFragment : Fragment() {

    lateinit var _binding: FragmentAddSaleBinding
    val binding: FragmentAddSaleBinding get() = _binding
    private val sharedViewModel: SaleSharedViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        //hiding the action bar

        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()
        _binding = FragmentAddSaleBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnSave.setOnClickListener {

            if (isDataValid()) {
                sharedViewModel.listOfSale.value.add(

                    SaleDTO(

                        binding.etProductName.text.toString(),
                        binding.etQuantity.text.toString().toInt(),
                        binding.etRate.text.toString().toLong()
                    )
                )
                activity?.onBackPressed()
            }
        }

        _binding.ivBackBtn.setOnClickListener {
            val action = AddSaleFragmentDirections.actionAddSaleFragmentToNavSale()
            findNavController().navigate(action)
        }
        _binding.ivSettings.setOnClickListener {
            val action = AddSaleFragmentDirections.actionAddSaleFragmentToNavSettings()
            findNavController().navigate(action)
        }

        _binding.cvSaveAndNew.setOnClickListener {
            Toast.makeText(
                activity,
                "Item / services name cannot be left empty",
                Toast.LENGTH_SHORT
            ).show()
        }

    }

    override fun onResume() {
        super.onResume()
        //hiding the nav bar
        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()
    }

    private fun isDataValid(): Boolean {
        var isValid = true
        if (_binding.etProductName.text.toString().isEmpty()) {
            _binding.etProductName.error = "Required"
            isValid = false
        }
        if (_binding.etQuantity.text.toString().isEmpty()) {
            _binding.etQuantity.error = "Required"
            isValid = false
        }
        if (_binding.etRate.text.toString().isEmpty()) {
            _binding.etRate.error = "Required"
            isValid = false
        }


        return isValid
    }

}