package com.example.examplehilt.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.examplehilt.MainCoroutineRule
import com.example.examplehilt.database.ReceiptDatabase
import com.example.examplehilt.database.Status
import com.example.examplehilt.database.dao.ReceiptHistoryDao
import com.example.examplehilt.database.entity.ReceiptHistory
import com.example.examplehilt.helper.Constants
import com.example.examplehilt.repositories.FakeReceiptRepository
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class ReceiptViewModelTest {
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var viewModel: FakeReceiptViewModel

    private lateinit var database: ReceiptDatabase
    private lateinit var dao: ReceiptHistoryDao

    @Before
    fun setup() {
        viewModel = FakeReceiptViewModel(FakeReceiptRepository())
    }

    @Test
    fun `insert receipt item with empty field, return error`() {
        viewModel.addReceipt(ReceiptHistory(0, "", "", "","", ""))

        val value = viewModel.insertReceiptItemStatus.getOrAwaitValueTest()

        assertThat(value.getContentIfNotHandled()?.status).isEqualTo(Status.ERROR)
    }

    @Test
    fun `insert receipt item with too long name, return error`() {
        val string = buildString {
            for (i in 1..Constants.MAX_NAME_LENGTH + 1) {
                append(1)
            }
        }
        viewModel.addReceipt(ReceiptHistory(0, string, string, string, string, string))

        val value = viewModel.insertReceiptItemStatus.getOrAwaitValueTest()

        assertThat(value.getContentIfNotHandled()?.status).isEqualTo(Status.ERROR)

    }
}
