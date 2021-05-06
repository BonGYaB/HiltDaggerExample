package com.example.examplehilt.database.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "receipt_history")
data class ReceiptHistory(
        @PrimaryKey(autoGenerate = true)
        val id: Int,
        val amount: String,
        val dishs: String,
        val tip: String,
        val photo: String,
        val timestamp: String
) : Parcelable