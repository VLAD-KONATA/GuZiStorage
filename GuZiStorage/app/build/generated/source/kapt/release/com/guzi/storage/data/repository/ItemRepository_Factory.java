package com.guzi.storage.data.repository;

import com.guzi.storage.data.local.dao.ItemDao;
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
public final class ItemRepository_Factory implements Factory<ItemRepository> {
  private final Provider<ItemDao> itemDaoProvider;

  public ItemRepository_Factory(Provider<ItemDao> itemDaoProvider) {
    this.itemDaoProvider = itemDaoProvider;
  }

  @Override
  public ItemRepository get() {
    return newInstance(itemDaoProvider.get());
  }

  public static ItemRepository_Factory create(Provider<ItemDao> itemDaoProvider) {
    return new ItemRepository_Factory(itemDaoProvider);
  }

  public static ItemRepository newInstance(ItemDao itemDao) {
    return new ItemRepository(itemDao);
  }
}
