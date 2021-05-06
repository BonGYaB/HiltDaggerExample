package com.example.examplehilt.data.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.example.examplehilt.database.ReceiptDatabase
import com.example.examplehilt.database.dao.ReceiptHistoryDao
import com.example.examplehilt.database.entity.ReceiptHistory
import com.example.examplehilt.getOrAwaitValue
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class ReceiptHistoryDaoTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var database: ReceiptDatabase
    private lateinit var dao: ReceiptHistoryDao

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
                ApplicationProvider.getApplicationContext(),
                ReceiptDatabase::class.java
        ).allowMainThreadQueries().build()
        dao = database.receiptHistory()
    }

    @After
    fun teardown() {
        database.close()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun insertReceiptItem() = runBlockingTest {
        val receiptItem = ReceiptHistory(0, "100", "10", "10", "12312hfjkashfkasdfgasfsfasdfasdfas", "020104444")
        dao.addReceiptHistory(receiptItem)

        val allReceiptItems = dao.readAllData().getOrAwaitValue()

        assertThat(allReceiptItems).contains(receiptItem)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun deleteReceiptItem() = runBlockingTest {
        val receiptItem = ReceiptHistory(0, "100", "10", "10", "12312hfjkashfkasdfgasfsfasdfasdfas", "020104444")
        dao.addReceiptHistory(receiptItem)
        dao.deleteReceipt(receiptItem)

        val allReceiptItems = dao.readAllData().getOrAwaitValue()

        assertThat(allReceiptItems).doesNotContain(receiptItem)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun observeTotalPriceSum() = runBlockingTest {
        val receiptItem1 = ReceiptHistory(0, "1", "1", "1", "1231231", "00022828")
        val receiptItem2 = ReceiptHistory(0, "2", "2", "2", "1231231", "00022828")
        val receiptItem3 = ReceiptHistory(0, "3", "3", "3", "1231231", "00022828")

        dao.addReceiptHistory(receiptItem1)
        dao.addReceiptHistory(receiptItem2)
        dao.addReceiptHistory(receiptItem3)

        val total = dao.readAllData().getOrAwaitValue()

        assertThat(total).isEqualTo("6")
    }


}