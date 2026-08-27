package com.guzi.storage.ui.add;

import com.guzi.storage.data.repository.ItemRepository;
import com.guzi.storage.data.repository.PresetOptionRepository;
import com.guzi.storage.data.repository.UserPreferencesRepository;
import com.guzi.storage.util.ImageFileManager;
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
public final class AddItemViewModel_Factory implements Factory<AddItemViewModel> {
  private final Provider<ItemRepository> itemRepositoryProvider;

  private final Provider<PresetOptionRepository> presetOptionRepositoryProvider;

  private final Provider<ImageFileManager> imageFileManagerProvider;

  private final Provider<UserPreferencesRepository> userPreferencesRepositoryProvider;

  public AddItemViewModel_Factory(Provider<ItemRepository> itemRepositoryProvider,
      Provider<PresetOptionRepository> presetOptionRepositoryProvider,
      Provider<ImageFileManager> imageFileManagerProvider,
      Provider<UserPreferencesRepository> userPreferencesRepositoryProvider) {
    this.itemRepositoryProvider = itemRepositoryProvider;
    this.presetOptionRepositoryProvider = presetOptionRepositoryProvider;
    this.imageFileManagerProvider = imageFileManagerProvider;
    this.userPreferencesRepositoryProvider = userPreferencesRepositoryProvider;
  }

  @Override
  public AddItemViewModel get() {
    return newInstance(itemRepositoryProvider.get(), presetOptionRepositoryProvider.get(), imageFileManagerProvider.get(), userPreferencesRepositoryProvider.get());
  }

  public static AddItemViewModel_Factory create(Provider<ItemRepository> itemRepositoryProvider,
      Provider<PresetOptionRepository> presetOptionRepositoryProvider,
      Provider<ImageFileManager> imageFileManagerProvider,
      Provider<UserPreferencesRepository> userPreferencesRepositoryProvider) {
    return new AddItemViewModel_Factory(itemRepositoryProvider, presetOptionRepositoryProvider, imageFileManagerProvider, userPreferencesRepositoryProvider);
  }

  public static AddItemViewModel newInstance(ItemRepository itemRepository,
      PresetOptionRepository presetOptionRepository, ImageFileManager imageFileManager,
      UserPreferencesRepository userPreferencesRepository) {
    return new AddItemViewModel(itemRepository, presetOptionRepository, imageFileManager, userPreferencesRepository);
  }
}
