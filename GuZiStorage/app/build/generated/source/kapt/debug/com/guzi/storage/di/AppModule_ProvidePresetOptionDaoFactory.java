package com.guzi.storage.di;

import com.guzi.storage.data.local.AppDatabase;
import com.guzi.storage.data.local.dao.PresetOptionDao;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
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
public final class AppModule_ProvidePresetOptionDaoFactory implements Factory<PresetOptionDao> {
  private final Provider<AppDatabase> databaseProvider;

  public AppModule_ProvidePresetOptionDaoFactory(Provider<AppDatabase> databaseProvider) {
    this.databaseProvider = databaseProvider;
  }

  @Override
  public PresetOptionDao get() {
    return providePresetOptionDao(databaseProvider.get());
  }

  public static AppModule_ProvidePresetOptionDaoFactory create(
      Provider<AppDatabase> databaseProvider) {
    return new AppModule_ProvidePresetOptionDaoFactory(databaseProvider);
  }

  public static PresetOptionDao providePresetOptionDao(AppDatabase database) {
    return Preconditions.checkNotNullFromProvides(AppModule.INSTANCE.providePresetOptionDao(database));
  }
}
