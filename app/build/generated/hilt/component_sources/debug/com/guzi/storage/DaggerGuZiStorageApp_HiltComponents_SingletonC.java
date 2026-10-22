package com.guzi.storage;

import android.app.Activity;
import android.app.Service;
import android.view.View;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;
import com.guzi.storage.data.local.AppDatabase;
import com.guzi.storage.data.local.dao.ItemDao;
import com.guzi.storage.data.local.dao.PresetOptionDao;
import com.guzi.storage.data.local.dao.TransactionDao;
import com.guzi.storage.data.repository.ItemRepository;
import com.guzi.storage.data.repository.PresetOptionRepository;
import com.guzi.storage.data.repository.TransactionRepository;
import com.guzi.storage.data.repository.UserPreferencesRepository;
import com.guzi.storage.di.AppModule_ProvideDatabaseFactory;
import com.guzi.storage.di.AppModule_ProvideItemDaoFactory;
import com.guzi.storage.di.AppModule_ProvidePresetOptionDaoFactory;
import com.guzi.storage.di.AppModule_ProvideTransactionDaoFactory;
import com.guzi.storage.ui.add.AddItemViewModel;
import com.guzi.storage.ui.add.AddItemViewModel_HiltModules;
import com.guzi.storage.ui.csvimport.CsvImportViewModel;
import com.guzi.storage.ui.csvimport.CsvImportViewModel_HiltModules;
import com.guzi.storage.ui.detail.DetailViewModel;
import com.guzi.storage.ui.detail.DetailViewModel_HiltModules;
import com.guzi.storage.ui.fileimport.StandaloneImportViewModel;
import com.guzi.storage.ui.fileimport.StandaloneImportViewModel_HiltModules;
import com.guzi.storage.ui.group.GroupViewModel;
import com.guzi.storage.ui.group.GroupViewModel_HiltModules;
import com.guzi.storage.ui.home.HomeViewModel;
import com.guzi.storage.ui.home.HomeViewModel_HiltModules;
import com.guzi.storage.ui.settings.GroupingSettingsViewModel;
import com.guzi.storage.ui.settings.GroupingSettingsViewModel_HiltModules;
import com.guzi.storage.ui.settings.PresetManageViewModel;
import com.guzi.storage.ui.settings.PresetManageViewModel_HiltModules;
import com.guzi.storage.ui.settings.SettingsViewModel;
import com.guzi.storage.ui.settings.SettingsViewModel_HiltModules;
import com.guzi.storage.ui.transaction.TransactionViewModel;
import com.guzi.storage.ui.transaction.TransactionViewModel_HiltModules;
import com.guzi.storage.ui.transaction.add.AddTransactionViewModel;
import com.guzi.storage.ui.transaction.add.AddTransactionViewModel_HiltModules;
import com.guzi.storage.ui.zipimport.ZipImportViewModel;
import com.guzi.storage.ui.zipimport.ZipImportViewModel_HiltModules;
import com.guzi.storage.util.BackupManager;
import com.guzi.storage.util.ImageExportManager;
import com.guzi.storage.util.ImageFileManager;
import dagger.hilt.android.ActivityRetainedLifecycle;
import dagger.hilt.android.ViewModelLifecycle;
import dagger.hilt.android.internal.builders.ActivityComponentBuilder;
import dagger.hilt.android.internal.builders.ActivityRetainedComponentBuilder;
import dagger.hilt.android.internal.builders.FragmentComponentBuilder;
import dagger.hilt.android.internal.builders.ServiceComponentBuilder;
import dagger.hilt.android.internal.builders.ViewComponentBuilder;
import dagger.hilt.android.internal.builders.ViewModelComponentBuilder;
import dagger.hilt.android.internal.builders.ViewWithFragmentComponentBuilder;
import dagger.hilt.android.internal.lifecycle.DefaultViewModelFactories;
import dagger.hilt.android.internal.lifecycle.DefaultViewModelFactories_InternalFactoryFactory_Factory;
import dagger.hilt.android.internal.managers.ActivityRetainedComponentManager_LifecycleModule_ProvideActivityRetainedLifecycleFactory;
import dagger.hilt.android.internal.managers.SavedStateHandleHolder;
import dagger.hilt.android.internal.modules.ApplicationContextModule;
import dagger.hilt.android.internal.modules.ApplicationContextModule_ProvideContextFactory;
import dagger.internal.DaggerGenerated;
import dagger.internal.DoubleCheck;
import dagger.internal.IdentifierNameString;
import dagger.internal.KeepFieldType;
import dagger.internal.LazyClassKeyMap;
import dagger.internal.MapBuilder;
import dagger.internal.Preconditions;
import dagger.internal.Provider;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import javax.annotation.processing.Generated;

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
public final class DaggerGuZiStorageApp_HiltComponents_SingletonC {
  private DaggerGuZiStorageApp_HiltComponents_SingletonC() {
  }

