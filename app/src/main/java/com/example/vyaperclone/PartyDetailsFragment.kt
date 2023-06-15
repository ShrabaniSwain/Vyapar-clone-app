package com.example.vyaperclone

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.example.vyaperclone.databinding.FragmentPartyDetailsBinding
import com.example.vyaperclone.databinding.FragmentPurchaseBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PartyDetailsFragment : Fragment() {

    private lateinit var binding: FragmentPartyDetailsBinding


    companion object {
        fun newInstance() = PartyDetailsFragment()
    }

    private val viewModel: ItemsViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentPartyDetailsBinding.inflate(inflater, container, false)
        binding.addItemRecyclerCompose.setContent {
            LazyColumn() {
                itemsIndexed(
                    items = viewModel.transcations.value
                ) { index, transaction ->
                    TransactionCard(transactionEntity = transaction, onClick = { /*TODO*/ })
                }
            }
        }
        return binding.root

    }

}