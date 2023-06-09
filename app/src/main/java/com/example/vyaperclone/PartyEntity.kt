package com.example.vyaperclone

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "partyTable")
data class PartyEntity(
    @ColumnInfo(name = "partyName") var partyName: String?,
    @ColumnInfo(name = "partyContactNumber") var partyContactNumber: String?,
    @ColumnInfo(name = "partyBillingAddress") var partyBillingAddress: String?,
    @ColumnInfo(name = "amount") var amout: Long?,
    @ColumnInfo(name = "partyType") var partyType: String?,

    ) {
    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null
}