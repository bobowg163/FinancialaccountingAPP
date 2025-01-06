package com.example.financialaccounting.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "transaction")
data class Transaction(
    @PrimaryKey(autoGenerate = true)
    val id:Long = 0,
    val amount:Double,
    val description:String,
    val category:String,
    val type:TransactionType,
    val date:Date
)

enum class TransactionType{
    INCOME,EXPENSE
}
