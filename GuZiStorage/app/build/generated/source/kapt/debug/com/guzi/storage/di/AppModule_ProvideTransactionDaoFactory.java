package com.guzi.storage.di;

import com.guzi.storage.data.local.AppDatabase;
import com.guzi.storage.data.local.dao.TransactionDao;
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
public final class AppModule_ProvideTransactionDaoFactory implements Factory<TransactionDao> {
  private final Provider<AppDatabase> databaseProvider;

  public AppModule_ProvideTransactionDaoFactory(Provider<AppDatabase> databaseProvider) {
    this.databaseProvider = databaseProvider;
  }

  @Override
  public TransactionDao get() {
    return provideTransactionDao(databaseProvider.get());
  }

  public static AppModule_ProvideTransactionDaoFactory create(
      Provider<AppDatabase> databaseProvider) {
    return new AppModule_ProvideTransactionDaoFactory(databaseProvider);
  }

  public static TransactionDao provideTransactionDao(AppDatabase database) {
    return Preconditions.checkNotNullFromProvides(AppModule.INSTANCE.provideTransactionDao(database));
  }
}
