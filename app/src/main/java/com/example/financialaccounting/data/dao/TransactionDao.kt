package com.example.financialaccounting.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.financialaccounting.data.Transaction
import com.example.financialaccounting.data.TransactionType
import kotlinx.coroutines.flow.Flow

@Dao
interface TransactionDao {
    @Query("SELECT * FROM `transaction` ORDER BY date DESC")
    fun getAllTransactions(): Flow<List<Transaction>>

    @Query("SELECT * FROM `transaction` WHERE type=:type ")
    fun getTransactionByType(type: TransactionType): Flow<List<Transaction>>

    @Query("SELECT sum(amount) From `transaction` WHERE type=:type")
    fun getTotalByType(type: TransactionType):Flow<Double?>

    @Insert
    suspend fun insertTransaction(transaction: Transaction)
    @Delete
    suspend fun deleteTransaction(transaction: Transaction)
}