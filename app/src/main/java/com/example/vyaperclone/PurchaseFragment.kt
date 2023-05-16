package com.example.vyaperclone

import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.vyaperclone.databinding.FragmentPurchaseBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PurchaseFragment : Fragment() {
    private lateinit var binding: FragmentPurchaseBinding

    var transactions = mutableListOf<TransactionEntity>()
    val adapter = CustomAdapter(transactions) { text -> binding.etParty.setText(text) }


    companion object {
        fun newInstance() = PurchaseFragment()

    }

   private val sharedViewModel: PurchaseSharedViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentPurchaseBinding.inflate(inflater, container, false)

        view?.findViewById<ComposeView>(R.id.ItemRecyclerCompose)?.setContent {
            LazyColumn() {
                itemsIndexed(
                    items = sharedViewModel.listOfPurchase.value
                ) { index, purchase ->
                    BilledItem(purchase.productName, index, purchase.quantity, purchase.price)
                }
            }
        }
        return binding.root

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        binding.btnAddItems.setOnClickListener {
            val action = PurchaseFragmentDirections.actionNavPurchaseToAddPurchaseFragment()
            findNavController().navigate(action)
        }

        binding.etParty.setOnClickListener{
            binding.recyclerviewName.visibility = View.VISIBLE
        }

        binding.btnSave.setOnClickListener {
            if (isDataValid()) {

                MainScope().launch{
                    sharedViewModel.addTransaction(
                        TransactionEntity(
                            binding.etBillNo.text.toString().toInt(),
                            Constants.PURCHASE,
                            binding.etParty.text.toString(),
                            convertListToBilledItems(),
                            convertListToBilledQuantity(),
                            binding.etPaid.text.toString().toLong(),
                            0,
                            binding.etTotal.text.toString().toLong()
                        )
                    )
                    sharedViewModel.listOfPurchase.value.clear()

                }
                activity?.onBackPressed()
            }


        }

        binding.btnCameraPurchaseFrag.setOnClickListener {
            val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivity(cameraIntent)
            true
        }
        binding.btnSharePurchaseFrag.setOnClickListener {
            val myIntent = Intent(Intent.ACTION_SEND)
            myIntent.type = "text/plain"
            val name =
                "Item Details:  \n " +
                        "Name: ${ binding.etBillNo.text}\n Paid Amount: ${ binding.etPaid.text}\n " +
                        " Total Amount: ${ binding.etTotal.text}\n"

            myIntent.putExtra(Intent.EXTRA_TEXT, name);
            startActivity(Intent.createChooser(myIntent, "Share Using"))
        }

        binding.etParty.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
            }

            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
            }

            override fun afterTextChanged(editable: Editable) {
                //after the change calling the method and passing the search input
                filter(editable.toString())
            }
        })

    }

    private fun filter(text: String) {
        //new array list that will hold the filtered data
        val filteredTransactions = ArrayList<TransactionEntity>()

        //looping through existing elements
        for (transaction in transactions) {
            //if the existing elements contains the search input
            if (transaction.partyName?.toLowerCase()?.contains(text.toLowerCase())!!) {
                //adding the element to filtered list
                filteredTransactions.add(transaction)
            }
        }

        //calling a method of the adapter class and passing the filtered list
        adapter.filterList(filteredTransactions)
    }

    private fun isDataValid(): Boolean {
        var isValid = true
        if ( binding.etBillNo.text.toString().isEmpty()) {
            binding.etBillNo.error = "Required"
            isValid = false
        }
        if ( binding.etParty.text.toString().isEmpty()) {
            binding.etParty.error = "Required"
            isValid = false
        }
        if ( binding.etPaid.text.toString().isEmpty()) {
            binding.etPaid.error = "Required"
            isValid = false
        }
        if ( binding.etTotal.text.toString().isEmpty()) {
            binding.etTotal.error = "Required"
            isValid = false
        }
        return isValid
    }

    private fun convertListToBilledQuantity(): String? {
        val data = sharedViewModel.listOfPurchase.value
        var quantity = ""
        for (i in data.indices) {

            if (i == 0) {
                quantity += "${data[i].quantity}"
            } else {
                quantity += ",${data[i].quantity}"
            }

        }
        return quantity
    }

    private fun convertListToBilledItems(): String? {
        val data = sharedViewModel.listOfPurchase.value
        var purchase = ""
        for (i in data.indices) {
            if (i == 0) {
                purchase += "${data[i].productName}"
            } else {
                purchase += ",${data[i].productName}"
            }

        }
        return purchase
    }

    override fun onResume() {
        super.onResume()
        sharedViewModel.getReport().observe(this, Observer {
            transactions.clear()
            transactions.addAll(it)
            adapter.notifyDataSetChanged()
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding.recyclerviewName.layoutManager = LinearLayoutManager(context)
        binding.recyclerviewName.adapter = adapter
    }

}