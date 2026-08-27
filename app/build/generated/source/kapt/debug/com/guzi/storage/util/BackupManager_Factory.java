package com.guzi.storage.util;

import android.content.Context;
import com.guzi.storage.data.repository.ItemRepository;
import com.guzi.storage.data.repository.TransactionRepository;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata("javax.inject.Singleton")
@QualifierMetadata("dagger.hilt.android.qualifiers.ApplicationContext")
@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes",
    "KotlinInternal",
    "KotlinInternalInJava",
    "cast",
    "deprecation"
})
public final class BackupManager_Factory implements Factory<BackupManager> {
  private final Provider<Context> contextProvider;

  private final Provider<ItemRepository> itemRepositoryProvider;

  private final Provider<TransactionRepository> transactionRepositoryProvider;

  public BackupManager_Factory(Provider<Context> contextProvider,
      Provider<ItemRepository> itemRepositoryProvider,
      Provider<TransactionRepository> transactionRepositoryProvider) {
    this.contextProvider = contextProvider;
    this.itemRepositoryProvider = itemRepositoryProvider;
    this.transactionRepositoryProvider = transactionRepositoryProvider;
  }

  @Override
  public BackupManager get() {
    return newInstance(contextProvider.get(), itemRepositoryProvider.get(), transactionRepositoryProvider.get());
  }

  public static BackupManager_Factory create(Provider<Context> contextProvider,
      Provider<ItemRepository> itemRepositoryProvider,
      Provider<TransactionRepository> transactionRepositoryProvider) {
    return new BackupManager_Factory(contextProvider, itemRepositoryProvider, transactionRepositoryProvider);
  }

  public static BackupManager newInstance(Context context, ItemRepository itemRepository,
      TransactionRepository transactionRepository) {
    return new BackupManager(context, itemRepository, transactionRepository);
  }
}
