package com.example.vyaperclone

import android.app.Dialog
import android.content.res.ColorStateList
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Button
import android.widget.EditText
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getColor
import androidx.fragment.app.activityViewModels
import com.example.vyaperclone.databinding.FragmentPartyDetailsBinding
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

        return ComposeView(requireContext()).apply {

            setContent {

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0xFFF3F3F3)),
                ) {
                    Column(
                        modifier = Modifier.background(Color.White)
                    ) {

                        Row(
                            modifier = Modifier
                                .horizontalScroll(rememberScrollState())
                                .padding(bottom = 15.dp, top = 15.dp)
                        ) {
                            Spacer(modifier = Modifier.size(7.dp))
                            var type: String = ""
                            var amount: String = ""
                            if (Constants.PartyType == Constants.PURCHASE || Constants.PartyType == Constants.SALE)
                            {amount = "₹ " + ((Constants.Amount.toLong() ?: 0) - getTheBigger(Constants.ReceivedAmount.toLong() ?: 0, Constants.PaidAmount.toLong() ?: 0)).toString()}
                            else {amount = Constants.Amount}
                            if (Constants.PartyType == Constants.SALE || Constants.PartyType == "Receive"){ type = "Receivable" } else {type = "Payable"}
                            SendReminderCard(
                                Constants.AllPartyName,Constants.CONTACTNO,R.drawable.ic_upward,"Send Reminder", type = type,amount,{})
                        }
                        Selector(viewModel.selectedType.value) {
                            viewModel.changeSelectedType(it)
                        }

                        if (viewModel.selectedType.value == 0 || viewModel.selectedType.value == 2) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(60.dp),
                                horizontalArrangement = Arrangement.End,
                                verticalAlignment = Alignment.CenterVertically
                            ) {}
                        } else {

                            TextField(
                                value = viewModel.searchQuery.value,
                                onValueChange = { viewModel.searchQueryChange(it) },
                                colors = TextFieldDefaults.textFieldColors(
                                    backgroundColor = Color.Transparent,
                                    focusedIndicatorColor = Color.Transparent,
                                    unfocusedIndicatorColor = Color.Transparent,
                                    textColor = Color.Black,
                                ),
                            )
                        }
                        Divider(color = Color.LightGray, thickness = 1.dp, startIndent = 10.dp)
                        if (viewModel.selectedType.value == 2) {
                            LazyColumn() {
                                itemsIndexed(
                                    items = viewModel.transcations.value
                                ) { index, party ->
                                    if (party.partyName == Constants.AllPartyName && party.type == Constants.LENDIN || party.partyName == Constants.AllPartyName && party.type == Constants.LENDOUT) {
                                        TransactionCard(
                                            transactionEntity = party,
                                            onClick = { /*TODO*/ })
                                    }
                                }
                            }
                        } else if (viewModel.selectedType.value == 0) {
                            LazyColumn() {
                                itemsIndexed(
                                    items = viewModel.transcations.value
                                ) { index, party ->
                                    if (party.partyName == Constants.AllPartyName && party.type == Constants.PartyType || party.partyName == Constants.AllPartyName && party.type == Constants.LENDIN
                                        ||  party.partyName == Constants.AllPartyName && party.type == Constants.LENDOUT) {
                                        TransactionCard(
                                            transactionEntity = party,
                                            onClick = { /*TODO*/ })
                                    }
                                }
                            }
                            Constants.existPartyName =  viewModel.transcations.value
                        } else if (viewModel.selectedType.value == 1) {
                            LazyColumn() {
                                itemsIndexed(
                                    items = viewModel.transcations.value
                                )
                                { index, transaction ->

                                    if (viewModel.searchQuery.value.isEmpty() && (transaction.partyName == Constants.AllPartyName && transaction.type == Constants.PartyType)) {
                                        TransactionCard(
                                            transactionEntity = transaction,
                                            onClick = { /*TODO*/ })
                                    } else if (transaction.partyName!!.contains(viewModel.searchQuery.value) && (transaction.partyName == Constants.AllPartyName && transaction.type == Constants.PartyType)){
                                        TransactionCard(
                                            transactionEntity = transaction,
                                            onClick = { /*TODO*/ })
                                    }

                                }
                            }
                        }
                    }
                }
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(),
                    verticalArrangement = Arrangement.Bottom
                ) {
                    BottomButtons()
                    Spacer(modifier = Modifier.size(10.dp))
                }
            }
        }

    }

    @Composable
    fun Selector(selected: Int, onClick: (Int) -> Unit) {

        if (Constants.PartyType == Constants.SALE) {
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.fillMaxWidth()
            ) {
                SelectorPartiesCards(selected == 0, "All", 0, onClick = onClick)
                SelectorPartiesCards(selected == 1, "Sales", 1, onClick = onClick)
                SelectorPartiesCards(selected == 2, "Lends", 2, onClick = onClick)
            }
        } else if (Constants.PartyType == Constants.PURCHASE){
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.fillMaxWidth()
            ) {
                SelectorPartiesCards(selected == 0, "All", 0, onClick = onClick)
                SelectorPartiesCards(selected == 1, "Purchase", 1, onClick = onClick)
                SelectorPartiesCards(selected == 2, "Lends", 2, onClick = onClick)
            }
        } else{
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.fillMaxWidth()
            ) {
                SelectorPartiesCards(selected == 0, "All", 0, onClick = onClick)
                SelectorPartiesCards(selected == 1, "Opening Balance", 1, onClick = onClick)
                SelectorPartiesCards(selected == 2, "Lends", 2, onClick = onClick)
            }
        }

    }

    @Composable
    fun SelectorPartiesCards(isSelected: Boolean, name: String, nameCode: Int, onClick: (Int) -> Unit) {
        Row {
            Card(
                modifier = Modifier
                    .width(110.dp)
                    .height(35.dp)
                    .clickable { onClick(nameCode) },
                shape = RoundedCornerShape(17.dp),
                border = BorderStroke(
                    1.3.dp,
                    color = if (isSelected) Color(0xFFCE2848) else Color.LightGray
                ),
                backgroundColor = if (isSelected) Color(0xFFF9DCE1) else Color.White
            ) {

                Row(
                    modifier = Modifier
                        .fillMaxHeight()
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = name,
                        color = if (isSelected) Color(0xFFCE2848) else Color.LightGray,
                        fontSize = 12.sp,
                        fontFamily = robotoFamily,
                        fontWeight = FontWeight.Medium,
                    )
                }
            }
        }
    }

    @Composable
    fun SendReminderCard(name: String,contactNo:String,image:Int, sendReminder: String, type:String, amount:String, onClick: (String) -> Unit
    ) {
        Column {
            Card(
                modifier = Modifier
                    .height(120.dp)
                    .fillMaxWidth()
                    .border(width = 1.dp, color = Color.Gray, shape = RoundedCornerShape(15.dp))
                    .clickable { onClick(sendReminder) }) {
                Row(
                    modifier = Modifier
                        .fillMaxHeight()
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween

                ) {

                    Row {
                        Column(
                            modifier = Modifier.padding(start = 16.dp),
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text(
                                modifier = Modifier
                                    .padding(top = 5.dp)
                                    .fillMaxWidth(0.9f),
                                text = name,
                                fontSize = 16.sp,
                                fontFamily = robotoFamily,
                                fontWeight = FontWeight.Medium
                            )
                            Text(
                                modifier = Modifier
                                    .padding(top = 1.dp)
                                    .fillMaxWidth(0.9f),
                                text = contactNo,
                                fontSize = 15.sp,
                                fontFamily = robotoFamily,
                                fontWeight = FontWeight.Medium
                            )
                        }

                        Spacer(modifier = Modifier.size(15.dp))

                        Column(
                            modifier = Modifier.padding(start = 20.dp),
                            verticalArrangement = Arrangement.Center
                        ) {
                            Image(
                                modifier = Modifier
                                    .padding(top = 40.dp, start = 37.dp)
                                    .height(35.dp)
                                    .width(50.dp),
                                painter = painterResource(id = R.drawable.baseline_doorbell_24),
                                contentDescription = null,
                                contentScale = ContentScale.Fit
                            )
                            Text(
                                modifier = Modifier
                                    .padding(top = 1.dp)
                                    .fillMaxWidth(0.9f),
                                text = sendReminder,
                                fontSize = 20.sp,
                                fontFamily = robotoFamily,
                                fontWeight = FontWeight.Normal,
                                color = Color(0xFFF51C35)
                            )
                        }

                        Spacer(modifier = Modifier.size(15.dp))

                        Column(
                            modifier = Modifier.padding(start = 10.dp),
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text(
                                modifier = Modifier
                                    .padding(top = 5.dp, end = 8.dp)
                                    .fillMaxWidth(0.9f),
                                text = type,
                                fontSize = 16.sp,
                                fontFamily = robotoFamily,
                                fontWeight = FontWeight.Medium,
                                color = if (Constants.PartyType == Constants.SALE || Constants.PartyType == "Receive") Color(0xFF26B180) else Color(0xFFE36B4E)
                            )
                            Text(
                                modifier = Modifier
                                    .padding(top = 1.dp)
                                    .fillMaxWidth(0.9f),
                                text = amount,
                                fontSize = 16.sp,
                                fontFamily = robotoFamily,
                                fontWeight = FontWeight.Medium,
                                color = if (Constants.PartyType == Constants.SALE || Constants.PartyType == "Receive") Color(0xFF26B180) else Color(0xFFE36B4E)
                            )
                        }
                    }
                }
            }
        }

    }

    @Composable
    fun BottomButtons(
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly

        ) {
            if (Constants.PartyType == Constants.SALE || Constants.PartyType == "Receive"){
            PaymentButton(Color(0xFF0174E7), "Receive", onClick = { showReceiveDialog() })
            }else{
                PaymentButton(Color(0xFF0174E7), "Pay", onClick = { showPayDialog() })
            }
        }
    }

    private fun showReceiveDialog() {
        val dialogView = layoutInflater.inflate(R.layout.receive_payment, activity?.findViewById(R.id.topsheetContainer))
        val dialog = Dialog(requireContext())
        dialog.setContentView(dialogView)

        val layoutParams = WindowManager.LayoutParams()
        layoutParams.copyFrom(dialog.window?.attributes)
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT
        layoutParams.gravity = Gravity.TOP

        // Set desired margin from the top (in pixels)
        val marginTopInPixels = resources.getDimensionPixelSize(R.dimen.dp_50)
        layoutParams.y = marginTopInPixels

        dialog.window?.attributes = layoutParams
        dialog.show()

        val btnSale = dialogView?.findViewById<Button>(R.id.btnBillPay)
        val btnLendMoney = dialogView?.findViewById<Button>(R.id.btnLendMoneyPay)
        val etPayInvoiceNo = dialogView?.findViewById<EditText>(R.id.etPayInvoiceNo)

//        btnSale?.setOnClickListener {
//            btnLendMoney?.backgroundTintList = ColorStateList.valueOf(0xFF3076BD.toInt())
//        }

        btnLendMoney?.setOnClickListener {
            val colorBlue = getColor(requireContext(), R.color.app_bar_color)
            btnLendMoney.backgroundTintList = ColorStateList.valueOf(colorBlue)
            btnSale?.backgroundTintList = ColorStateList.valueOf(0xFF3076BD.toInt())
            etPayInvoiceNo?.visibility = View.GONE
        }

    }

    private fun showPayDialog() {
        val dialogView = layoutInflater.inflate(R.layout.pay_dialog, activity?.findViewById(R.id.topsheetContainer))
        val dialog = Dialog(requireContext())
        dialog.setContentView(dialogView)

        val layoutParams = WindowManager.LayoutParams()
        layoutParams.copyFrom(dialog.window?.attributes)
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT
        layoutParams.gravity = Gravity.TOP

        // Set desired margin from the top (in pixels)
        val marginTopInPixels = resources.getDimensionPixelSize(R.dimen.dp_50)
        layoutParams.y = marginTopInPixels

        dialog.window?.attributes = layoutParams
        dialog.show()

    }

}