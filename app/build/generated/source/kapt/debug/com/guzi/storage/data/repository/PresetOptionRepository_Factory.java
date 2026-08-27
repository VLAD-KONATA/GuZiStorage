package com.guzi.storage.data.repository;

import com.guzi.storage.data.local.dao.PresetOptionDao;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata("javax.inject.Singleton")
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
public final class PresetOptionRepository_Factory implements Factory<PresetOptionRepository> {
  private final Provider<PresetOptionDao> presetOptionDaoProvider;

  public PresetOptionRepository_Factory(Provider<PresetOptionDao> presetOptionDaoProvider) {
    this.presetOptionDaoProvider = presetOptionDaoProvider;
  }

  @Override
  public PresetOptionRepository get() {
    return newInstance(presetOptionDaoProvider.get());
  }

  public static PresetOptionRepository_Factory create(
      Provider<PresetOptionDao> presetOptionDaoProvider) {
    return new PresetOptionRepository_Factory(presetOptionDaoProvider);
  }

  public static PresetOptionRepository newInstance(PresetOptionDao presetOptionDao) {
    return new PresetOptionRepository(presetOptionDao);
  }
}
