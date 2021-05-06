package com.example.examplehilt.repository

import androidx.lifecycle.LiveData
import com.example.examplehilt.database.dao.ReceiptHistoryDao
import com.example.examplehilt.database.entity.ReceiptHistory
import javax.inject.Inject

open class DefaultRepository
    @Inject constructor(
            private val receiptHistoryDao: ReceiptHistoryDao
    ): ReceiptRepository {

    override val readAllData: LiveData<List<ReceiptHistory>> = receiptHistoryDao.readAllData()

    override suspend fun addReceipt(receipt: ReceiptHistory) {
        receiptHistoryDao.addReceiptHistory(receipt)
    }

    override suspend fun updateReceipt(receipt: ReceiptHistory) {
        receiptHistoryDao.updateUser(receipt)
    }

    override suspend fun deleteReceipt(receipt: ReceiptHistory) {
        receiptHistoryDao.deleteReceipt(receipt)
    }

    override suspend fun deleteAllData() {
        receiptHistoryDao.deleteAllReceipt()
    }
}