  public static Builder builder() {
    return new Builder();
  }

  public static final class Builder {
    private ApplicationContextModule applicationContextModule;

    private Builder() {
    }

    public Builder applicationContextModule(ApplicationContextModule applicationContextModule) {
      this.applicationContextModule = Preconditions.checkNotNull(applicationContextModule);
      return this;
    }

    public GuZiStorageApp_HiltComponents.SingletonC build() {
      Preconditions.checkBuilderRequirement(applicationContextModule, ApplicationContextModule.class);
      return new SingletonCImpl(applicationContextModule);
    }
  }

  private static final class ActivityRetainedCBuilder implements GuZiStorageApp_HiltComponents.ActivityRetainedC.Builder {
    private final SingletonCImpl singletonCImpl;

    private SavedStateHandleHolder savedStateHandleHolder;

    private ActivityRetainedCBuilder(SingletonCImpl singletonCImpl) {
      this.singletonCImpl = singletonCImpl;
    }

    @Override
    public ActivityRetainedCBuilder savedStateHandleHolder(
        SavedStateHandleHolder savedStateHandleHolder) {
      this.savedStateHandleHolder = Preconditions.checkNotNull(savedStateHandleHolder);
      return this;
    }

    @Override
    public GuZiStorageApp_HiltComponents.ActivityRetainedC build() {
      Preconditions.checkBuilderRequirement(savedStateHandleHolder, SavedStateHandleHolder.class);
      return new ActivityRetainedCImpl(singletonCImpl, savedStateHandleHolder);
    }
  }

  private static final class ActivityCBuilder implements GuZiStorageApp_HiltComponents.ActivityC.Builder {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private Activity activity;

    private ActivityCBuilder(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
    }

    @Override
    public ActivityCBuilder activity(Activity activity) {
      this.activity = Preconditions.checkNotNull(activity);
      return this;
    }

    @Override
    public GuZiStorageApp_HiltComponents.ActivityC build() {
      Preconditions.checkBuilderRequirement(activity, Activity.class);
      return new ActivityCImpl(singletonCImpl, activityRetainedCImpl, activity);
    }
  }

  private static final class FragmentCBuilder implements GuZiStorageApp_HiltComponents.FragmentC.Builder {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private Fragment fragment;

    private FragmentCBuilder(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, ActivityCImpl activityCImpl) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;
    }

    @Override
    public FragmentCBuilder fragment(Fragment fragment) {
      this.fragment = Preconditions.checkNotNull(fragment);
      return this;
    }

