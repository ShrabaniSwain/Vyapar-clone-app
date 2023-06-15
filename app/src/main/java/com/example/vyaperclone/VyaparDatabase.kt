package com.example.vyaperclone

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.work.impl.WorkDatabaseMigrations.MIGRATION_1_2

@Database(
    entities = [ItemsEntity::class, PartyEntity::class, TransactionEntity::class, ExpenseEntity::class, AddItems::class],
<<<<<<< HEAD
    version = 4
=======
    version = 3
>>>>>>> 52c58d9be6d2a9260d7b29b555cb0f9164f3ff8f
)
abstract class VyaparDatabase : RoomDatabase() {
    abstract fun getDAO(): VyaparDAO
}