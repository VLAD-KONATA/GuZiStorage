package com.guzi.storage.ui.zipimport;

import android.content.Context;
import com.guzi.storage.data.repository.ItemRepository;
import com.guzi.storage.data.repository.PresetOptionRepository;
import com.guzi.storage.data.repository.TransactionRepository;
import com.guzi.storage.util.ImageFileManager;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata
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
public final class ZipImportViewModel_Factory implements Factory<ZipImportViewModel> {
  private final Provider<Context> contextProvider;

  private final Provider<ItemRepository> itemRepositoryProvider;

  private final Provider<TransactionRepository> transactionRepositoryProvider;

  private final Provider<PresetOptionRepository> presetOptionRepositoryProvider;

  private final Provider<ImageFileManager> imageFileManagerProvider;

  public ZipImportViewModel_Factory(Provider<Context> contextProvider,
      Provider<ItemRepository> itemRepositoryProvider,
      Provider<TransactionRepository> transactionRepositoryProvider,
      Provider<PresetOptionRepository> presetOptionRepositoryProvider,
      Provider<ImageFileManager> imageFileManagerProvider) {
    this.contextProvider = contextProvider;
    this.itemRepositoryProvider = itemRepositoryProvider;
    this.transactionRepositoryProvider = transactionRepositoryProvider;
    this.presetOptionRepositoryProvider = presetOptionRepositoryProvider;
    this.imageFileManagerProvider = imageFileManagerProvider;
  }

  @Override
  public ZipImportViewModel get() {
    return newInstance(contextProvider.get(), itemRepositoryProvider.get(), transactionRepositoryProvider.get(), presetOptionRepositoryProvider.get(), imageFileManagerProvider.get());
  }

  public static ZipImportViewModel_Factory create(Provider<Context> contextProvider,
      Provider<ItemRepository> itemRepositoryProvider,
      Provider<TransactionRepository> transactionRepositoryProvider,
      Provider<PresetOptionRepository> presetOptionRepositoryProvider,
      Provider<ImageFileManager> imageFileManagerProvider) {
    return new ZipImportViewModel_Factory(contextProvider, itemRepositoryProvider, transactionRepositoryProvider, presetOptionRepositoryProvider, imageFileManagerProvider);
  }

  public static ZipImportViewModel newInstance(Context context, ItemRepository itemRepository,
      TransactionRepository transactionRepository, PresetOptionRepository presetOptionRepository,
      ImageFileManager imageFileManager) {
    return new ZipImportViewModel(context, itemRepository, transactionRepository, presetOptionRepository, imageFileManager);
  }
}
