package com.guzi.storage.ui.home;

import com.guzi.storage.data.repository.ItemRepository;
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
public final class HomeViewModel_Factory implements Factory<HomeViewModel> {
  private final Provider<ItemRepository> itemRepositoryProvider;

  private final Provider<UserPreferencesRepository> userPreferencesRepositoryProvider;

  public HomeViewModel_Factory(Provider<ItemRepository> itemRepositoryProvider,
      Provider<UserPreferencesRepository> userPreferencesRepositoryProvider) {
    this.itemRepositoryProvider = itemRepositoryProvider;
    this.userPreferencesRepositoryProvider = userPreferencesRepositoryProvider;
  }

  @Override
  public HomeViewModel get() {
    return newInstance(itemRepositoryProvider.get(), userPreferencesRepositoryProvider.get());
  }

  public static HomeViewModel_Factory create(Provider<ItemRepository> itemRepositoryProvider,
      Provider<UserPreferencesRepository> userPreferencesRepositoryProvider) {
    return new HomeViewModel_Factory(itemRepositoryProvider, userPreferencesRepositoryProvider);
  }

  public static HomeViewModel newInstance(ItemRepository itemRepository,
      UserPreferencesRepository userPreferencesRepository) {
    return new HomeViewModel(itemRepository, userPreferencesRepository);
  }
}
