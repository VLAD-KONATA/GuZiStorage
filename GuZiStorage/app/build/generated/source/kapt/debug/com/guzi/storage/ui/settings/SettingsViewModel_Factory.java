package com.guzi.storage.ui.settings;

import com.guzi.storage.data.repository.UserPreferencesRepository;
import com.guzi.storage.util.BackupManager;
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
public final class SettingsViewModel_Factory implements Factory<SettingsViewModel> {
  private final Provider<UserPreferencesRepository> userPreferencesRepositoryProvider;

  private final Provider<BackupManager> backupManagerProvider;

  public SettingsViewModel_Factory(
      Provider<UserPreferencesRepository> userPreferencesRepositoryProvider,
      Provider<BackupManager> backupManagerProvider) {
    this.userPreferencesRepositoryProvider = userPreferencesRepositoryProvider;
    this.backupManagerProvider = backupManagerProvider;
  }

  @Override
  public SettingsViewModel get() {
    return newInstance(userPreferencesRepositoryProvider.get(), backupManagerProvider.get());
  }

  public static SettingsViewModel_Factory create(
      Provider<UserPreferencesRepository> userPreferencesRepositoryProvider,
      Provider<BackupManager> backupManagerProvider) {
    return new SettingsViewModel_Factory(userPreferencesRepositoryProvider, backupManagerProvider);
  }

  public static SettingsViewModel newInstance(UserPreferencesRepository userPreferencesRepository,
      BackupManager backupManager) {
    return new SettingsViewModel(userPreferencesRepository, backupManager);
  }
}
