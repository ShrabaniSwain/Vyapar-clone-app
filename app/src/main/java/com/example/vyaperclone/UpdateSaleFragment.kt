package com.example.vyaperclone

import android.os.Bundle
import android.text.Editable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.vyaperclone.databinding.FragmentUpdatePurchaseDataBinding
import com.example.vyaperclone.databinding.FragmentUpdateSaleBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [UpdateSaleFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class UpdateSaleFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    lateinit var binding: FragmentUpdateSaleBinding

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
        // Inflate the layout for this fragment
        binding = FragmentUpdateSaleBinding.inflate(inflater, container, false)
        return binding.root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment UpdateSaleFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            UpdateSaleFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val editablePartyName = Editable.Factory.getInstance().newEditable(Constants.PartyName)
        binding.etCustomer.text=editablePartyName

        val editableTotalAmount = Editable.Factory.getInstance().newEditable(Constants.TotalAmt.toString())
        binding.etTotalAmount.text=editableTotalAmount

        val editablePaidAmt = Editable.Factory.getInstance().newEditable(Constants.Received.toString())
        binding.etPaidAmount.text=editablePaidAmt

        val editableBillNo = Editable.Factory.getInstance().newEditable(Constants.BillNo.toString())
        binding.etInvNo.text=editableBillNo

        val balanceDueAmt = Constants.TotalAmt - Constants.Received
        val editableBalanceAmt = Editable.Factory.getInstance().newEditable(balanceDueAmt.toString())
        binding.etBalanceDue.text= editableBalanceAmt

    }
}