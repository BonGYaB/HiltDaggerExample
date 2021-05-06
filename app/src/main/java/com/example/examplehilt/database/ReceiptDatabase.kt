package com.example.examplehilt.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.examplehilt.database.dao.ReceiptHistoryDao
import com.example.examplehilt.database.entity.ReceiptHistory

@Database(entities = [ReceiptHistory::class], version = 1, exportSchema = false)
abstract class ReceiptDatabase : RoomDatabase() {
    abstract fun receiptHistory(): ReceiptHistoryDao

    companion object {
        @Volatile
        private var INSTANCE: ReceiptDatabase? = null

        fun getDatabase(context: Context): ReceiptDatabase {
            val tempInstance = INSTANCE
            if(tempInstance != null) {
                return tempInstance
            }

            synchronized(this) {
                var instance = Room.databaseBuilder(
                    context.applicationContext,
                    ReceiptDatabase::class.java,
                    "receipt_history_database"
                ).fallbackToDestructiveMigration().build()

                INSTANCE = instance
                return instance
            }
        }
    }
}