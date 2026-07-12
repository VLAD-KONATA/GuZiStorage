package com.guzi.storage.domain.model;

import com.guzi.storage.data.local.PipeListConverter;
import com.guzi.storage.data.local.entity.ItemEntity;

/**
 * 物品领域模型 - 业务层使用的物品数据结构
 *
 * 所有多选字段以 List<String> 表示，通过 PipeListConverter 与 Entity 互转
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000H\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0006\n\u0002\u0010\u0006\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\b\n\u0002\u0010$\n\u0000\n\u0002\u0010\t\n\u0002\b<\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0087\b\u0018\u0000 `2\u00020\u0001:\u0001`B\u00ab\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u0003\u0012\u0006\u0010\u0006\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\u0003\u0012\u0006\u0010\b\u001a\u00020\u0003\u0012\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\n\u0012\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\u0003\u0012\b\b\u0002\u0010\r\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u000e\u001a\u00020\u000f\u0012\n\b\u0002\u0010\u0010\u001a\u0004\u0018\u00010\u0003\u0012\u000e\b\u0002\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00030\u0012\u0012\u000e\b\u0002\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00030\u0012\u0012\u000e\b\u0002\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00030\u0012\u0012\u000e\b\u0002\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00030\u0012\u0012\u000e\b\u0002\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00030\u0012\u0012\u000e\b\u0002\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00030\u0012\u0012\u000e\b\u0002\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u00030\u0012\u0012\u000e\b\u0002\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u00030\u0012\u0012\u0014\b\u0002\u0010\u001a\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00010\u001b\u0012\b\b\u0002\u0010\u001c\u001a\u00020\u001d\u0012\b\b\u0002\u0010\u001e\u001a\u00020\u001d\u00a2\u0006\u0002\u0010\u001fJ\t\u0010@\u001a\u00020\u0003H\u00c6\u0003J\t\u0010A\u001a\u00020\u0003H\u00c6\u0003J\t\u0010B\u001a\u00020\u000fH\u00c6\u0003J\u000b\u0010C\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\u000f\u0010D\u001a\b\u0012\u0004\u0012\u00020\u00030\u0012H\u00c6\u0003J\u000f\u0010E\u001a\b\u0012\u0004\u0012\u00020\u00030\u0012H\u00c6\u0003J\u000f\u0010F\u001a\b\u0012\u0004\u0012\u00020\u00030\u0012H\u00c6\u0003J\u000f\u0010G\u001a\b\u0012\u0004\u0012\u00020\u00030\u0012H\u00c6\u0003J\u000f\u0010H\u001a\b\u0012\u0004\u0012\u00020\u00030\u0012H\u00c6\u0003J\u000f\u0010I\u001a\b\u0012\u0004\u0012\u00020\u00030\u0012H\u00c6\u0003J\u000f\u0010J\u001a\b\u0012\u0004\u0012\u00020\u00030\u0012H\u00c6\u0003J\t\u0010K\u001a\u00020\u0003H\u00c6\u0003J\u000f\u0010L\u001a\b\u0012\u0004\u0012\u00020\u00030\u0012H\u00c6\u0003J\u0015\u0010M\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00010\u001bH\u00c6\u0003J\t\u0010N\u001a\u00020\u001dH\u00c6\u0003J\t\u0010O\u001a\u00020\u001dH\u00c6\u0003J\u000b\u0010P\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\t\u0010Q\u001a\u00020\u0003H\u00c6\u0003J\u000b\u0010R\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\t\u0010S\u001a\u00020\u0003H\u00c6\u0003J\u0010\u0010T\u001a\u0004\u0018\u00010\nH\u00c6\u0003\u00a2\u0006\u0002\u00105J\u000b\u0010U\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\u000b\u0010V\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\u00bc\u0002\u0010W\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u00032\b\b\u0002\u0010\u0006\u001a\u00020\u00032\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\u00032\b\b\u0002\u0010\b\u001a\u00020\u00032\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\n2\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\u00032\b\b\u0002\u0010\r\u001a\u00020\u00032\b\b\u0002\u0010\u000e\u001a\u00020\u000f2\n\b\u0002\u0010\u0010\u001a\u0004\u0018\u00010\u00032\u000e\b\u0002\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00030\u00122\u000e\b\u0002\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00030\u00122\u000e\b\u0002\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00030\u00122\u000e\b\u0002\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00030\u00122\u000e\b\u0002\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00030\u00122\u000e\b\u0002\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00030\u00122\u000e\b\u0002\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u00030\u00122\u000e\b\u0002\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u00030\u00122\u0014\b\u0002\u0010\u001a\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00010\u001b2\b\b\u0002\u0010\u001c\u001a\u00020\u001d2\b\b\u0002\u0010\u001e\u001a\u00020\u001dH\u00c6\u0001\u00a2\u0006\u0002\u0010XJ\u0013\u0010Y\u001a\u00020Z2\b\u0010[\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010\\\u001a\u00020\u000fH\u00d6\u0001J\u0006\u0010]\u001a\u00020^J\t\u0010_\u001a\u00020\u0003H\u00d6\u0001R\u0017\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00030\u0012\u00a2\u0006\b\n\u0000\u001a\u0004\b \u0010!R\u0013\u0010\u000b\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\"\u0010#R\u0017\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00030\u0012\u00a2\u0006\b\n\u0000\u001a\u0004\b$\u0010!R\u0011\u0010\u001c\u001a\u00020\u001d\u00a2\u0006\b\n\u0000\u001a\u0004\b%\u0010&R\u0017\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u00030\u0012\u00a2\u0006\b\n\u0000\u001a\u0004\b\'\u0010!R\u0011\u0010(\u001a\u00020\u00038F\u00a2\u0006\u0006\u001a\u0004\b)\u0010#R\u001d\u0010\u001a\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00010\u001b\u00a2\u0006\b\n\u0000\u001a\u0004\b*\u0010+R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b,\u0010#R\u0017\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u00030\u0012\u00a2\u0006\b\n\u0000\u001a\u0004\b-\u0010!R\u0011\u0010\u0006\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b.\u0010#R\u0017\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00030\u0012\u00a2\u0006\b\n\u0000\u001a\u0004\b/\u0010!R\u0011\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b0\u0010#R\u0013\u0010\u0005\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b1\u0010#R\u0013\u0010\u0010\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b2\u0010#R\u0017\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00030\u0012\u00a2\u0006\b\n\u0000\u001a\u0004\b3\u0010!R\u0015\u0010\t\u001a\u0004\u0018\u00010\n\u00a2\u0006\n\n\u0002\u00106\u001a\u0004\b4\u00105R\u0013\u0010\u0007\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b7\u0010#R\u0011\u0010\u000e\u001a\u00020\u000f\u00a2\u0006\b\n\u0000\u001a\u0004\b8\u00109R\u0017\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00030\u0012\u00a2\u0006\b\n\u0000\u001a\u0004\b:\u0010!R\u0011\u0010\b\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b;\u0010#R\u0017\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00030\u0012\u00a2\u0006\b\n\u0000\u001a\u0004\b<\u0010!R\u0011\u0010\r\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b=\u0010#R\u0011\u0010\u001e\u001a\u00020\u001d\u00a2\u0006\b\n\u0000\u001a\u0004\b>\u0010&R\u0013\u0010\f\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b?\u0010#\u00a8\u0006a"}, d2 = {"Lcom/guzi/storage/domain/model/Item;", "", "id", "", "nameCn", "nameEn", "ipName", "producer", "sellMethod", "price", "", "category", "version", "status", "quantity", "", "note", "originPlaces", "", "saleFormat", "sizes", "materials", "characters", "artTypes", "damageDetails", "imagePaths", "extraAttributes", "", "createdAt", "", "updatedAt", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/Double;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;ILjava/lang/String;Ljava/util/List;Ljava/util/List;Ljava/util/List;Ljava/util/List;Ljava/util/List;Ljava/util/List;Ljava/util/List;Ljava/util/List;Ljava/util/Map;JJ)V", "getArtTypes", "()Ljava/util/List;", "getCategory", "()Ljava/lang/String;", "getCharacters", "getCreatedAt", "()J", "getDamageDetails", "displayName", "getDisplayName", "getExtraAttributes", "()Ljava/util/Map;", "getId", "getImagePaths", "getIpName", "getMaterials", "getNameCn", "getNameEn", "getNote", "getOriginPlaces", "getPrice", "()Ljava/lang/Double;", "Ljava/lang/Double;", "getProducer", "getQuantity", "()I", "getSaleFormat", "getSellMethod", "getSizes", "getStatus", "getUpdatedAt", "getVersion", "component1", "component10", "component11", "component12", "component13", "component14", "component15", "component16", "component17", "component18", "component19", "component2", "component20", "component21", "component22", "component23", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/Double;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;ILjava/lang/String;Ljava/util/List;Ljava/util/List;Ljava/util/List;Ljava/util/List;Ljava/util/List;Ljava/util/List;Ljava/util/List;Ljava/util/List;Ljava/util/Map;JJ)Lcom/guzi/storage/domain/model/Item;", "equals", "", "other", "hashCode", "toEntity", "Lcom/guzi/storage/data/local/entity/ItemEntity;", "toString", "Companion", "app_debug"})
public final class Item {
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String id = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String nameCn = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String nameEn = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String ipName = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String producer = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String sellMethod = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Double price = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String category = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String version = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String status = null;
    private final int quantity = 0;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String note = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<java.lang.String> originPlaces = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<java.lang.String> saleFormat = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<java.lang.String> sizes = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<java.lang.String> materials = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<java.lang.String> characters = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<java.lang.String> artTypes = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<java.lang.String> damageDetails = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<java.lang.String> imagePaths = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.Map<java.lang.String, java.lang.Object> extraAttributes = null;
    private final long createdAt = 0L;
    private final long updatedAt = 0L;
    @org.jetbrains.annotations.NotNull()
    public static final com.guzi.storage.domain.model.Item.Companion Companion = null;
    
    public Item(@org.jetbrains.annotations.NotNull()
    java.lang.String id, @org.jetbrains.annotations.NotNull()
    java.lang.String nameCn, @org.jetbrains.annotations.Nullable()
    java.lang.String nameEn, @org.jetbrains.annotations.NotNull()
    java.lang.String ipName, @org.jetbrains.annotations.Nullable()
    java.lang.String producer, @org.jetbrains.annotations.NotNull()
    java.lang.String sellMethod, @org.jetbrains.annotations.Nullable()
    java.lang.Double price, @org.jetbrains.annotations.Nullable()
    java.lang.String category, @org.jetbrains.annotations.Nullable()
    java.lang.String version, @org.jetbrains.annotations.NotNull()
    java.lang.String status, int quantity, @org.jetbrains.annotations.Nullable()
    java.lang.String note, @org.jetbrains.annotations.NotNull()
    java.util.List<java.lang.String> originPlaces, @org.jetbrains.annotations.NotNull()
    java.util.List<java.lang.String> saleFormat, @org.jetbrains.annotations.NotNull()
    java.util.List<java.lang.String> sizes, @org.jetbrains.annotations.NotNull()
    java.util.List<java.lang.String> materials, @org.jetbrains.annotations.NotNull()
    java.util.List<java.lang.String> characters, @org.jetbrains.annotations.NotNull()
    java.util.List<java.lang.String> artTypes, @org.jetbrains.annotations.NotNull()
    java.util.List<java.lang.String> damageDetails, @org.jetbrains.annotations.NotNull()
    java.util.List<java.lang.String> imagePaths, @org.jetbrains.annotations.NotNull()
    java.util.Map<java.lang.String, ? extends java.lang.Object> extraAttributes, long createdAt, long updatedAt) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getId() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getNameCn() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getNameEn() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getIpName() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getProducer() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getSellMethod() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double getPrice() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getCategory() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getVersion() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getStatus() {
        return null;
    }
    
    public final int getQuantity() {
        return 0;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getNote() {
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
    public final java.util.List<java.lang.String> getDamageDetails() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<java.lang.String> getImagePaths() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.Map<java.lang.String, java.lang.Object> getExtraAttributes() {
        return null;
    }
    
    public final long getCreatedAt() {
        return 0L;
    }
    
    public final long getUpdatedAt() {
        return 0L;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getDisplayName() {
        return null;
    }
    
    /**
     * 转换为 Entity 存储
     */
    @org.jetbrains.annotations.NotNull()
    public final com.guzi.storage.data.local.entity.ItemEntity toEntity() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component1() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component10() {
        return null;
    }
    
    public final int component11() {
        return 0;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component12() {
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
    public final java.util.List<java.lang.String> component15() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<java.lang.String> component16() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<java.lang.String> component17() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<java.lang.String> component18() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<java.lang.String> component19() {
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
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.Map<java.lang.String, java.lang.Object> component21() {
        return null;
    }
    
    public final long component22() {
        return 0L;
    }
    
    public final long component23() {
        return 0L;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component3() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component4() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component5() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component6() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double component7() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component8() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component9() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.guzi.storage.domain.model.Item copy(@org.jetbrains.annotations.NotNull()
    java.lang.String id, @org.jetbrains.annotations.NotNull()
    java.lang.String nameCn, @org.jetbrains.annotations.Nullable()
    java.lang.String nameEn, @org.jetbrains.annotations.NotNull()
    java.lang.String ipName, @org.jetbrains.annotations.Nullable()
    java.lang.String producer, @org.jetbrains.annotations.NotNull()
    java.lang.String sellMethod, @org.jetbrains.annotations.Nullable()
    java.lang.Double price, @org.jetbrains.annotations.Nullable()
    java.lang.String category, @org.jetbrains.annotations.Nullable()
    java.lang.String version, @org.jetbrains.annotations.NotNull()
    java.lang.String status, int quantity, @org.jetbrains.annotations.Nullable()
    java.lang.String note, @org.jetbrains.annotations.NotNull()
    java.util.List<java.lang.String> originPlaces, @org.jetbrains.annotations.NotNull()
    java.util.List<java.lang.String> saleFormat, @org.jetbrains.annotations.NotNull()
    java.util.List<java.lang.String> sizes, @org.jetbrains.annotations.NotNull()
    java.util.List<java.lang.String> materials, @org.jetbrains.annotations.NotNull()
    java.util.List<java.lang.String> characters, @org.jetbrains.annotations.NotNull()
    java.util.List<java.lang.String> artTypes, @org.jetbrains.annotations.NotNull()
    java.util.List<java.lang.String> damageDetails, @org.jetbrains.annotations.NotNull()
    java.util.List<java.lang.String> imagePaths, @org.jetbrains.annotations.NotNull()
    java.util.Map<java.lang.String, ? extends java.lang.Object> extraAttributes, long createdAt, long updatedAt) {
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
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006\u00a8\u0006\u0007"}, d2 = {"Lcom/guzi/storage/domain/model/Item$Companion;", "", "()V", "fromEntity", "Lcom/guzi/storage/domain/model/Item;", "entity", "Lcom/guzi/storage/data/local/entity/ItemEntity;", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
        
        /**
         * 从 Entity 构建领域模型
         */
        @org.jetbrains.annotations.NotNull()
        public final com.guzi.storage.domain.model.Item fromEntity(@org.jetbrains.annotations.NotNull()
        com.guzi.storage.data.local.entity.ItemEntity entity) {
            return null;
        }
    }
}