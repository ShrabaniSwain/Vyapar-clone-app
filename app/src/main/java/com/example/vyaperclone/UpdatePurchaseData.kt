package com.example.vyaperclone

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import androidx.navigation.fragment.findNavController
import com.example.vyaperclone.databinding.FragmentUpdatePurchaseDataBinding
import dagger.hilt.android.AndroidEntryPoint

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [UpdatePurchaseData.newInstance] factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
class UpdatePurchaseData : Fragment() {
    private var param1: String? = null
    private var param2: String? = null
    lateinit var binding: FragmentUpdatePurchaseDataBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }
    private val viewModel: ItemsViewModel by viewModels()
    val sharedViewModel: ItemsViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUpdatePurchaseDataBinding.inflate(inflater, container, false)
        return binding.root

    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            UpdatePurchaseData().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val editablePartyName = Editable.Factory.getInstance().newEditable(Constants.PartyName)
        binding.etParty.text = editablePartyName
        binding.etParty.isEnabled = false

        val editableTotalAmount =
            Editable.Factory.getInstance().newEditable(Constants.TotalAmt.toString())
        binding.etTotal.text = editableTotalAmount
        binding.etTotal.isEnabled = false

        val editablePaidAmt =
            Editable.Factory.getInstance().newEditable(Constants.PaidAmt.toString())
        binding.etPaid.text = editablePaidAmt
        binding.etPaid.isEnabled = false

        val editableBillNo = Editable.Factory.getInstance().newEditable(Constants.BillNo.toString())
        binding.etBillNo.text = editableBillNo
        binding.etBillNo.isEnabled = false

        val balanceDueAmt = Constants.TotalAmt - Constants.PaidAmt
        val editableBalanceAmt =
            Editable.Factory.getInstance().newEditable(balanceDueAmt.toString())
        binding.balanceDue.text = editableBalanceAmt
        binding.balanceDue.isEnabled = false

        binding.btnEdit.setOnClickListener {
            binding.etParty.isEnabled = true
            binding.etTotal.isEnabled = true
            binding.etPaid.isEnabled = true
            binding.etBillNo.isEnabled = true
            binding.balanceDue.isEnabled = true
        }

        binding.btnSave.setOnClickListener {
            val updatedPartyName = binding.etParty.text.toString()
            val updatedTotalAmount = binding.etTotal.text.toString().toInt()
            val updatedPaidAmt = binding.etPaid.text.toString().toInt()
            val updatedBillNo = binding.etBillNo.text.toString().toInt()

            val updatedTransaction = TransactionEntity(
                billNo = updatedBillNo,
                Constants.PURCHASE,
                partyName = updatedPartyName,
                billedItemNames = null,
                billedItemQuantity = null,
                paidAmt = updatedPaidAmt.toLong(),0,
                total = updatedTotalAmount.toLong()
            )
//            sharedViewModel.updatedTransaction.value=updatedTransaction

            viewModel.updateTransaction(updatedTransaction)
            findNavController().popBackStack()
        }
    }

}