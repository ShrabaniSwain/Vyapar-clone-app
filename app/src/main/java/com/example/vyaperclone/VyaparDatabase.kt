package com.example.vyaperclone

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [ItemsEntity::class, PartyEntity::class, TransactionEntity::class, ExpenseEntity::class],
    version = 1
)
abstract class VyaparDatabase : RoomDatabase() {
    abstract fun getDAO(): VyaparDAO
}