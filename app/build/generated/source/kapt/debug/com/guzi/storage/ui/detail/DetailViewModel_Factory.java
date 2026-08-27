package com.guzi.storage.ui.detail;

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
public final class DetailViewModel_Factory implements Factory<DetailViewModel> {
  private final Provider<ItemRepository> itemRepositoryProvider;

  private final Provider<TransactionRepository> transactionRepositoryProvider;

  public DetailViewModel_Factory(Provider<ItemRepository> itemRepositoryProvider,
      Provider<TransactionRepository> transactionRepositoryProvider) {
    this.itemRepositoryProvider = itemRepositoryProvider;
    this.transactionRepositoryProvider = transactionRepositoryProvider;
  }

  @Override
  public DetailViewModel get() {
    return newInstance(itemRepositoryProvider.get(), transactionRepositoryProvider.get());
  }

  public static DetailViewModel_Factory create(Provider<ItemRepository> itemRepositoryProvider,
      Provider<TransactionRepository> transactionRepositoryProvider) {
    return new DetailViewModel_Factory(itemRepositoryProvider, transactionRepositoryProvider);
  }

  public static DetailViewModel newInstance(ItemRepository itemRepository,
      TransactionRepository transactionRepository) {
    return new DetailViewModel(itemRepository, transactionRepository);
  }
}
