package com.example.examplehilt.repository

import androidx.lifecycle.LiveData
import com.example.examplehilt.database.dao.ReceiptHistoryDao
import com.example.examplehilt.database.entity.ReceiptHistory
import kotlinx.coroutines.coroutineScope
import javax.inject.Inject

interface ReceiptRepository {
    val readAllData: LiveData<List<ReceiptHistory>>

    suspend fun addReceipt(receipt: ReceiptHistory)

    suspend fun updateReceipt(receipt: ReceiptHistory)

    suspend fun deleteReceipt(receipt: ReceiptHistory)

    suspend fun deleteAllData()
}