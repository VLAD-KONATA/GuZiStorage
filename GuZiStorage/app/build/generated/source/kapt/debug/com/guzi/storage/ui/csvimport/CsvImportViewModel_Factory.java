package com.guzi.storage.ui.csvimport;

import android.content.Context;
import com.guzi.storage.data.repository.ItemRepository;
import com.guzi.storage.data.repository.PresetOptionRepository;
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
public final class CsvImportViewModel_Factory implements Factory<CsvImportViewModel> {
  private final Provider<Context> contextProvider;

  private final Provider<ItemRepository> itemRepositoryProvider;

  private final Provider<PresetOptionRepository> presetOptionRepositoryProvider;

  public CsvImportViewModel_Factory(Provider<Context> contextProvider,
      Provider<ItemRepository> itemRepositoryProvider,
      Provider<PresetOptionRepository> presetOptionRepositoryProvider) {
    this.contextProvider = contextProvider;
    this.itemRepositoryProvider = itemRepositoryProvider;
    this.presetOptionRepositoryProvider = presetOptionRepositoryProvider;
  }

  @Override
  public CsvImportViewModel get() {
    return newInstance(contextProvider.get(), itemRepositoryProvider.get(), presetOptionRepositoryProvider.get());
  }

  public static CsvImportViewModel_Factory create(Provider<Context> contextProvider,
      Provider<ItemRepository> itemRepositoryProvider,
      Provider<PresetOptionRepository> presetOptionRepositoryProvider) {
    return new CsvImportViewModel_Factory(contextProvider, itemRepositoryProvider, presetOptionRepositoryProvider);
  }

  public static CsvImportViewModel newInstance(Context context, ItemRepository itemRepository,
      PresetOptionRepository presetOptionRepository) {
    return new CsvImportViewModel(context, itemRepository, presetOptionRepository);
  }
}
