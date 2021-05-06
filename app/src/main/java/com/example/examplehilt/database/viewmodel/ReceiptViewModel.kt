package com.example.examplehilt.database.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.examplehilt.database.Resource
import com.example.examplehilt.database.entity.ReceiptHistory
import com.example.examplehilt.helper.Event
import com.example.examplehilt.repository.DefaultRepository
import com.example.examplehilt.repository.ReceiptRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReceiptViewModel @Inject constructor(
    private val repository: DefaultRepository
    ): ViewModel() {

    private val _insertReceiptItemStatus = MutableLiveData<Event<Resource<ReceiptHistory>>>()
    val insertReceiptItemStatus: LiveData<Event<Resource<ReceiptHistory>>> = _insertReceiptItemStatus

    fun getInstance() : String {
        return this.toString()
    }

    fun readAllData(): LiveData<List<ReceiptHistory>> {
        return repository.readAllData
    }

    fun addReceipt(receipt: ReceiptHistory) {
        viewModelScope.launch {
            repository.addReceipt(receipt)
        }
    }

    fun updateReceipt(receipt: ReceiptHistory) {
        viewModelScope.launch(Dispatchers.IO){
            repository.updateReceipt(receipt)
        }
    }

    fun deleteReceipt(receipt: ReceiptHistory) {
        viewModelScope.launch(Dispatchers.IO){
            repository.deleteReceipt(receipt)
        }
    }

    fun deleteAllReceipts() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAllData()
        }
    }
}