package com.example.financialaccounting.data.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.financialaccounting.data.Transaction
import com.example.financialaccounting.data.TransactionType
import com.example.financialaccounting.data.dao.TransactionDao
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TransactionViewModel @Inject constructor(
    private val transactionDao: TransactionDao
) : ViewModel() {
    private val _transactions = MutableStateFlow<List<Transaction>>(emptyList())
    val transaction: StateFlow<List<Transaction>> = _transactions

    private val _totalIncome = MutableStateFlow(0.0)
    val totalIncome: StateFlow<Double> = _totalIncome

    init {
        viewModelScope.launch {
            transactionDao.getAllTransactions().collect {
                _transactions.value = it
            }
        }
        updateTotals()
    }

    private fun updateTotals() {
        viewModelScope.launch {
            transactionDao.getTotalByType(TransactionType.INCOME).collect {
                _totalIncome.value = it ?: 0.0
            }
        }
        viewModelScope.launch {
            transactionDao.getTotalByType(TransactionType.EXPENSE).collect {
                _totalIncome.value = it ?: 0.0
            }
        }
    }

    fun addTransaction(
        amount: Double,
        description: String,
        category: String,
        type: TransactionType
    ) {
        viewModelScope.launch {
            val transaction = Transaction(
                amount = amount,
                description = description,
                category = category,
                type = type,
                date = Date()
            )
            transactionDao.insertTransaction(transaction)
        }
    }

    fun deleteTransaction(transaction: Transaction) {
        viewModelScope.launch {
            transactionDao.deleteTransaction(transaction)
        }
    }

}