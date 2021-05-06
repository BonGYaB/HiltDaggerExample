package com.example.examplehilt.di

import android.content.Context
import com.example.examplehilt.database.ReceiptDatabase
import com.example.examplehilt.database.dao.ReceiptHistoryDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {


//    @Singleton
//    @Provides
//    fun provideTipHistoryDao(
//        tipDatabase: TipDatabase
//    ): TipHistoryDao {
//       return TipDatabase.getDatabase().tipHistory()
//    }

    @Singleton
    @Provides
    fun providesTipDatabase(
        @ApplicationContext context: Context
    ): ReceiptHistoryDao {
        return ReceiptDatabase.getDatabase(context).receiptHistory()
    }
}