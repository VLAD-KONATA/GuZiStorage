package com.guzi.storage.di;

import android.content.Context;
import androidx.room.Room;
import com.guzi.storage.data.local.AppDatabase;
import com.guzi.storage.data.local.dao.ItemDao;
import com.guzi.storage.data.local.dao.PresetOptionDao;
import com.guzi.storage.data.local.dao.TransactionDao;
import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;
import javax.inject.Singleton;

/**
 * Hilt 依赖注入模块 - 提供数据库和 DAO 实例
 */
@dagger.Module()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u00c7\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0012\u0010\u0003\u001a\u00020\u00042\b\b\u0001\u0010\u0005\u001a\u00020\u0006H\u0007J\u0010\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\u0004H\u0007J\u0010\u0010\n\u001a\u00020\u000b2\u0006\u0010\t\u001a\u00020\u0004H\u0007J\u0010\u0010\f\u001a\u00020\r2\u0006\u0010\t\u001a\u00020\u0004H\u0007\u00a8\u0006\u000e"}, d2 = {"Lcom/guzi/storage/di/AppModule;", "", "()V", "provideDatabase", "Lcom/guzi/storage/data/local/AppDatabase;", "context", "Landroid/content/Context;", "provideItemDao", "Lcom/guzi/storage/data/local/dao/ItemDao;", "database", "providePresetOptionDao", "Lcom/guzi/storage/data/local/dao/PresetOptionDao;", "provideTransactionDao", "Lcom/guzi/storage/data/local/dao/TransactionDao;", "app_debug"})
@dagger.hilt.InstallIn(value = {dagger.hilt.components.SingletonComponent.class})
public final class AppModule {
    @org.jetbrains.annotations.NotNull()
    public static final com.guzi.storage.di.AppModule INSTANCE = null;
    
    private AppModule() {
        super();
    }
    
    @dagger.Provides()
    @javax.inject.Singleton()
    @org.jetbrains.annotations.NotNull()
    public final com.guzi.storage.data.local.AppDatabase provideDatabase(@dagger.hilt.android.qualifiers.ApplicationContext()
    @org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        return null;
    }
    
    @dagger.Provides()
    @org.jetbrains.annotations.NotNull()
    public final com.guzi.storage.data.local.dao.ItemDao provideItemDao(@org.jetbrains.annotations.NotNull()
    com.guzi.storage.data.local.AppDatabase database) {
        return null;
    }
    
    @dagger.Provides()
    @org.jetbrains.annotations.NotNull()
    public final com.guzi.storage.data.local.dao.TransactionDao provideTransactionDao(@org.jetbrains.annotations.NotNull()
    com.guzi.storage.data.local.AppDatabase database) {
        return null;
    }
    
    @dagger.Provides()
    @org.jetbrains.annotations.NotNull()
    public final com.guzi.storage.data.local.dao.PresetOptionDao providePresetOptionDao(@org.jetbrains.annotations.NotNull()
    com.guzi.storage.data.local.AppDatabase database) {
        return null;
    }
}