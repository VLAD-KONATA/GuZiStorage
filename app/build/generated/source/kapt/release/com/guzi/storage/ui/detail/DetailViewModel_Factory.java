package com.guzi.storage.ui.detail;

import com.guzi.storage.data.repository.ItemRepository;
import com.guzi.storage.data.repository.TransactionRepository;
import com.guzi.storage.util.ImageExportManager;
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

  private final Provider<ImageExportManager> imageExportManagerProvider;

  public DetailViewModel_Factory(Provider<ItemRepository> itemRepositoryProvider,
      Provider<TransactionRepository> transactionRepositoryProvider,
      Provider<ImageExportManager> imageExportManagerProvider) {
    this.itemRepositoryProvider = itemRepositoryProvider;
    this.transactionRepositoryProvider = transactionRepositoryProvider;
    this.imageExportManagerProvider = imageExportManagerProvider;
  }

  @Override
  public DetailViewModel get() {
    return newInstance(itemRepositoryProvider.get(), transactionRepositoryProvider.get(), imageExportManagerProvider.get());
  }

  public static DetailViewModel_Factory create(Provider<ItemRepository> itemRepositoryProvider,
      Provider<TransactionRepository> transactionRepositoryProvider,
      Provider<ImageExportManager> imageExportManagerProvider) {
    return new DetailViewModel_Factory(itemRepositoryProvider, transactionRepositoryProvider, imageExportManagerProvider);
  }

  public static DetailViewModel newInstance(ItemRepository itemRepository,
      TransactionRepository transactionRepository, ImageExportManager imageExportManager) {
    return new DetailViewModel(itemRepository, transactionRepository, imageExportManager);
  }
}
