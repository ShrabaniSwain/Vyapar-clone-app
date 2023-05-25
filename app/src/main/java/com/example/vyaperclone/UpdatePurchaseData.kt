package com.example.vyaperclone

import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.vyaperclone.databinding.FragmentUpdatePurchaseDataBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [UpdatePurchaseData.newInstance] factory method to
 * create an instance of this fragment.
 */

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
        binding.etParty.text=editablePartyName
        val editableTotalAmount = Editable.Factory.getInstance().newEditable(Constants.TotalAmt.toString())
        binding.etTotal.text=editableTotalAmount
        val editablePaidAmt = Editable.Factory.getInstance().newEditable(Constants.PaidAmt.toString())
        binding.etPaid.text=editablePaidAmt
        val editableBillNo = Editable.Factory.getInstance().newEditable(Constants.BillNo.toString())
        binding.etBillNo.text=editableBillNo

        val balanceDueAmt = Constants.TotalAmt - Constants.PaidAmt
        val editableBalanceAmt = Editable.Factory.getInstance().newEditable(balanceDueAmt.toString())
        binding.balanceDue.text= editableBalanceAmt

    }

}