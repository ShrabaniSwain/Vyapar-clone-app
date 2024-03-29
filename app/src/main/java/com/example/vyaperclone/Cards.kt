package com.example.vyaperclone

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.lang.Math.abs

val robotoFamily = FontFamily(
    Font(R.font.robotomedium, FontWeight.Medium),
    Font(R.font.roboto, FontWeight.Normal)
)

@Composable
fun ItemCard(itemEntity: ItemsEntity, onClick: (ItemsEntity) -> Unit) {
    Column {


        Card(
            modifier = Modifier
                .height(100.dp)
                .fillMaxWidth()
                .clickable { onClick(itemEntity) },
        ) {
            Column(modifier = Modifier.padding(start = 10.dp)) {

                Row(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        modifier = Modifier
                            .padding(top = 10.dp)
                            .fillMaxWidth(0.9f),
                        text = itemEntity.name!!,
                        fontSize = 15.sp,
                        fontFamily = robotoFamily,
                        fontWeight = FontWeight.Medium
                    )
                    Image(
                        modifier = Modifier
                            .padding(top = 10.dp, end = 10.dp)
                            .height(15.dp)
                            .width(15.dp),
                        painter = painterResource(id = R.drawable.ic_share),
                        contentDescription = null,
                        contentScale = ContentScale.Fit
                    )
                }


                Row(
                    modifier = Modifier
                        .fillMaxHeight()
                        .fillMaxWidth(0.73f),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxHeight(),
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "Sale price",
                            fontSize = 10.sp,
                            fontFamily = robotoFamily,
                            fontWeight = FontWeight.Medium,
                            color = Color.Gray

                        )
                        Spacer(modifier = Modifier.size(5.dp))
                        Text(
                            text = "₹ "+itemEntity.salePrice.toString(),
                            fontSize = 15.sp,
                            fontFamily = robotoFamily,
                            fontWeight = FontWeight.Normal,
                        )
                    }
                    Column(
                        modifier = Modifier
                            .fillMaxHeight(),
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "Purchase price",
                            fontSize = 10.sp,
                            fontFamily = robotoFamily,
                            fontWeight = FontWeight.Medium,
                            color = Color.Gray
                        )
                        Spacer(modifier = Modifier.size(5.dp))
                        Text(
                            text = "₹ "+itemEntity.purchasePrice.toString(),
                            fontSize = 15.sp,
                            fontFamily = robotoFamily,
                            fontWeight = FontWeight.Normal,
                        )
                    }
                    Column(
                        modifier = Modifier
                            .fillMaxHeight(),
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "In Stock",
                            fontSize = 12.sp,
                            fontFamily = robotoFamily,
                            fontWeight = FontWeight.Medium,
                            color = Color.Gray
                        )
                        Spacer(modifier = Modifier.size(5.dp))
                        Text(
                            text = "₹ "+itemEntity.stock.toString(),
                            fontSize = 15.sp,
                            fontFamily = robotoFamily,
                            fontWeight = FontWeight.Normal
                        )
                    }

                }
            }
        }

        Divider(color = Color.LightGray, thickness = 1.dp, startIndent = 10.dp)
    }
}

