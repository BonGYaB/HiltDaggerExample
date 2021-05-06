package com.example.examplehilt.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.examplehilt.database.entity.ReceiptHistory

@Dao
interface ReceiptHistoryDao {

    @Query("SELECT count(*) FROM receipt_history") // items is the table in the @Entity tag of ItemsYouAreStoringInDB.kt, id is a primary key which ensures each entry in DB is unique
    fun numberOfItemsInDB() : Int

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addReceiptHistory(tip: ReceiptHistory)

    @Update
    suspend fun updateUser(tip: ReceiptHistory)

    @Delete
    suspend fun deleteReceipt(tip: ReceiptHistory)

    @Query("DELETE FROM receipt_history")
    suspend fun deleteAllReceipt()

    @Query("SELECT * FROM receipt_history ORDER BY id ASC")
    fun readAllData(): LiveData<List<ReceiptHistory>>
}