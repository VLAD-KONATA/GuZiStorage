package com.guzi.storage.ui.group;

import com.guzi.storage.data.repository.ItemRepository;
import com.guzi.storage.data.repository.PresetOptionRepository;
import com.guzi.storage.data.repository.UserPreferencesRepository;
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
public final class GroupViewModel_Factory implements Factory<GroupViewModel> {
  private final Provider<ItemRepository> itemRepositoryProvider;

  private final Provider<PresetOptionRepository> presetOptionRepositoryProvider;

  private final Provider<UserPreferencesRepository> userPreferencesRepositoryProvider;

  public GroupViewModel_Factory(Provider<ItemRepository> itemRepositoryProvider,
      Provider<PresetOptionRepository> presetOptionRepositoryProvider,
      Provider<UserPreferencesRepository> userPreferencesRepositoryProvider) {
    this.itemRepositoryProvider = itemRepositoryProvider;
    this.presetOptionRepositoryProvider = presetOptionRepositoryProvider;
    this.userPreferencesRepositoryProvider = userPreferencesRepositoryProvider;
  }

  @Override
  public GroupViewModel get() {
    return newInstance(itemRepositoryProvider.get(), presetOptionRepositoryProvider.get(), userPreferencesRepositoryProvider.get());
  }

  public static GroupViewModel_Factory create(Provider<ItemRepository> itemRepositoryProvider,
      Provider<PresetOptionRepository> presetOptionRepositoryProvider,
      Provider<UserPreferencesRepository> userPreferencesRepositoryProvider) {
    return new GroupViewModel_Factory(itemRepositoryProvider, presetOptionRepositoryProvider, userPreferencesRepositoryProvider);
  }

  public static GroupViewModel newInstance(ItemRepository itemRepository,
      PresetOptionRepository presetOptionRepository,
      UserPreferencesRepository userPreferencesRepository) {
    return new GroupViewModel(itemRepository, presetOptionRepository, userPreferencesRepository);
  }
}
