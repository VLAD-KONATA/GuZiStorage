package com.guzi.storage.ui.add;

import android.net.Uri;
import androidx.lifecycle.ViewModel;
import com.guzi.storage.data.repository.ItemRepository;
import com.guzi.storage.data.repository.PresetOptionRepository;
import com.guzi.storage.data.repository.UserPreferencesRepository;
import com.guzi.storage.domain.model.Item;
import com.guzi.storage.util.CurrencyUtils;
import com.guzi.storage.util.DateUtils;
import com.guzi.storage.util.ImageFileManager;
import com.guzi.storage.util.ItemValueParser;
import com.guzi.storage.data.local.PipeListConverter;
import dagger.hilt.android.lifecycle.HiltViewModel;
import kotlinx.coroutines.flow.StateFlow;
import com.guzi.storage.util.IdGenerator;
import javax.inject.Inject;

/**
 * 添加/编辑物品表单的 ViewModel
 *
 * 管理表单所有字段的状态、校验逻辑和提交操作
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000p\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\"\n\u0002\b\u0011\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u000b\n\u0002\u0010\b\n\u0002\b%\b\u0007\u0018\u00002\u00020\u0001B\'\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u00a2\u0006\u0002\u0010\nJ\u001c\u00108\u001a\u0002092\f\u0010:\u001a\b\u0012\u0004\u0012\u00020;0\r2\u0006\u0010<\u001a\u00020=J\u0018\u0010>\u001a\u0002092\u0006\u0010?\u001a\u00020\u000e2\u0006\u0010@\u001a\u00020\u000eH\u0007J\u0006\u0010A\u001a\u000209J\u000e\u0010B\u001a\u0002092\u0006\u0010C\u001a\u00020\u000eJ\u000e\u0010D\u001a\u0002092\u0006\u0010E\u001a\u00020\u000eJ\b\u0010F\u001a\u000209H\u0002J\u0016\u0010G\u001a\u0002092\u0006\u0010H\u001a\u00020I2\u0006\u0010J\u001a\u00020IJ\u000e\u0010K\u001a\u0002092\u0006\u0010L\u001a\u00020\u000eJ\u0006\u0010M\u001a\u000209J\u0016\u0010N\u001a\u0002092\u0006\u0010O\u001a\u00020\u0012H\u0082@\u00a2\u0006\u0002\u0010PJ\u000e\u0010Q\u001a\u0002092\u0006\u0010R\u001a\u00020\u000eJ\u001c\u0010S\u001a\b\u0012\u0004\u0012\u00020\u000e0\r2\f\u0010T\u001a\b\u0012\u0004\u0012\u00020\u000e0\rH\u0002J\u0014\u0010U\u001a\u0002092\f\u0010@\u001a\b\u0012\u0004\u0012\u00020\u000e0\rJ\u0014\u0010V\u001a\u0002092\f\u0010@\u001a\b\u0012\u0004\u0012\u00020\u000e0\rJ\u0014\u0010W\u001a\u0002092\f\u0010@\u001a\b\u0012\u0004\u0012\u00020\u000e0\rJ\u000e\u0010X\u001a\u0002092\u0006\u0010@\u001a\u00020\u000eJ\u0014\u0010Y\u001a\u0002092\f\u0010@\u001a\b\u0012\u0004\u0012\u00020\u000e0\rJ\u0014\u0010Z\u001a\u0002092\f\u0010@\u001a\b\u0012\u0004\u0012\u00020\u000e0\rJ\u000e\u0010[\u001a\u0002092\u0006\u0010@\u001a\u00020=J\u000e\u0010\\\u001a\u0002092\u0006\u0010@\u001a\u00020=J\u0014\u0010]\u001a\u0002092\f\u0010@\u001a\b\u0012\u0004\u0012\u00020\u000e0\rJ\u000e\u0010^\u001a\u0002092\u0006\u0010@\u001a\u00020\u000eJ\u000e\u0010_\u001a\u0002092\u0006\u0010@\u001a\u00020\u000eJ\u000e\u0010`\u001a\u0002092\u0006\u0010@\u001a\u00020\u000eJ\u0014\u0010a\u001a\u0002092\f\u0010@\u001a\b\u0012\u0004\u0012\u00020\u000e0\rJ\u000e\u0010b\u001a\u0002092\u0006\u0010@\u001a\u00020\u000eJ\u000e\u0010c\u001a\u0002092\u0006\u0010@\u001a\u00020\u000eJ\u0014\u0010d\u001a\u0002092\f\u0010@\u001a\b\u0012\u0004\u0012\u00020\u000e0\rJ\u000e\u0010e\u001a\u0002092\u0006\u0010@\u001a\u00020\u000eJ\u0014\u0010f\u001a\u0002092\f\u0010@\u001a\b\u0012\u0004\u0012\u00020\u000e0\rJ\u000e\u0010g\u001a\u0002092\u0006\u0010@\u001a\u00020\u000eJ\u0014\u0010h\u001a\u0002092\f\u0010@\u001a\b\u0012\u0004\u0012\u00020\u000e0\rJ\u000e\u0010i\u001a\u0002092\u0006\u0010@\u001a\u00020\u000eJ\u0014\u0010j\u001a\u0002092\f\u0010@\u001a\b\u0012\u0004\u0012\u00020\u000e0\rJ\u000e\u0010k\u001a\u0002092\u0006\u0010@\u001a\u00020\u000eJ\u000e\u0010l\u001a\u0002092\u0006\u0010@\u001a\u00020\u000eJ\u0006\u0010m\u001a\u00020=R\u001a\u0010\u000b\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000e0\r0\fX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u0010\u000f\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000e0\r0\fX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u0010\u0010\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000e0\r0\fX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00120\fX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u0010\u0013\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000e0\r0\fX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u0010\u0014\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000e0\r0\fX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u0010\u0015\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000e0\r0\fX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u0010\u0016\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000e0\r0\fX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u0010\u0017\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000e0\r0\fX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0016\u0010\u0018\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00190\fX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u0010\u001a\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000e0\r0\fX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u0010\u001b\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000e0\r0\fX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001d\u0010\u001c\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000e0\r0\u001d\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\u001fR\u001d\u0010 \u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000e0\r0\u001d\u00a2\u0006\b\n\u0000\u001a\u0004\b!\u0010\u001fR\u001d\u0010\"\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000e0\r0\u001d\u00a2\u0006\b\n\u0000\u001a\u0004\b#\u0010\u001fR\u0017\u0010$\u001a\b\u0012\u0004\u0012\u00020\u00120\u001d\u00a2\u0006\b\n\u0000\u001a\u0004\b%\u0010\u001fR\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010&\u001a\b\u0012\u0004\u0012\u00020\u000e0\'X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001d\u0010(\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000e0\r0\u001d\u00a2\u0006\b\n\u0000\u001a\u0004\b)\u0010\u001fR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001d\u0010*\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000e0\r0\u001d\u00a2\u0006\b\n\u0000\u001a\u0004\b+\u0010\u001fR\u001d\u0010,\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000e0\r0\u001d\u00a2\u0006\b\n\u0000\u001a\u0004\b-\u0010\u001fR\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001d\u0010.\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000e0\r0\u001d\u00a2\u0006\b\n\u0000\u001a\u0004\b/\u0010\u001fR\u001d\u00100\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000e0\r0\u001d\u00a2\u0006\b\n\u0000\u001a\u0004\b1\u0010\u001fR\u0019\u00102\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00190\u001d\u00a2\u0006\b\n\u0000\u001a\u0004\b3\u0010\u001fR\u001d\u00104\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000e0\r0\u001d\u00a2\u0006\b\n\u0000\u001a\u0004\b5\u0010\u001fR\u001d\u00106\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000e0\r0\u001d\u00a2\u0006\b\n\u0000\u001a\u0004\b7\u0010\u001fR\u000e\u0010\b\u001a\u00020\tX\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006n"}, d2 = {"Lcom/guzi/storage/ui/add/AddItemViewModel;", "Landroidx/lifecycle/ViewModel;", "itemRepository", "Lcom/guzi/storage/data/repository/ItemRepository;", "presetOptionRepository", "Lcom/guzi/storage/data/repository/PresetOptionRepository;", "imageFileManager", "Lcom/guzi/storage/util/ImageFileManager;", "userPreferencesRepository", "Lcom/guzi/storage/data/repository/UserPreferencesRepository;", "(Lcom/guzi/storage/data/repository/ItemRepository;Lcom/guzi/storage/data/repository/PresetOptionRepository;Lcom/guzi/storage/util/ImageFileManager;Lcom/guzi/storage/data/repository/UserPreferencesRepository;)V", "_artTypeOptions", "Lkotlinx/coroutines/flow/MutableStateFlow;", "", "", "_categoryOptions", "_characterOptions", "_formState", "Lcom/guzi/storage/ui/add/ItemFormState;", "_ipOptions", "_materialOptions", "_originPlaceOptions", "_producerOptions", "_saleFormatOptions", "_saveResult", "Lcom/guzi/storage/ui/add/SaveResult;", "_sellMethodOptions", "_sizeOptions", "artTypeOptions", "Lkotlinx/coroutines/flow/StateFlow;", "getArtTypeOptions", "()Lkotlinx/coroutines/flow/StateFlow;", "categoryOptions", "getCategoryOptions", "characterOptions", "getCharacterOptions", "formState", "getFormState", "initialImagePaths", "", "ipOptions", "getIpOptions", "materialOptions", "getMaterialOptions", "originPlaceOptions", "getOriginPlaceOptions", "producerOptions", "getProducerOptions", "saleFormatOptions", "getSaleFormatOptions", "saveResult", "getSaveResult", "sellMethodOptions", "getSellMethodOptions", "sizeOptions", "getSizeOptions", "addImages", "", "uris", "Landroid/net/Uri;", "compress", "", "addNewPresetOption", "attributeName", "value", "clearSaveResult", "loadItem", "itemId", "loadItemAsCopy", "sourceItemId", "loadPresetOptions", "moveImage", "fromIndex", "", "toIndex", "removeImage", "path", "save", "saveNewPresetsFromState", "state", "(Lcom/guzi/storage/ui/add/ItemFormState;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "setDefaultIpName", "ipName", "sortByFrequency", "values", "updateArtTypes", "updateCategories", "updateCharacters", "updateCurrency", "updateDamageDetails", "updateIpNames", "updateIsNoPrice", "updateIsOriginalBagUnknown", "updateMaterials", "updateNameCn", "updateNameEn", "updateNote", "updateOriginPlaces", "updateOriginalBagQuantity", "updatePrice", "updateProducers", "updateQuantity", "updateReferenceLinks", "updateReleaseDate", "updateSaleFormat", "updateSellMethod", "updateSizes", "updateStatus", "updateVersion", "validate", "app_debug"})
@dagger.hilt.android.lifecycle.HiltViewModel()
public final class AddItemViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final com.guzi.storage.data.repository.ItemRepository itemRepository = null;
    @org.jetbrains.annotations.NotNull()
    private final com.guzi.storage.data.repository.PresetOptionRepository presetOptionRepository = null;
    @org.jetbrains.annotations.NotNull()
    private final com.guzi.storage.util.ImageFileManager imageFileManager = null;
    @org.jetbrains.annotations.NotNull()
    private final com.guzi.storage.data.repository.UserPreferencesRepository userPreferencesRepository = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<com.guzi.storage.ui.add.ItemFormState> _formState = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.guzi.storage.ui.add.ItemFormState> formState = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.util.List<java.lang.String>> _ipOptions = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.util.List<java.lang.String>> ipOptions = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.util.List<java.lang.String>> _producerOptions = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.util.List<java.lang.String>> producerOptions = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.util.List<java.lang.String>> _originPlaceOptions = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.util.List<java.lang.String>> originPlaceOptions = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.util.List<java.lang.String>> _saleFormatOptions = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.util.List<java.lang.String>> saleFormatOptions = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.util.List<java.lang.String>> _sellMethodOptions = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.util.List<java.lang.String>> sellMethodOptions = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.util.List<java.lang.String>> _categoryOptions = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.util.List<java.lang.String>> categoryOptions = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.util.List<java.lang.String>> _sizeOptions = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.util.List<java.lang.String>> sizeOptions = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.util.List<java.lang.String>> _materialOptions = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.util.List<java.lang.String>> materialOptions = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.util.List<java.lang.String>> _characterOptions = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.util.List<java.lang.String>> characterOptions = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.util.List<java.lang.String>> _artTypeOptions = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.util.List<java.lang.String>> artTypeOptions = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<com.guzi.storage.ui.add.SaveResult> _saveResult = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.guzi.storage.ui.add.SaveResult> saveResult = null;
    @org.jetbrains.annotations.NotNull()
    private java.util.Set<java.lang.String> initialImagePaths;
    
    @javax.inject.Inject()
    public AddItemViewModel(@org.jetbrains.annotations.NotNull()
    com.guzi.storage.data.repository.ItemRepository itemRepository, @org.jetbrains.annotations.NotNull()
    com.guzi.storage.data.repository.PresetOptionRepository presetOptionRepository, @org.jetbrains.annotations.NotNull()
    com.guzi.storage.util.ImageFileManager imageFileManager, @org.jetbrains.annotations.NotNull()
    com.guzi.storage.data.repository.UserPreferencesRepository userPreferencesRepository) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.guzi.storage.ui.add.ItemFormState> getFormState() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.util.List<java.lang.String>> getIpOptions() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.util.List<java.lang.String>> getProducerOptions() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.util.List<java.lang.String>> getOriginPlaceOptions() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.util.List<java.lang.String>> getSaleFormatOptions() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.util.List<java.lang.String>> getSellMethodOptions() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.util.List<java.lang.String>> getCategoryOptions() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.util.List<java.lang.String>> getSizeOptions() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.util.List<java.lang.String>> getMaterialOptions() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.util.List<java.lang.String>> getCharacterOptions() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.util.List<java.lang.String>> getArtTypeOptions() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.guzi.storage.ui.add.SaveResult> getSaveResult() {
        return null;
    }
    
    private final void loadPresetOptions() {
    }
    
    private final java.util.List<java.lang.String> sortByFrequency(java.util.List<java.lang.String> values) {
        return null;
    }
    
    /**
     * 添加模式下预填 IP 名（从分组页进入时）
     */
    public final void setDefaultIpName(@org.jetbrains.annotations.NotNull()
    java.lang.String ipName) {
    }
    
    /**
     * 加载已有物品数据（编辑模式）
     */
    public final void loadItem(@org.jetbrains.annotations.NotNull()
    java.lang.String itemId) {
    }
    
    /**
     * 加载已有物品数据作为副本（复制后编辑）
     * 复制所有字段和图片，但使用新 ID，以新建模式保存
     */
    public final void loadItemAsCopy(@org.jetbrains.annotations.NotNull()
    java.lang.String sourceItemId) {
    }
    
    public final void updateNameCn(@org.jetbrains.annotations.NotNull()
    java.lang.String value) {
    }
    
    public final void updateNameEn(@org.jetbrains.annotations.NotNull()
    java.lang.String value) {
    }
    
    public final void updateIpNames(@org.jetbrains.annotations.NotNull()
    java.util.List<java.lang.String> value) {
    }
    
    public final void updateProducers(@org.jetbrains.annotations.NotNull()
    java.util.List<java.lang.String> value) {
    }
    
    public final void updateOriginPlaces(@org.jetbrains.annotations.NotNull()
    java.util.List<java.lang.String> value) {
    }
    
    public final void updateSaleFormat(@org.jetbrains.annotations.NotNull()
    java.util.List<java.lang.String> value) {
    }
    
    public final void updateSellMethod(@org.jetbrains.annotations.NotNull()
    java.lang.String value) {
    }
    
    public final void updatePrice(@org.jetbrains.annotations.NotNull()
    java.lang.String value) {
    }
    
    public final void updateCurrency(@org.jetbrains.annotations.NotNull()
    java.lang.String value) {
    }
    
    public final void updateIsNoPrice(boolean value) {
    }
    
    public final void updateReleaseDate(@org.jetbrains.annotations.NotNull()
    java.lang.String value) {
    }
    
    public final void updateCategories(@org.jetbrains.annotations.NotNull()
    java.util.List<java.lang.String> value) {
    }
    
    public final void updateSizes(@org.jetbrains.annotations.NotNull()
    java.util.List<java.lang.String> value) {
    }
    
    public final void updateMaterials(@org.jetbrains.annotations.NotNull()
    java.util.List<java.lang.String> value) {
    }
    
    public final void updateCharacters(@org.jetbrains.annotations.NotNull()
    java.util.List<java.lang.String> value) {
    }
    
    public final void updateArtTypes(@org.jetbrains.annotations.NotNull()
    java.util.List<java.lang.String> value) {
    }
    
    public final void updateStatus(@org.jetbrains.annotations.NotNull()
    java.lang.String value) {
    }
    
    public final void updateDamageDetails(@org.jetbrains.annotations.NotNull()
    java.util.List<java.lang.String> value) {
    }
    
    public final void updateVersion(@org.jetbrains.annotations.NotNull()
    java.lang.String value) {
    }
    
    public final void updateQuantity(@org.jetbrains.annotations.NotNull()
    java.lang.String value) {
    }
    
    public final void updateOriginalBagQuantity(@org.jetbrains.annotations.NotNull()
    java.lang.String value) {
    }
    
    public final void updateIsOriginalBagUnknown(boolean value) {
    }
    
    public final void updateNote(@org.jetbrains.annotations.NotNull()
    java.lang.String value) {
    }
    
    public final void updateReferenceLinks(@org.jetbrains.annotations.NotNull()
    java.util.List<java.lang.String> value) {
    }
    
    /**
     * 批量添加图片
     *
     * @param uris 图片 URI 列表
     * @param compress true=压缩存储，false=存储原图
     */
    public final void addImages(@org.jetbrains.annotations.NotNull()
    java.util.List<? extends android.net.Uri> uris, boolean compress) {
    }
    
    /**
     * 移除图片
     *
     * 数据库已引用的旧图仅标记待删除，保存成功后才删除物理文件（不保存则不生效）；
     * 本次会话新增的图片数据库尚未引用，直接删除物理文件
     */
    public final void removeImage(@org.jetbrains.annotations.NotNull()
    java.lang.String path) {
    }
    
    /**
     * 调整图片顺序（编辑时长按拖拽排序）
     */
    public final void moveImage(int fromIndex, int toIndex) {
    }
    
    /**
     * 添加新预设选项（已不再使用，保留空实现防止引用报错）
     */
    @java.lang.Deprecated()
    public final void addNewPresetOption(@org.jetbrains.annotations.NotNull()
    java.lang.String attributeName, @org.jetbrains.annotations.NotNull()
    java.lang.String value) {
    }
    
    public final boolean validate() {
        return false;
    }
    
    public final void save() {
    }
    
    public final void clearSaveResult() {
    }
    
    /**
     * 保存成功后，把表单中尚未在预设库里的新值写入
     */
    private final java.lang.Object saveNewPresetsFromState(com.guzi.storage.ui.add.ItemFormState state, kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
}