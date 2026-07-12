package com.guzi.storage.util;

import android.content.Context;
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
public final class ImageFileManager_Factory implements Factory<ImageFileManager> {
  private final Provider<Context> contextProvider;

  public ImageFileManager_Factory(Provider<Context> contextProvider) {
    this.contextProvider = contextProvider;
  }

  @Override
  public ImageFileManager get() {
    return newInstance(contextProvider.get());
  }

  public static ImageFileManager_Factory create(Provider<Context> contextProvider) {
    return new ImageFileManager_Factory(contextProvider);
  }

  public static ImageFileManager newInstance(Context context) {
    return new ImageFileManager(context);
  }
}