@Composable
fun TransactionCard(
    transactionEntity: TransactionEntity, onClick: (TransactionEntity) -> Unit
) {
    Column {
        Card(
            modifier = Modifier
                .height(120.dp)
                .fillMaxWidth()
                .clickable { onClick(transactionEntity) },
        ) {
            Column(modifier = Modifier.padding(start = 16.dp)) {
                Text(
                    modifier = Modifier
                        .padding(top = 10.dp)
                        .fillMaxWidth(0.9f),
                    text = transactionEntity.partyName!!,
                    fontSize = 15.sp,
                    fontFamily = robotoFamily,
                    fontWeight = FontWeight.Medium
                )
                Text(
                    modifier = Modifier
                        .padding(top = 1.dp)
                        .fillMaxWidth(0.9f),
                    text = transactionEntity.partyBillingDate!!,
                    fontSize = 12.sp,
                    fontFamily = robotoFamily,
                    fontWeight = FontWeight.Medium,
                    color = Color.Gray
                )
                //0xFF
                //0xFFD1F2E7
                Spacer(modifier = Modifier.size(5.dp))
                Surface(
                    modifier = Modifier.height(20.dp),
                    shape = MaterialTheme.shapes.small,
                    color = if (transactionEntity.type == "purchase" || transactionEntity.type == Constants.LENDIN || transactionEntity.type == "Pay") Color(0xFFFEE4DC) else Color( 0xFFD1F2E7),

                    ) {
                    Column(
                        verticalArrangement = Arrangement.Center
                    ) {
                        if (transactionEntity.type == "purchase") {
                            Text(
                                text = "PURCHASE",
                                modifier = Modifier.padding(start=5.dp,end =5.dp),
                                color = Color(0xFFE36B4E),
                                fontSize = 12.sp
                            )
                        } else if (transactionEntity.type == Constants.SALE){
                            Text(
                                text = "SALE",
                                modifier = Modifier.padding(start=5.dp,end =5.dp),
                                color = Color(0xFF26B180),
                                fontSize = 12.sp
                            )
                        }else if (transactionEntity.type == Constants.LENDIN){
                            Text(
                                text = "LEND (IN)",
                                modifier = Modifier.padding(start=5.dp,end =5.dp),
                                color = Color(0xFFE36B4E),
                                fontSize = 12.sp
                            )
                        }else if (transactionEntity.type == Constants.LENDOUT){
                            Text(
                                text = "LEND (OUT)",
                                modifier = Modifier.padding(start=5.dp,end =5.dp),
                                color = Color(0xFF26B180),
                                fontSize = 12.sp
                            )
                        }else if (transactionEntity.type == "Receive"){
                            Text(
                                text = "Opening Balance (Receivable)",
                                modifier = Modifier.padding(start=5.dp,end =5.dp),
                                color = Color(0xFF26B180),
                                fontSize = 12.sp
                            )
                        }else if(transactionEntity.type == "Pay"){
                            Text(
                                text = "Opening Balance (Payable)",
                                modifier = Modifier.padding(start=5.dp,end =5.dp),
                                color = Color(0xFFE36B4E),
                                fontSize = 12.sp
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.size(1.dp))


                Row(
                    modifier = Modifier
                        .fillMaxHeight()
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween

                ) {

                    Row {
                        Column(
                            modifier = Modifier
                                .fillMaxHeight(),
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = "Total",
                                fontSize = 10.sp,
                                fontFamily = robotoFamily,
                                fontWeight = FontWeight.Medium,
                                color = Color.Gray
                            )
                            Spacer(modifier = Modifier.size(1.dp))
                            Text(
                                text = "₹ " + transactionEntity.total.toString(),
                                fontSize = 15.sp,
                                fontFamily = robotoFamily,
                                fontWeight = FontWeight.Normal
                            )
                        }

                        Spacer(modifier = Modifier.size(50.dp))

                        Column(
                            modifier = Modifier
                                .fillMaxHeight(),
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = "Balance",
                                fontSize = 10.sp,
                                fontFamily = robotoFamily,
                                fontWeight = FontWeight.Medium,
                                color = Color.Gray
                            )
                            Spacer(modifier = Modifier.size(1.dp))
                            Text(
                                text = "₹ " + ((transactionEntity.total ?: 0) - getTheBigger(transactionEntity.received ?: 0, transactionEntity.paidAmt ?: 0)).toString(),
                                fontSize = 15.sp,
                                fontFamily = robotoFamily,
                                fontWeight = FontWeight.Normal
                            )
                        }
                    }
                    Row {
                        Image(
                            modifier = Modifier
                                .padding(top = 7.dp, end = 10.dp)
                                .height(20.dp)
                                .width(20.dp),
                            painter = painterResource(id = R.drawable.ic_print),
                            contentDescription = null,
                            contentScale = ContentScale.Fit
                        )
                        Image(
                            modifier = Modifier
                                .padding(top = 5.dp, end = 10.dp)
                                .height(20.dp)
                                .width(20.dp),
                            painter = painterResource(id = R.drawable.ic_share),
                            contentDescription = null,
                            contentScale = ContentScale.Fit
                        )
                        Image(
                            modifier = Modifier
                                .padding(top = 5.dp, end = 10.dp)
                                .height(20.dp)
                                .width(20.dp),
                            painter = painterResource(id = R.drawable.ic_more),
                            contentDescription = null,
                            contentScale = ContentScale.Fit
                        )
                    }
                }

            }
        }
        Divider(color = Color.LightGray, thickness = 1.dp)

    }

}

fun getTheBigger(received: Long, paidAmt: Long): Long {
    return Math.max(paidAmt,received)
}

@Composable
fun PartiesCard(partyEntity: TransactionEntity, onClick: (TransactionEntity) -> Unit) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Card(
            modifier = Modifier
                .height(75.dp)
                .fillMaxWidth()
                .clickable { onClick(partyEntity) },
        ) {
            Column(modifier = Modifier.padding(17.dp)) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Text(
                            text = partyEntity.partyName!!,
                            fontSize = 16.sp,
                            fontFamily = robotoFamily,
                            fontWeight = FontWeight.Medium
                        )
                        Spacer(modifier = Modifier.size(1.dp))
                        Text(
                            text = partyEntity.partyBillingDate!!, // Replace with your desired text
                            fontSize = 13.sp,
                            fontFamily = robotoFamily,
                            fontWeight = FontWeight.Normal,
                            color = Color.Gray // Customize the color as needed
                        )
                    }
                    Column(
                        modifier = Modifier
                            .fillMaxHeight(),
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "₹ " + ((partyEntity.total ?: 0) - getTheBigger(partyEntity.received ?: 0, partyEntity.paidAmt ?: 0)).toString(),
                            fontSize = 17.sp,
                            fontFamily = robotoFamily,
                            fontWeight = FontWeight.Medium,
                            color = if (partyEntity.type == "Receive" || partyEntity.type == Constants.SALE) Color(0xFF26B180) else Color(0xFFE36B4E)
                        )
                        Spacer(modifier = Modifier.size(1.dp))
                        Text(
                            text = if (partyEntity.type == "Receive" || partyEntity.type == Constants.SALE) "You'll Get" else "You'll Give",
                            fontSize = 13.sp,
                            fontFamily = robotoFamily,
                            fontWeight = FontWeight.Normal,
                            color = if (partyEntity.type == "Receive" || partyEntity.type == Constants.SALE) Color(0xFF26B180) else Color(0xFFE36B4E)
                        )
                    }
                }
            }
        }
        Divider(color = Color.LightGray, thickness = 1.dp)
    }
}



@Preview(showBackground = true)
@Composable
fun Preview() {
    Column {

        TransactionCard(transactionEntity =
        TransactionEntity(
            54,
            Constants.PURCHASE,
            "batman",
            "cola,car",
            "100",
            "10,15",
            1000,
            500,
            1000,
            "8917281008",
            "Odisha","date"
        ), onClick = { /*TODO*/ })
        TransactionCard(transactionEntity =
        TransactionEntity(
            54,
            Constants.SALE,
            "batman",
            "cola,car",
            "100",
            "10,15",
            1000,
            500,
            1000,
            "8917281008",
            "Odisha","date"
        ), onClick = { /*TODO*/ })

    }
}