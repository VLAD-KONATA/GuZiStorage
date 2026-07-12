package com.guzi.storage.ui.add;

import android.net.Uri;
import androidx.lifecycle.ViewModel;
import com.guzi.storage.data.repository.ItemRepository;
import com.guzi.storage.data.repository.PresetOptionRepository;
import com.guzi.storage.data.repository.UserPreferencesRepository;
import com.guzi.storage.domain.model.Item;
import com.guzi.storage.util.ImageFileManager;
import dagger.hilt.android.lifecycle.HiltViewModel;
import kotlinx.coroutines.flow.StateFlow;
import java.util.UUID;
import javax.inject.Inject;

/**
 * 表单状态
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0010 \n\u0002\b\u000f\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010$\n\u0002\b4\n\u0002\u0010\b\n\u0002\b\u0002\b\u0087\b\u0018\u00002\u00020\u0001B\u009d\u0002\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0005\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0007\u001a\u00020\u0003\u0012\u000e\b\u0002\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00030\t\u0012\u000e\b\u0002\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00030\t\u0012\b\b\u0002\u0010\u000b\u001a\u00020\u0003\u0012\b\b\u0002\u0010\f\u001a\u00020\u0003\u0012\b\b\u0002\u0010\r\u001a\u00020\u0003\u0012\u000e\b\u0002\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00030\t\u0012\u000e\b\u0002\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00030\t\u0012\u000e\b\u0002\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00030\t\u0012\u000e\b\u0002\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00030\t\u0012\b\b\u0002\u0010\u0012\u001a\u00020\u0003\u0012\u000e\b\u0002\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00030\t\u0012\b\b\u0002\u0010\u0014\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0015\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0016\u001a\u00020\u0003\u0012\u000e\b\u0002\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00030\t\u0012\b\b\u0002\u0010\u0018\u001a\u00020\u0019\u0012\u0014\b\u0002\u0010\u001a\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00030\u001b\u00a2\u0006\u0002\u0010\u001cJ\t\u00106\u001a\u00020\u0003H\u00c6\u0003J\t\u00107\u001a\u00020\u0003H\u00c6\u0003J\u000f\u00108\u001a\b\u0012\u0004\u0012\u00020\u00030\tH\u00c6\u0003J\u000f\u00109\u001a\b\u0012\u0004\u0012\u00020\u00030\tH\u00c6\u0003J\u000f\u0010:\u001a\b\u0012\u0004\u0012\u00020\u00030\tH\u00c6\u0003J\u000f\u0010;\u001a\b\u0012\u0004\u0012\u00020\u00030\tH\u00c6\u0003J\t\u0010<\u001a\u00020\u0003H\u00c6\u0003J\u000f\u0010=\u001a\b\u0012\u0004\u0012\u00020\u00030\tH\u00c6\u0003J\t\u0010>\u001a\u00020\u0003H\u00c6\u0003J\t\u0010?\u001a\u00020\u0003H\u00c6\u0003J\t\u0010@\u001a\u00020\u0003H\u00c6\u0003J\t\u0010A\u001a\u00020\u0003H\u00c6\u0003J\u000f\u0010B\u001a\b\u0012\u0004\u0012\u00020\u00030\tH\u00c6\u0003J\t\u0010C\u001a\u00020\u0019H\u00c6\u0003J\u0015\u0010D\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00030\u001bH\u00c6\u0003J\t\u0010E\u001a\u00020\u0003H\u00c6\u0003J\t\u0010F\u001a\u00020\u0003H\u00c6\u0003J\t\u0010G\u001a\u00020\u0003H\u00c6\u0003J\u000f\u0010H\u001a\b\u0012\u0004\u0012\u00020\u00030\tH\u00c6\u0003J\u000f\u0010I\u001a\b\u0012\u0004\u0012\u00020\u00030\tH\u00c6\u0003J\t\u0010J\u001a\u00020\u0003H\u00c6\u0003J\t\u0010K\u001a\u00020\u0003H\u00c6\u0003J\u00a1\u0002\u0010L\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00032\b\b\u0002\u0010\u0006\u001a\u00020\u00032\b\b\u0002\u0010\u0007\u001a\u00020\u00032\u000e\b\u0002\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00030\t2\u000e\b\u0002\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00030\t2\b\b\u0002\u0010\u000b\u001a\u00020\u00032\b\b\u0002\u0010\f\u001a\u00020\u00032\b\b\u0002\u0010\r\u001a\u00020\u00032\u000e\b\u0002\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00030\t2\u000e\b\u0002\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00030\t2\u000e\b\u0002\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00030\t2\u000e\b\u0002\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00030\t2\b\b\u0002\u0010\u0012\u001a\u00020\u00032\u000e\b\u0002\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00030\t2\b\b\u0002\u0010\u0014\u001a\u00020\u00032\b\b\u0002\u0010\u0015\u001a\u00020\u00032\b\b\u0002\u0010\u0016\u001a\u00020\u00032\u000e\b\u0002\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00030\t2\b\b\u0002\u0010\u0018\u001a\u00020\u00192\u0014\b\u0002\u0010\u001a\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00030\u001bH\u00c6\u0001J\u0013\u0010M\u001a\u00020\u00192\b\u0010N\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010O\u001a\u00020PH\u00d6\u0001J\t\u0010Q\u001a\u00020\u0003H\u00d6\u0001R\u0017\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00030\t\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u001eR\u0011\u0010\r\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001f\u0010 R\u0017\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00030\t\u00a2\u0006\b\n\u0000\u001a\u0004\b!\u0010\u001eR\u0017\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00030\t\u00a2\u0006\b\n\u0000\u001a\u0004\b\"\u0010\u001eR\u0017\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00030\t\u00a2\u0006\b\n\u0000\u001a\u0004\b#\u0010\u001eR\u0011\u0010\u0006\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b$\u0010 R\u0011\u0010\u0018\u001a\u00020\u0019\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010%R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b&\u0010 R\u0017\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00030\t\u00a2\u0006\b\n\u0000\u001a\u0004\b\'\u0010\u001eR\u0011\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b(\u0010 R\u0011\u0010\u0005\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b)\u0010 R\u0011\u0010\u0016\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b*\u0010 R\u0017\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00030\t\u00a2\u0006\b\n\u0000\u001a\u0004\b+\u0010\u001eR\u0011\u0010\f\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b,\u0010 R\u0011\u0010\u0007\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b-\u0010 R\u0011\u0010\u0015\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b.\u0010 R\u0017\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00030\t\u00a2\u0006\b\n\u0000\u001a\u0004\b/\u0010\u001eR\u0011\u0010\u000b\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b0\u0010 R\u0017\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00030\t\u00a2\u0006\b\n\u0000\u001a\u0004\b1\u0010\u001eR\u0011\u0010\u0012\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b2\u0010 R\u001d\u0010\u001a\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00030\u001b\u00a2\u0006\b\n\u0000\u001a\u0004\b3\u00104R\u0011\u0010\u0014\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b5\u0010 \u00a8\u0006R"}, d2 = {"Lcom/guzi/storage/ui/add/ItemFormState;", "", "itemId", "", "nameCn", "nameEn", "ipName", "producer", "originPlaces", "", "saleFormat", "sellMethod", "price", "category", "sizes", "materials", "characters", "artTypes", "status", "damageDetails", "version", "quantity", "note", "imagePaths", "isEditMode", "", "validationErrors", "", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/util/List;Ljava/util/List;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/util/List;Ljava/util/List;Ljava/util/List;Ljava/util/List;Ljava/lang/String;Ljava/util/List;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/util/List;ZLjava/util/Map;)V", "getArtTypes", "()Ljava/util/List;", "getCategory", "()Ljava/lang/String;", "getCharacters", "getDamageDetails", "getImagePaths", "getIpName", "()Z", "getItemId", "getMaterials", "getNameCn", "getNameEn", "getNote", "getOriginPlaces", "getPrice", "getProducer", "getQuantity", "getSaleFormat", "getSellMethod", "getSizes", "getStatus", "getValidationErrors", "()Ljava/util/Map;", "getVersion", "component1", "component10", "component11", "component12", "component13", "component14", "component15", "component16", "component17", "component18", "component19", "component2", "component20", "component21", "component22", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "equals", "other", "hashCode", "", "toString", "app_release"})
public final class ItemFormState {
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String itemId = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String nameCn = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String nameEn = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String ipName = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String producer = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<java.lang.String> originPlaces = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<java.lang.String> saleFormat = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String sellMethod = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String price = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String category = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<java.lang.String> sizes = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<java.lang.String> materials = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<java.lang.String> characters = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<java.lang.String> artTypes = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String status = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<java.lang.String> damageDetails = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String version = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String quantity = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String note = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<java.lang.String> imagePaths = null;
    private final boolean isEditMode = false;
    @org.jetbrains.annotations.NotNull()
    private final java.util.Map<java.lang.String, java.lang.String> validationErrors = null;
    
    public ItemFormState(@org.jetbrains.annotations.NotNull()
    java.lang.String itemId, @org.jetbrains.annotations.NotNull()
    java.lang.String nameCn, @org.jetbrains.annotations.NotNull()
    java.lang.String nameEn, @org.jetbrains.annotations.NotNull()
    java.lang.String ipName, @org.jetbrains.annotations.NotNull()
    java.lang.String producer, @org.jetbrains.annotations.NotNull()
    java.util.List<java.lang.String> originPlaces, @org.jetbrains.annotations.NotNull()
    java.util.List<java.lang.String> saleFormat, @org.jetbrains.annotations.NotNull()
    java.lang.String sellMethod, @org.jetbrains.annotations.NotNull()
    java.lang.String price, @org.jetbrains.annotations.NotNull()
    java.lang.String category, @org.jetbrains.annotations.NotNull()
    java.util.List<java.lang.String> sizes, @org.jetbrains.annotations.NotNull()
    java.util.List<java.lang.String> materials, @org.jetbrains.annotations.NotNull()
    java.util.List<java.lang.String> characters, @org.jetbrains.annotations.NotNull()
    java.util.List<java.lang.String> artTypes, @org.jetbrains.annotations.NotNull()
    java.lang.String status, @org.jetbrains.annotations.NotNull()
    java.util.List<java.lang.String> damageDetails, @org.jetbrains.annotations.NotNull()
    java.lang.String version, @org.jetbrains.annotations.NotNull()
    java.lang.String quantity, @org.jetbrains.annotations.NotNull()
    java.lang.String note, @org.jetbrains.annotations.NotNull()
    java.util.List<java.lang.String> imagePaths, boolean isEditMode, @org.jetbrains.annotations.NotNull()
    java.util.Map<java.lang.String, java.lang.String> validationErrors) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getItemId() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getNameCn() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getNameEn() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getIpName() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getProducer() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<java.lang.String> getOriginPlaces() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<java.lang.String> getSaleFormat() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getSellMethod() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getPrice() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getCategory() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<java.lang.String> getSizes() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<java.lang.String> getMaterials() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<java.lang.String> getCharacters() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<java.lang.String> getArtTypes() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getStatus() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<java.lang.String> getDamageDetails() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getVersion() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getQuantity() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getNote() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<java.lang.String> getImagePaths() {
        return null;
    }
    
    public final boolean isEditMode() {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.Map<java.lang.String, java.lang.String> getValidationErrors() {
        return null;
    }
    
    public ItemFormState() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component1() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component10() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<java.lang.String> component11() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<java.lang.String> component12() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<java.lang.String> component13() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<java.lang.String> component14() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component15() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<java.lang.String> component16() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component17() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component18() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component19() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component2() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<java.lang.String> component20() {
        return null;
    }
    
    public final boolean component21() {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.Map<java.lang.String, java.lang.String> component22() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component3() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component4() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component5() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<java.lang.String> component6() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<java.lang.String> component7() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component8() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component9() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.guzi.storage.ui.add.ItemFormState copy(@org.jetbrains.annotations.NotNull()
    java.lang.String itemId, @org.jetbrains.annotations.NotNull()
    java.lang.String nameCn, @org.jetbrains.annotations.NotNull()
    java.lang.String nameEn, @org.jetbrains.annotations.NotNull()
    java.lang.String ipName, @org.jetbrains.annotations.NotNull()
    java.lang.String producer, @org.jetbrains.annotations.NotNull()
    java.util.List<java.lang.String> originPlaces, @org.jetbrains.annotations.NotNull()
    java.util.List<java.lang.String> saleFormat, @org.jetbrains.annotations.NotNull()
    java.lang.String sellMethod, @org.jetbrains.annotations.NotNull()
    java.lang.String price, @org.jetbrains.annotations.NotNull()
    java.lang.String category, @org.jetbrains.annotations.NotNull()
    java.util.List<java.lang.String> sizes, @org.jetbrains.annotations.NotNull()
    java.util.List<java.lang.String> materials, @org.jetbrains.annotations.NotNull()
    java.util.List<java.lang.String> characters, @org.jetbrains.annotations.NotNull()
    java.util.List<java.lang.String> artTypes, @org.jetbrains.annotations.NotNull()
    java.lang.String status, @org.jetbrains.annotations.NotNull()
    java.util.List<java.lang.String> damageDetails, @org.jetbrains.annotations.NotNull()
    java.lang.String version, @org.jetbrains.annotations.NotNull()
    java.lang.String quantity, @org.jetbrains.annotations.NotNull()
    java.lang.String note, @org.jetbrains.annotations.NotNull()
    java.util.List<java.lang.String> imagePaths, boolean isEditMode, @org.jetbrains.annotations.NotNull()
    java.util.Map<java.lang.String, java.lang.String> validationErrors) {
        return null;
    }
    
    @java.lang.Override()
    public boolean equals(@org.jetbrains.annotations.Nullable()
    java.lang.Object other) {
        return false;
    }
    
    @java.lang.Override()
    public int hashCode() {
        return 0;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public java.lang.String toString() {
        return null;
    }
}