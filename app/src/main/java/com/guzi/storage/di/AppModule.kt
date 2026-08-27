package com.guzi.storage.di

import android.content.Context
import androidx.room.Room
import com.guzi.storage.data.local.AppDatabase
import com.guzi.storage.data.local.dao.ItemDao
import com.guzi.storage.data.local.dao.PresetOptionDao
import com.guzi.storage.data.local.dao.TransactionDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Hilt 依赖注入模块 - 提供数据库和 DAO 实例
 */
@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "guzi_storage.db"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun provideItemDao(database: AppDatabase): ItemDao {
        return database.itemDao()
    }

    @Provides
    fun provideTransactionDao(database: AppDatabase): TransactionDao {
        return database.transactionDao()
    }

    @Provides
    fun providePresetOptionDao(database: AppDatabase): PresetOptionDao {
        return database.presetOptionDao()
    }
}
