package com.guzi.storage.ui.fileimport;

import android.content.Context;
import androidx.lifecycle.SavedStateHandle;
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
public final class StandaloneImportViewModel_Factory implements Factory<StandaloneImportViewModel> {
  private final Provider<Context> contextProvider;

  private final Provider<ItemRepository> itemRepositoryProvider;

  private final Provider<TransactionRepository> transactionRepositoryProvider;

  private final Provider<PresetOptionRepository> presetOptionRepositoryProvider;

  private final Provider<ImageFileManager> imageFileManagerProvider;

  private final Provider<SavedStateHandle> savedStateHandleProvider;

  public StandaloneImportViewModel_Factory(Provider<Context> contextProvider,
      Provider<ItemRepository> itemRepositoryProvider,
      Provider<TransactionRepository> transactionRepositoryProvider,
      Provider<PresetOptionRepository> presetOptionRepositoryProvider,
      Provider<ImageFileManager> imageFileManagerProvider,
      Provider<SavedStateHandle> savedStateHandleProvider) {
    this.contextProvider = contextProvider;
    this.itemRepositoryProvider = itemRepositoryProvider;
    this.transactionRepositoryProvider = transactionRepositoryProvider;
    this.presetOptionRepositoryProvider = presetOptionRepositoryProvider;
    this.imageFileManagerProvider = imageFileManagerProvider;
    this.savedStateHandleProvider = savedStateHandleProvider;
  }

  @Override
  public StandaloneImportViewModel get() {
    return newInstance(contextProvider.get(), itemRepositoryProvider.get(), transactionRepositoryProvider.get(), presetOptionRepositoryProvider.get(), imageFileManagerProvider.get(), savedStateHandleProvider.get());
  }

  public static StandaloneImportViewModel_Factory create(Provider<Context> contextProvider,
      Provider<ItemRepository> itemRepositoryProvider,
      Provider<TransactionRepository> transactionRepositoryProvider,
      Provider<PresetOptionRepository> presetOptionRepositoryProvider,
      Provider<ImageFileManager> imageFileManagerProvider,
      Provider<SavedStateHandle> savedStateHandleProvider) {
    return new StandaloneImportViewModel_Factory(contextProvider, itemRepositoryProvider, transactionRepositoryProvider, presetOptionRepositoryProvider, imageFileManagerProvider, savedStateHandleProvider);
  }

  public static StandaloneImportViewModel newInstance(Context context,
      ItemRepository itemRepository, TransactionRepository transactionRepository,
      PresetOptionRepository presetOptionRepository, ImageFileManager imageFileManager,
      SavedStateHandle savedStateHandle) {
    return new StandaloneImportViewModel(context, itemRepository, transactionRepository, presetOptionRepository, imageFileManager, savedStateHandle);
  }
}
