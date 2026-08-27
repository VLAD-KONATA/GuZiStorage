package com.guzi.storage.ui.settings;

import com.guzi.storage.data.repository.PresetOptionRepository;
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
public final class PresetManageViewModel_Factory implements Factory<PresetManageViewModel> {
  private final Provider<PresetOptionRepository> presetOptionRepositoryProvider;

  public PresetManageViewModel_Factory(
      Provider<PresetOptionRepository> presetOptionRepositoryProvider) {
    this.presetOptionRepositoryProvider = presetOptionRepositoryProvider;
  }

  @Override
  public PresetManageViewModel get() {
    return newInstance(presetOptionRepositoryProvider.get());
  }

  public static PresetManageViewModel_Factory create(
      Provider<PresetOptionRepository> presetOptionRepositoryProvider) {
    return new PresetManageViewModel_Factory(presetOptionRepositoryProvider);
  }

  public static PresetManageViewModel newInstance(PresetOptionRepository presetOptionRepository) {
    return new PresetManageViewModel(presetOptionRepository);
  }
}
