package com.example.vyaperclone

class Constants {
    companion object {
        const val PURCHASE = "purchase"
        const val SALE = "sale"
        const val SALEINT = 6
        const val YOUWILLGETINT = 7
        const val YOUWILLGIVEINT = 8
        const val PURCHASEINT = 9
        const val PRODUCT = "product"
        const val SERVICE = "service"
        var PartyName = ""
        var UpdatedPartyName = ""
        var BillNo = 0
        var TotalAmt = 0
        var PaidAmt = 0
        var Received = 0
        var itemsName = ""
        var quantity= "0"
        var rate= "0"
        var existPartyName: List<PartyEntity> = emptyList()
        var partyDetails: List<TransactionEntity> = emptyList()
        var receiveAmount= 0
    }
}