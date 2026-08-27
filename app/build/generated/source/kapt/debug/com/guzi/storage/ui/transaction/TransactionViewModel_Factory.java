package com.guzi.storage.ui.transaction;

import com.guzi.storage.data.repository.ItemRepository;
import com.guzi.storage.data.repository.TransactionRepository;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata
@QualifierMetadata
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
public final class TransactionViewModel_Factory implements Factory<TransactionViewModel> {
  private final Provider<TransactionRepository> transactionRepositoryProvider;

  private final Provider<ItemRepository> itemRepositoryProvider;

  public TransactionViewModel_Factory(Provider<TransactionRepository> transactionRepositoryProvider,
      Provider<ItemRepository> itemRepositoryProvider) {
    this.transactionRepositoryProvider = transactionRepositoryProvider;
    this.itemRepositoryProvider = itemRepositoryProvider;
  }

  @Override
  public TransactionViewModel get() {
    return newInstance(transactionRepositoryProvider.get(), itemRepositoryProvider.get());
  }

  public static TransactionViewModel_Factory create(
      Provider<TransactionRepository> transactionRepositoryProvider,
      Provider<ItemRepository> itemRepositoryProvider) {
    return new TransactionViewModel_Factory(transactionRepositoryProvider, itemRepositoryProvider);
  }

  public static TransactionViewModel newInstance(TransactionRepository transactionRepository,
      ItemRepository itemRepository) {
    return new TransactionViewModel(transactionRepository, itemRepository);
  }
}
