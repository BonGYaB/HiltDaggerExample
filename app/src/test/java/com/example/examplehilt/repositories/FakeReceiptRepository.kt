package com.example.examplehilt.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.examplehilt.database.dao.ReceiptHistoryDao
import com.example.examplehilt.database.entity.ReceiptHistory
import com.example.examplehilt.repository.DefaultRepository
import com.example.examplehilt.repository.ReceiptRepository

class FakeReceiptRepository : ReceiptRepository {

    private val receiptItems = mutableListOf<ReceiptHistory>()

    private val observableReceiptHistory = MutableLiveData<List<ReceiptHistory>>(receiptItems)
    private val observableTotalAmount = MutableLiveData<Float>()

    private var shouldReturnNetworkError = false

    fun setShouldReturnNetworkError(statusNetwork: Boolean) {
        shouldReturnNetworkError = statusNetwork
    }

    private fun refreshLiveData() {
        observableReceiptHistory.postValue(receiptItems)
        observableTotalAmount.postValue(getTotalAmount())
    }

    private fun getTotalAmount(): Float {
        return receiptItems.sumByDouble { it.amount.toDouble() }.toFloat()
    }

    override val readAllData: LiveData<List<ReceiptHistory>> = observableReceiptHistory

    override suspend fun addReceipt(receipt: ReceiptHistory) {
        receiptItems.add(receipt)
        refreshLiveData()
    }

    override suspend fun deleteReceipt(receipt: ReceiptHistory) {
        receiptItems.remove(receipt)
        refreshLiveData()
    }

    override suspend fun updateReceipt(receipt: ReceiptHistory) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteAllData() {
        receiptItems.removeAll(receiptItems)
    }

}