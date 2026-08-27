package com.guzi.storage.ui.transaction.add;

import com.guzi.storage.data.repository.ItemRepository;
import com.guzi.storage.data.repository.PresetOptionRepository;
import com.guzi.storage.data.repository.TransactionRepository;
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
public final class AddTransactionViewModel_Factory implements Factory<AddTransactionViewModel> {
  private final Provider<TransactionRepository> transactionRepositoryProvider;

  private final Provider<PresetOptionRepository> presetOptionRepositoryProvider;

  private final Provider<ItemRepository> itemRepositoryProvider;

  private final Provider<UserPreferencesRepository> userPreferencesRepositoryProvider;

  public AddTransactionViewModel_Factory(
      Provider<TransactionRepository> transactionRepositoryProvider,
      Provider<PresetOptionRepository> presetOptionRepositoryProvider,
      Provider<ItemRepository> itemRepositoryProvider,
      Provider<UserPreferencesRepository> userPreferencesRepositoryProvider) {
    this.transactionRepositoryProvider = transactionRepositoryProvider;
    this.presetOptionRepositoryProvider = presetOptionRepositoryProvider;
    this.itemRepositoryProvider = itemRepositoryProvider;
    this.userPreferencesRepositoryProvider = userPreferencesRepositoryProvider;
  }

  @Override
  public AddTransactionViewModel get() {
    return newInstance(transactionRepositoryProvider.get(), presetOptionRepositoryProvider.get(), itemRepositoryProvider.get(), userPreferencesRepositoryProvider.get());
  }

  public static AddTransactionViewModel_Factory create(
      Provider<TransactionRepository> transactionRepositoryProvider,
      Provider<PresetOptionRepository> presetOptionRepositoryProvider,
      Provider<ItemRepository> itemRepositoryProvider,
      Provider<UserPreferencesRepository> userPreferencesRepositoryProvider) {
    return new AddTransactionViewModel_Factory(transactionRepositoryProvider, presetOptionRepositoryProvider, itemRepositoryProvider, userPreferencesRepositoryProvider);
  }

  public static AddTransactionViewModel newInstance(TransactionRepository transactionRepository,
      PresetOptionRepository presetOptionRepository, ItemRepository itemRepository,
      UserPreferencesRepository userPreferencesRepository) {
    return new AddTransactionViewModel(transactionRepository, presetOptionRepository, itemRepository, userPreferencesRepository);
  }
}
