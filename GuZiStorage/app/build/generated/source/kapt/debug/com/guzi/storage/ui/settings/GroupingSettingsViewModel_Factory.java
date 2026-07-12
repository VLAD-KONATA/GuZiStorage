package com.guzi.storage.ui.settings;

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
public final class GroupingSettingsViewModel_Factory implements Factory<GroupingSettingsViewModel> {
  private final Provider<UserPreferencesRepository> userPreferencesRepositoryProvider;

  public GroupingSettingsViewModel_Factory(
      Provider<UserPreferencesRepository> userPreferencesRepositoryProvider) {
    this.userPreferencesRepositoryProvider = userPreferencesRepositoryProvider;
  }

  @Override
  public GroupingSettingsViewModel get() {
    return newInstance(userPreferencesRepositoryProvider.get());
  }

  public static GroupingSettingsViewModel_Factory create(
      Provider<UserPreferencesRepository> userPreferencesRepositoryProvider) {
    return new GroupingSettingsViewModel_Factory(userPreferencesRepositoryProvider);
  }

  public static GroupingSettingsViewModel newInstance(
      UserPreferencesRepository userPreferencesRepository) {
    return new GroupingSettingsViewModel(userPreferencesRepository);
  }
}
