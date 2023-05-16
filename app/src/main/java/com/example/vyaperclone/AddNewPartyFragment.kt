package com.example.vyaperclone

import android.content.Intent
import android.os.Bundle
import android.provider.ContactsContract
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.vyaperclone.databinding.FragmentAddExpenseBinding
import com.example.vyaperclone.databinding.FragmentAddNewPartyBinding
import com.example.vyaperclone.databinding.FragmentOpeningBalanceBinding
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddNewPartyFragment : Fragment() {

    private lateinit var binding: FragmentAddNewPartyBinding
    private lateinit var binding1: FragmentOpeningBalanceBinding

    private val partiesViewModel: PartiesViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAddNewPartyBinding.inflate(inflater, container, false)
        binding1 = FragmentOpeningBalanceBinding.inflate(inflater, container, false)
        return binding.root
        return binding1.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val viewPageAdapter = PartyViewPagerAdapterNew(this)
        binding.viewPagerAddNewParty.adapter = viewPageAdapter

        TabLayoutMediator(binding.tabLayoutAddNewParty, binding.viewPagerAddNewParty) { tab, position ->
            tab.text = when (position) {
                0 -> "Addresses"
                1 -> "GST"
                2 -> "Opening Balance"
                else -> "Addresses"
            }
        }.attach()


        var partyName: String = ""
        var contactNo: String = ""
        var billingAdd: String = ""
        var amount: Long = 0

        binding.btnSave.setOnClickListener {
            if (isDataValid()) {
                partyName = binding.partyNameEditText.text.toString()
                contactNo = binding.contactNumberEditText.text.toString()
//                billingAdd = billingAddressEditText.text.toString()
//                amount = java.lang.Long.parseLong(binding1.openingBalanceEditText.text.toString())
                val inputString = binding1.openingBalanceEditText.text.toString()
                amount = inputString.toLongOrNull() ?: 0L
                var partyEntity =
                    PartyEntity(partyName, contactNo, "Pune", amount)
                partiesViewModel.addParty(partyEntity)
            }
            activity?.onBackPressed()
        }

        binding.cvImportContacts.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI)
            intent.type = ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE
            startActivityForResult(intent, 1)
        }

    }

    private fun isDataValid(): Boolean {
        if (binding.partyNameEditText.text == null) {
            binding.partyNameEditText.error = "Required"
            return false
        }

        if (binding.contactNumberEditText.text == null || binding.contactNumberEditText.text.toString().length != 10) {
            binding.contactNumberEditText.error = "Required"
            return false
        }
        if (binding1.openingBalanceEditText.text == null) {
            binding1.openingBalanceEditText.error = "Required"
            return false
        }


        return true
    }
}

private class PartyViewPagerAdapterNew(fm: AddNewPartyFragment) :
    FragmentStateAdapter(fm) {
    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> return AddressesFragment.newInstance()
            1 -> return GstFragment.newInstance()
            2 -> return OpeningBalanceFragment.newInstance()
            else -> CategoriesFragment.newInstance()
        }
    }
}