    @Override
    public GuZiStorageApp_HiltComponents.FragmentC build() {
      Preconditions.checkBuilderRequirement(fragment, Fragment.class);
      return new FragmentCImpl(singletonCImpl, activityRetainedCImpl, activityCImpl, fragment);
    }
  }

  private static final class ViewWithFragmentCBuilder implements GuZiStorageApp_HiltComponents.ViewWithFragmentC.Builder {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private final FragmentCImpl fragmentCImpl;

    private View view;

    private ViewWithFragmentCBuilder(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, ActivityCImpl activityCImpl,
        FragmentCImpl fragmentCImpl) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;
      this.fragmentCImpl = fragmentCImpl;
    }

    @Override
    public ViewWithFragmentCBuilder view(View view) {
      this.view = Preconditions.checkNotNull(view);
      return this;
    }

    @Override
    public GuZiStorageApp_HiltComponents.ViewWithFragmentC build() {
      Preconditions.checkBuilderRequirement(view, View.class);
      return new ViewWithFragmentCImpl(singletonCImpl, activityRetainedCImpl, activityCImpl, fragmentCImpl, view);
    }
  }

  private static final class ViewCBuilder implements GuZiStorageApp_HiltComponents.ViewC.Builder {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private View view;

    private ViewCBuilder(SingletonCImpl singletonCImpl, ActivityRetainedCImpl activityRetainedCImpl,
        ActivityCImpl activityCImpl) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;
    }

    @Override
    public ViewCBuilder view(View view) {
      this.view = Preconditions.checkNotNull(view);
      return this;
    }

    @Override
    public GuZiStorageApp_HiltComponents.ViewC build() {
      Preconditions.checkBuilderRequirement(view, View.class);
      return new ViewCImpl(singletonCImpl, activityRetainedCImpl, activityCImpl, view);
    }
  }

  private static final class ViewModelCBuilder implements GuZiStorageApp_HiltComponents.ViewModelC.Builder {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private SavedStateHandle savedStateHandle;

    private ViewModelLifecycle viewModelLifecycle;

    private ViewModelCBuilder(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
    }

    @Override
    public ViewModelCBuilder savedStateHandle(SavedStateHandle handle) {
      this.savedStateHandle = Preconditions.checkNotNull(handle);
      return this;
    }

    @Override
    public ViewModelCBuilder viewModelLifecycle(ViewModelLifecycle viewModelLifecycle) {
      this.viewModelLifecycle = Preconditions.checkNotNull(viewModelLifecycle);
      return this;
    }

    @Override
    public GuZiStorageApp_HiltComponents.ViewModelC build() {
      Preconditions.checkBuilderRequirement(savedStateHandle, SavedStateHandle.class);
      Preconditions.checkBuilderRequirement(viewModelLifecycle, ViewModelLifecycle.class);
      return new ViewModelCImpl(singletonCImpl, activityRetainedCImpl, savedStateHandle, viewModelLifecycle);
    }
  }

  private static final class ServiceCBuilder implements GuZiStorageApp_HiltComponents.ServiceC.Builder {
    private final SingletonCImpl singletonCImpl;

    private Service service;

    private ServiceCBuilder(SingletonCImpl singletonCImpl) {
      this.singletonCImpl = singletonCImpl;
    }

    @Override
    public ServiceCBuilder service(Service service) {
      this.service = Preconditions.checkNotNull(service);
      return this;
    }

    @Override
    public GuZiStorageApp_HiltComponents.ServiceC build() {
      Preconditions.checkBuilderRequirement(service, Service.class);
      return new ServiceCImpl(singletonCImpl, service);
    }
  }

  private static final class ViewWithFragmentCImpl extends GuZiStorageApp_HiltComponents.ViewWithFragmentC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private final FragmentCImpl fragmentCImpl;

    private final ViewWithFragmentCImpl viewWithFragmentCImpl = this;

    private ViewWithFragmentCImpl(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, ActivityCImpl activityCImpl,
        FragmentCImpl fragmentCImpl, View viewParam) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;
      this.fragmentCImpl = fragmentCImpl;


    }
  }

  private static final class FragmentCImpl extends GuZiStorageApp_HiltComponents.FragmentC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private final FragmentCImpl fragmentCImpl = this;

    private FragmentCImpl(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, ActivityCImpl activityCImpl,
        Fragment fragmentParam) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;


    }

    @Override
    public DefaultViewModelFactories.InternalFactoryFactory getHiltInternalFactoryFactory() {
      return activityCImpl.getHiltInternalFactoryFactory();
    }

    @Override
    public ViewWithFragmentComponentBuilder viewWithFragmentComponentBuilder() {
      return new ViewWithFragmentCBuilder(singletonCImpl, activityRetainedCImpl, activityCImpl, fragmentCImpl);
    }
  }

  private static final class ViewCImpl extends GuZiStorageApp_HiltComponents.ViewC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private final ViewCImpl viewCImpl = this;

    private ViewCImpl(SingletonCImpl singletonCImpl, ActivityRetainedCImpl activityRetainedCImpl,
        ActivityCImpl activityCImpl, View viewParam) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;


    }
  }

  private static final class ActivityCImpl extends GuZiStorageApp_HiltComponents.ActivityC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl = this;

    private ActivityCImpl(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, Activity activityParam) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;


    }

    @Override
    public void injectMainActivity(MainActivity mainActivity) {
    }

    @Override
    public DefaultViewModelFactories.InternalFactoryFactory getHiltInternalFactoryFactory() {
      return DefaultViewModelFactories_InternalFactoryFactory_Factory.newInstance(getViewModelKeys(), new ViewModelCBuilder(singletonCImpl, activityRetainedCImpl));
    }

    @Override
    public Map<Class<?>, Boolean> getViewModelKeys() {
      return LazyClassKeyMap.<Boolean>of(MapBuilder.<String, Boolean>newMapBuilder(12).put(LazyClassKeyProvider.com_guzi_storage_ui_add_AddItemViewModel, AddItemViewModel_HiltModules.KeyModule.provide()).put(LazyClassKeyProvider.com_guzi_storage_ui_transaction_add_AddTransactionViewModel, AddTransactionViewModel_HiltModules.KeyModule.provide()).put(LazyClassKeyProvider.com_guzi_storage_ui_csvimport_CsvImportViewModel, CsvImportViewModel_HiltModules.KeyModule.provide()).put(LazyClassKeyProvider.com_guzi_storage_ui_detail_DetailViewModel, DetailViewModel_HiltModules.KeyModule.provide()).put(LazyClassKeyProvider.com_guzi_storage_ui_group_GroupViewModel, GroupViewModel_HiltModules.KeyModule.provide()).put(LazyClassKeyProvider.com_guzi_storage_ui_settings_GroupingSettingsViewModel, GroupingSettingsViewModel_HiltModules.KeyModule.provide()).put(LazyClassKeyProvider.com_guzi_storage_ui_home_HomeViewModel, HomeViewModel_HiltModules.KeyModule.provide()).put(LazyClassKeyProvider.com_guzi_storage_ui_settings_PresetManageViewModel, PresetManageViewModel_HiltModules.KeyModule.provide()).put(LazyClassKeyProvider.com_guzi_storage_ui_settings_SettingsViewModel, SettingsViewModel_HiltModules.KeyModule.provide()).put(LazyClassKeyProvider.com_guzi_storage_ui_fileimport_StandaloneImportViewModel, StandaloneImportViewModel_HiltModules.KeyModule.provide()).put(LazyClassKeyProvider.com_guzi_storage_ui_transaction_TransactionViewModel, TransactionViewModel_HiltModules.KeyModule.provide()).put(LazyClassKeyProvider.com_guzi_storage_ui_zipimport_ZipImportViewModel, ZipImportViewModel_HiltModules.KeyModule.provide()).build());
    }

    @Override
    public ViewModelComponentBuilder getViewModelComponentBuilder() {
      return new ViewModelCBuilder(singletonCImpl, activityRetainedCImpl);
    }

    @Override
    public FragmentComponentBuilder fragmentComponentBuilder() {
      return new FragmentCBuilder(singletonCImpl, activityRetainedCImpl, activityCImpl);
    }

    @Override
    public ViewComponentBuilder viewComponentBuilder() {
      return new ViewCBuilder(singletonCImpl, activityRetainedCImpl, activityCImpl);
    }

    @IdentifierNameString
    private static final class LazyClassKeyProvider {
      static String com_guzi_storage_ui_add_AddItemViewModel = "com.guzi.storage.ui.add.AddItemViewModel";

      static String com_guzi_storage_ui_detail_DetailViewModel = "com.guzi.storage.ui.detail.DetailViewModel";

      static String com_guzi_storage_ui_zipimport_ZipImportViewModel = "com.guzi.storage.ui.zipimport.ZipImportViewModel";

      static String com_guzi_storage_ui_settings_PresetManageViewModel = "com.guzi.storage.ui.settings.PresetManageViewModel";

      static String com_guzi_storage_ui_settings_GroupingSettingsViewModel = "com.guzi.storage.ui.settings.GroupingSettingsViewModel";

      static String com_guzi_storage_ui_fileimport_StandaloneImportViewModel = "com.guzi.storage.ui.fileimport.StandaloneImportViewModel";

      static String com_guzi_storage_ui_group_GroupViewModel = "com.guzi.storage.ui.group.GroupViewModel";

      static String com_guzi_storage_ui_settings_SettingsViewModel = "com.guzi.storage.ui.settings.SettingsViewModel";

      static String com_guzi_storage_ui_transaction_add_AddTransactionViewModel = "com.guzi.storage.ui.transaction.add.AddTransactionViewModel";

      static String com_guzi_storage_ui_csvimport_CsvImportViewModel = "com.guzi.storage.ui.csvimport.CsvImportViewModel";

      static String com_guzi_storage_ui_home_HomeViewModel = "com.guzi.storage.ui.home.HomeViewModel";

      static String com_guzi_storage_ui_transaction_TransactionViewModel = "com.guzi.storage.ui.transaction.TransactionViewModel";

      @KeepFieldType
      AddItemViewModel com_guzi_storage_ui_add_AddItemViewModel2;

      @KeepFieldType
      DetailViewModel com_guzi_storage_ui_detail_DetailViewModel2;

      @KeepFieldType
      ZipImportViewModel com_guzi_storage_ui_zipimport_ZipImportViewModel2;

      @KeepFieldType
      PresetManageViewModel com_guzi_storage_ui_settings_PresetManageViewModel2;

      @KeepFieldType
      GroupingSettingsViewModel com_guzi_storage_ui_settings_GroupingSettingsViewModel2;

      @KeepFieldType
      StandaloneImportViewModel com_guzi_storage_ui_fileimport_StandaloneImportViewModel2;

      @KeepFieldType
      GroupViewModel com_guzi_storage_ui_group_GroupViewModel2;

      @KeepFieldType
      SettingsViewModel com_guzi_storage_ui_settings_SettingsViewModel2;

      @KeepFieldType
      AddTransactionViewModel com_guzi_storage_ui_transaction_add_AddTransactionViewModel2;

      @KeepFieldType
      CsvImportViewModel com_guzi_storage_ui_csvimport_CsvImportViewModel2;

      @KeepFieldType
      HomeViewModel com_guzi_storage_ui_home_HomeViewModel2;

      @KeepFieldType
      TransactionViewModel com_guzi_storage_ui_transaction_TransactionViewModel2;
    }
  }

  private static final class ViewModelCImpl extends GuZiStorageApp_HiltComponents.ViewModelC {
    private final SavedStateHandle savedStateHandle;

    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ViewModelCImpl viewModelCImpl = this;

    private Provider<AddItemViewModel> addItemViewModelProvider;

    private Provider<AddTransactionViewModel> addTransactionViewModelProvider;

    private Provider<CsvImportViewModel> csvImportViewModelProvider;

    private Provider<DetailViewModel> detailViewModelProvider;

    private Provider<GroupViewModel> groupViewModelProvider;

    private Provider<GroupingSettingsViewModel> groupingSettingsViewModelProvider;

    private Provider<HomeViewModel> homeViewModelProvider;

    private Provider<PresetManageViewModel> presetManageViewModelProvider;

    private Provider<SettingsViewModel> settingsViewModelProvider;

    private Provider<StandaloneImportViewModel> standaloneImportViewModelProvider;

    private Provider<TransactionViewModel> transactionViewModelProvider;

    private Provider<ZipImportViewModel> zipImportViewModelProvider;

    private ViewModelCImpl(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, SavedStateHandle savedStateHandleParam,
        ViewModelLifecycle viewModelLifecycleParam) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.savedStateHandle = savedStateHandleParam;
      initialize(savedStateHandleParam, viewModelLifecycleParam);

    }

    @SuppressWarnings("unchecked")
    private void initialize(final SavedStateHandle savedStateHandleParam,
        final ViewModelLifecycle viewModelLifecycleParam) {
      this.addItemViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 0);
      this.addTransactionViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 1);
      this.csvImportViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 2);
      this.detailViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 3);
      this.groupViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 4);
      this.groupingSettingsViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 5);
      this.homeViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 6);
      this.presetManageViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 7);
      this.settingsViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 8);
      this.standaloneImportViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 9);
      this.transactionViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 10);
      this.zipImportViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 11);
    }

    @Override
    public Map<Class<?>, javax.inject.Provider<ViewModel>> getHiltViewModelMap() {
      return LazyClassKeyMap.<javax.inject.Provider<ViewModel>>of(MapBuilder.<String, javax.inject.Provider<ViewModel>>newMapBuilder(12).put(LazyClassKeyProvider.com_guzi_storage_ui_add_AddItemViewModel, ((Provider) addItemViewModelProvider)).put(LazyClassKeyProvider.com_guzi_storage_ui_transaction_add_AddTransactionViewModel, ((Provider) addTransactionViewModelProvider)).put(LazyClassKeyProvider.com_guzi_storage_ui_csvimport_CsvImportViewModel, ((Provider) csvImportViewModelProvider)).put(LazyClassKeyProvider.com_guzi_storage_ui_detail_DetailViewModel, ((Provider) detailViewModelProvider)).put(LazyClassKeyProvider.com_guzi_storage_ui_group_GroupViewModel, ((Provider) groupViewModelProvider)).put(LazyClassKeyProvider.com_guzi_storage_ui_settings_GroupingSettingsViewModel, ((Provider) groupingSettingsViewModelProvider)).put(LazyClassKeyProvider.com_guzi_storage_ui_home_HomeViewModel, ((Provider) homeViewModelProvider)).put(LazyClassKeyProvider.com_guzi_storage_ui_settings_PresetManageViewModel, ((Provider) presetManageViewModelProvider)).put(LazyClassKeyProvider.com_guzi_storage_ui_settings_SettingsViewModel, ((Provider) settingsViewModelProvider)).put(LazyClassKeyProvider.com_guzi_storage_ui_fileimport_StandaloneImportViewModel, ((Provider) standaloneImportViewModelProvider)).put(LazyClassKeyProvider.com_guzi_storage_ui_transaction_TransactionViewModel, ((Provider) transactionViewModelProvider)).put(LazyClassKeyProvider.com_guzi_storage_ui_zipimport_ZipImportViewModel, ((Provider) zipImportViewModelProvider)).build());
    }

    @Override
    public Map<Class<?>, Object> getHiltViewModelAssistedMap() {
      return Collections.<Class<?>, Object>emptyMap();
    }

    @IdentifierNameString
    private static final class LazyClassKeyProvider {
      static String com_guzi_storage_ui_settings_GroupingSettingsViewModel = "com.guzi.storage.ui.settings.GroupingSettingsViewModel";

      static String com_guzi_storage_ui_fileimport_StandaloneImportViewModel = "com.guzi.storage.ui.fileimport.StandaloneImportViewModel";

      static String com_guzi_storage_ui_transaction_TransactionViewModel = "com.guzi.storage.ui.transaction.TransactionViewModel";

      static String com_guzi_storage_ui_group_GroupViewModel = "com.guzi.storage.ui.group.GroupViewModel";

      static String com_guzi_storage_ui_add_AddItemViewModel = "com.guzi.storage.ui.add.AddItemViewModel";

      static String com_guzi_storage_ui_csvimport_CsvImportViewModel = "com.guzi.storage.ui.csvimport.CsvImportViewModel";

      static String com_guzi_storage_ui_zipimport_ZipImportViewModel = "com.guzi.storage.ui.zipimport.ZipImportViewModel";

      static String com_guzi_storage_ui_transaction_add_AddTransactionViewModel = "com.guzi.storage.ui.transaction.add.AddTransactionViewModel";

      static String com_guzi_storage_ui_detail_DetailViewModel = "com.guzi.storage.ui.detail.DetailViewModel";

      static String com_guzi_storage_ui_settings_PresetManageViewModel = "com.guzi.storage.ui.settings.PresetManageViewModel";

      static String com_guzi_storage_ui_home_HomeViewModel = "com.guzi.storage.ui.home.HomeViewModel";

      static String com_guzi_storage_ui_settings_SettingsViewModel = "com.guzi.storage.ui.settings.SettingsViewModel";

      @KeepFieldType
      GroupingSettingsViewModel com_guzi_storage_ui_settings_GroupingSettingsViewModel2;

      @KeepFieldType
      StandaloneImportViewModel com_guzi_storage_ui_fileimport_StandaloneImportViewModel2;

      @KeepFieldType
      TransactionViewModel com_guzi_storage_ui_transaction_TransactionViewModel2;

      @KeepFieldType
      GroupViewModel com_guzi_storage_ui_group_GroupViewModel2;

      @KeepFieldType
      AddItemViewModel com_guzi_storage_ui_add_AddItemViewModel2;

      @KeepFieldType
      CsvImportViewModel com_guzi_storage_ui_csvimport_CsvImportViewModel2;

      @KeepFieldType
      ZipImportViewModel com_guzi_storage_ui_zipimport_ZipImportViewModel2;

      @KeepFieldType
      AddTransactionViewModel com_guzi_storage_ui_transaction_add_AddTransactionViewModel2;

      @KeepFieldType
      DetailViewModel com_guzi_storage_ui_detail_DetailViewModel2;

      @KeepFieldType
      PresetManageViewModel com_guzi_storage_ui_settings_PresetManageViewModel2;

      @KeepFieldType
      HomeViewModel com_guzi_storage_ui_home_HomeViewModel2;

      @KeepFieldType
      SettingsViewModel com_guzi_storage_ui_settings_SettingsViewModel2;
    }

    private static final class SwitchingProvider<T> implements Provider<T> {
      private final SingletonCImpl singletonCImpl;

      private final ActivityRetainedCImpl activityRetainedCImpl;

      private final ViewModelCImpl viewModelCImpl;

      private final int id;

      SwitchingProvider(SingletonCImpl singletonCImpl, ActivityRetainedCImpl activityRetainedCImpl,
          ViewModelCImpl viewModelCImpl, int id) {
        this.singletonCImpl = singletonCImpl;
        this.activityRetainedCImpl = activityRetainedCImpl;
        this.viewModelCImpl = viewModelCImpl;
        this.id = id;
      }

      @SuppressWarnings("unchecked")
      @Override
      public T get() {
        switch (id) {
          case 0: // com.guzi.storage.ui.add.AddItemViewModel 
          return (T) new AddItemViewModel(singletonCImpl.itemRepositoryProvider.get(), singletonCImpl.presetOptionRepositoryProvider.get(), singletonCImpl.imageFileManagerProvider.get(), singletonCImpl.userPreferencesRepositoryProvider.get());

          case 1: // com.guzi.storage.ui.transaction.add.AddTransactionViewModel 
          return (T) new AddTransactionViewModel(singletonCImpl.transactionRepositoryProvider.get(), singletonCImpl.presetOptionRepositoryProvider.get(), singletonCImpl.itemRepositoryProvider.get(), singletonCImpl.userPreferencesRepositoryProvider.get());

          case 2: // com.guzi.storage.ui.csvimport.CsvImportViewModel 
          return (T) new CsvImportViewModel(ApplicationContextModule_ProvideContextFactory.provideContext(singletonCImpl.applicationContextModule), singletonCImpl.itemRepositoryProvider.get(), singletonCImpl.presetOptionRepositoryProvider.get());

          case 3: // com.guzi.storage.ui.detail.DetailViewModel 
          return (T) new DetailViewModel(singletonCImpl.itemRepositoryProvider.get(), singletonCImpl.transactionRepositoryProvider.get(), singletonCImpl.imageExportManagerProvider.get());

          case 4: // com.guzi.storage.ui.group.GroupViewModel 
          return (T) new GroupViewModel(singletonCImpl.itemRepositoryProvider.get(), singletonCImpl.presetOptionRepositoryProvider.get(), singletonCImpl.userPreferencesRepositoryProvider.get());

          case 5: // com.guzi.storage.ui.settings.GroupingSettingsViewModel 
          return (T) new GroupingSettingsViewModel(singletonCImpl.userPreferencesRepositoryProvider.get());

          case 6: // com.guzi.storage.ui.home.HomeViewModel 
          return (T) new HomeViewModel(singletonCImpl.itemRepositoryProvider.get(), singletonCImpl.userPreferencesRepositoryProvider.get());

          case 7: // com.guzi.storage.ui.settings.PresetManageViewModel 
          return (T) new PresetManageViewModel(singletonCImpl.presetOptionRepositoryProvider.get());

          case 8: // com.guzi.storage.ui.settings.SettingsViewModel 
          return (T) new SettingsViewModel(singletonCImpl.userPreferencesRepositoryProvider.get(), singletonCImpl.backupManagerProvider.get());

          case 9: // com.guzi.storage.ui.fileimport.StandaloneImportViewModel 
          return (T) new StandaloneImportViewModel(ApplicationContextModule_ProvideContextFactory.provideContext(singletonCImpl.applicationContextModule), singletonCImpl.itemRepositoryProvider.get(), singletonCImpl.transactionRepositoryProvider.get(), singletonCImpl.presetOptionRepositoryProvider.get(), singletonCImpl.imageFileManagerProvider.get(), viewModelCImpl.savedStateHandle);

          case 10: // com.guzi.storage.ui.transaction.TransactionViewModel 
          return (T) new TransactionViewModel(singletonCImpl.transactionRepositoryProvider.get(), singletonCImpl.itemRepositoryProvider.get());

          case 11: // com.guzi.storage.ui.zipimport.ZipImportViewModel 
          return (T) new ZipImportViewModel(ApplicationContextModule_ProvideContextFactory.provideContext(singletonCImpl.applicationContextModule), singletonCImpl.itemRepositoryProvider.get(), singletonCImpl.transactionRepositoryProvider.get(), singletonCImpl.presetOptionRepositoryProvider.get(), singletonCImpl.imageFileManagerProvider.get());

          default: throw new AssertionError(id);
        }
      }
    }
  }

  private static final class ActivityRetainedCImpl extends GuZiStorageApp_HiltComponents.ActivityRetainedC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl = this;

    private Provider<ActivityRetainedLifecycle> provideActivityRetainedLifecycleProvider;

    private ActivityRetainedCImpl(SingletonCImpl singletonCImpl,
        SavedStateHandleHolder savedStateHandleHolderParam) {
      this.singletonCImpl = singletonCImpl;

      initialize(savedStateHandleHolderParam);

    }

    @SuppressWarnings("unchecked")
    private void initialize(final SavedStateHandleHolder savedStateHandleHolderParam) {
      this.provideActivityRetainedLifecycleProvider = DoubleCheck.provider(new SwitchingProvider<ActivityRetainedLifecycle>(singletonCImpl, activityRetainedCImpl, 0));
    }

    @Override
    public ActivityComponentBuilder activityComponentBuilder() {
      return new ActivityCBuilder(singletonCImpl, activityRetainedCImpl);
    }

    @Override
    public ActivityRetainedLifecycle getActivityRetainedLifecycle() {
      return provideActivityRetainedLifecycleProvider.get();
    }

    private static final class SwitchingProvider<T> implements Provider<T> {
      private final SingletonCImpl singletonCImpl;

      private final ActivityRetainedCImpl activityRetainedCImpl;

      private final int id;

      SwitchingProvider(SingletonCImpl singletonCImpl, ActivityRetainedCImpl activityRetainedCImpl,
          int id) {
        this.singletonCImpl = singletonCImpl;
        this.activityRetainedCImpl = activityRetainedCImpl;
        this.id = id;
      }

      @SuppressWarnings("unchecked")
      @Override
      public T get() {
        switch (id) {
          case 0: // dagger.hilt.android.ActivityRetainedLifecycle 
          return (T) ActivityRetainedComponentManager_LifecycleModule_ProvideActivityRetainedLifecycleFactory.provideActivityRetainedLifecycle();

          default: throw new AssertionError(id);
        }
      }
    }
  }

  private static final class ServiceCImpl extends GuZiStorageApp_HiltComponents.ServiceC {
    private final SingletonCImpl singletonCImpl;

    private final ServiceCImpl serviceCImpl = this;

    private ServiceCImpl(SingletonCImpl singletonCImpl, Service serviceParam) {
      this.singletonCImpl = singletonCImpl;


    }
  }

  private static final class SingletonCImpl extends GuZiStorageApp_HiltComponents.SingletonC {
    private final ApplicationContextModule applicationContextModule;

    private final SingletonCImpl singletonCImpl = this;

    private Provider<AppDatabase> provideDatabaseProvider;

    private Provider<ImageFileManager> imageFileManagerProvider;

    private Provider<ItemRepository> itemRepositoryProvider;

    private Provider<PresetOptionRepository> presetOptionRepositoryProvider;

    private Provider<UserPreferencesRepository> userPreferencesRepositoryProvider;

    private Provider<TransactionRepository> transactionRepositoryProvider;

    private Provider<ImageExportManager> imageExportManagerProvider;

    private Provider<BackupManager> backupManagerProvider;

    private SingletonCImpl(ApplicationContextModule applicationContextModuleParam) {
      this.applicationContextModule = applicationContextModuleParam;
      initialize(applicationContextModuleParam);

    }

    private ItemDao itemDao() {
      return AppModule_ProvideItemDaoFactory.provideItemDao(provideDatabaseProvider.get());
    }

    private PresetOptionDao presetOptionDao() {
      return AppModule_ProvidePresetOptionDaoFactory.providePresetOptionDao(provideDatabaseProvider.get());
    }

    private TransactionDao transactionDao() {
      return AppModule_ProvideTransactionDaoFactory.provideTransactionDao(provideDatabaseProvider.get());
    }

    @SuppressWarnings("unchecked")
    private void initialize(final ApplicationContextModule applicationContextModuleParam) {
      this.provideDatabaseProvider = DoubleCheck.provider(new SwitchingProvider<AppDatabase>(singletonCImpl, 1));
      this.imageFileManagerProvider = DoubleCheck.provider(new SwitchingProvider<ImageFileManager>(singletonCImpl, 2));
      this.itemRepositoryProvider = DoubleCheck.provider(new SwitchingProvider<ItemRepository>(singletonCImpl, 0));
      this.presetOptionRepositoryProvider = DoubleCheck.provider(new SwitchingProvider<PresetOptionRepository>(singletonCImpl, 3));
      this.userPreferencesRepositoryProvider = DoubleCheck.provider(new SwitchingProvider<UserPreferencesRepository>(singletonCImpl, 4));
      this.transactionRepositoryProvider = DoubleCheck.provider(new SwitchingProvider<TransactionRepository>(singletonCImpl, 5));
      this.imageExportManagerProvider = DoubleCheck.provider(new SwitchingProvider<ImageExportManager>(singletonCImpl, 6));
      this.backupManagerProvider = DoubleCheck.provider(new SwitchingProvider<BackupManager>(singletonCImpl, 7));
    }

    @Override
    public void injectGuZiStorageApp(GuZiStorageApp guZiStorageApp) {
    }

    @Override
    public Set<Boolean> getDisableFragmentGetContextFix() {
      return Collections.<Boolean>emptySet();
    }

    @Override
    public ActivityRetainedComponentBuilder retainedComponentBuilder() {
      return new ActivityRetainedCBuilder(singletonCImpl);
    }

    @Override
    public ServiceComponentBuilder serviceComponentBuilder() {
      return new ServiceCBuilder(singletonCImpl);
    }

    private static final class SwitchingProvider<T> implements Provider<T> {
      private final SingletonCImpl singletonCImpl;

      private final int id;

      SwitchingProvider(SingletonCImpl singletonCImpl, int id) {
        this.singletonCImpl = singletonCImpl;
        this.id = id;
      }

      @SuppressWarnings("unchecked")
      @Override
      public T get() {
        switch (id) {
          case 0: // com.guzi.storage.data.repository.ItemRepository 
          return (T) new ItemRepository(singletonCImpl.itemDao(), singletonCImpl.imageFileManagerProvider.get());

          case 1: // com.guzi.storage.data.local.AppDatabase 
          return (T) AppModule_ProvideDatabaseFactory.provideDatabase(ApplicationContextModule_ProvideContextFactory.provideContext(singletonCImpl.applicationContextModule));

          case 2: // com.guzi.storage.util.ImageFileManager 
          return (T) new ImageFileManager(ApplicationContextModule_ProvideContextFactory.provideContext(singletonCImpl.applicationContextModule));

          case 3: // com.guzi.storage.data.repository.PresetOptionRepository 
          return (T) new PresetOptionRepository(singletonCImpl.presetOptionDao());

          case 4: // com.guzi.storage.data.repository.UserPreferencesRepository 
          return (T) new UserPreferencesRepository(ApplicationContextModule_ProvideContextFactory.provideContext(singletonCImpl.applicationContextModule));

          case 5: // com.guzi.storage.data.repository.TransactionRepository 
          return (T) new TransactionRepository(singletonCImpl.transactionDao());

          case 6: // com.guzi.storage.util.ImageExportManager 
          return (T) new ImageExportManager(ApplicationContextModule_ProvideContextFactory.provideContext(singletonCImpl.applicationContextModule), singletonCImpl.imageFileManagerProvider.get(), singletonCImpl.userPreferencesRepositoryProvider.get());

          case 7: // com.guzi.storage.util.BackupManager 
          return (T) new BackupManager(ApplicationContextModule_ProvideContextFactory.provideContext(singletonCImpl.applicationContextModule), singletonCImpl.itemRepositoryProvider.get(), singletonCImpl.transactionRepositoryProvider.get());

          default: throw new AssertionError(id);
        }
      }
    }
  }
}
