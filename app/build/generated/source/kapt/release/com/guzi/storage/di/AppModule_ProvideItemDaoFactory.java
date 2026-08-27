package com.guzi.storage.di;

import com.guzi.storage.data.local.AppDatabase;
import com.guzi.storage.data.local.dao.ItemDao;
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
public final class AppModule_ProvideItemDaoFactory implements Factory<ItemDao> {
  private final Provider<AppDatabase> databaseProvider;

  public AppModule_ProvideItemDaoFactory(Provider<AppDatabase> databaseProvider) {
    this.databaseProvider = databaseProvider;
  }

  @Override
  public ItemDao get() {
    return provideItemDao(databaseProvider.get());
  }

  public static AppModule_ProvideItemDaoFactory create(Provider<AppDatabase> databaseProvider) {
    return new AppModule_ProvideItemDaoFactory(databaseProvider);
  }

  public static ItemDao provideItemDao(AppDatabase database) {
    return Preconditions.checkNotNullFromProvides(AppModule.INSTANCE.provideItemDao(database));
  }
}
