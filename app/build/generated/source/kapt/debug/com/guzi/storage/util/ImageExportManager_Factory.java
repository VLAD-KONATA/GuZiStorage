package com.guzi.storage.util;

import android.content.Context;
import com.guzi.storage.data.repository.UserPreferencesRepository;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata("javax.inject.Singleton")
@QualifierMetadata("dagger.hilt.android.qualifiers.ApplicationContext")
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
public final class ImageExportManager_Factory implements Factory<ImageExportManager> {
  private final Provider<Context> contextProvider;

  private final Provider<ImageFileManager> imageFileManagerProvider;

  private final Provider<UserPreferencesRepository> userPreferencesRepositoryProvider;

  public ImageExportManager_Factory(Provider<Context> contextProvider,
      Provider<ImageFileManager> imageFileManagerProvider,
      Provider<UserPreferencesRepository> userPreferencesRepositoryProvider) {
    this.contextProvider = contextProvider;
    this.imageFileManagerProvider = imageFileManagerProvider;
    this.userPreferencesRepositoryProvider = userPreferencesRepositoryProvider;
  }

  @Override
  public ImageExportManager get() {
    return newInstance(contextProvider.get(), imageFileManagerProvider.get(), userPreferencesRepositoryProvider.get());
  }

  public static ImageExportManager_Factory create(Provider<Context> contextProvider,
      Provider<ImageFileManager> imageFileManagerProvider,
      Provider<UserPreferencesRepository> userPreferencesRepositoryProvider) {
    return new ImageExportManager_Factory(contextProvider, imageFileManagerProvider, userPreferencesRepositoryProvider);
  }

  public static ImageExportManager newInstance(Context context, ImageFileManager imageFileManager,
      UserPreferencesRepository userPreferencesRepository) {
    return new ImageExportManager(context, imageFileManager, userPreferencesRepository);
  }
}
