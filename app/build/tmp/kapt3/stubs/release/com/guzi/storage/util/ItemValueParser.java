package com.guzi.storage.util;

/**
 * 谷子字段解析规则。
 *
 * CSV/ZIP 导入和手动保存共用这里，避免同一字段在不同入口表现不一致。
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010 \n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\b\u00c7\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006J\u0014\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00060\b2\u0006\u0010\u0005\u001a\u00020\u0006J\u0015\u0010\t\u001a\u0004\u0018\u00010\n2\u0006\u0010\u0005\u001a\u00020\u0006\u00a2\u0006\u0002\u0010\u000bJ\u000e\u0010\f\u001a\u00020\n2\u0006\u0010\u0005\u001a\u00020\u0006\u00a8\u0006\r"}, d2 = {"Lcom/guzi/storage/util/ItemValueParser;", "", "()V", "hasPipe", "", "value", "", "parseMultiValue", "", "parseOriginalBagQuantity", "", "(Ljava/lang/String;)Ljava/lang/Integer;", "parseQuantity", "app_release"})
public final class ItemValueParser {
    @org.jetbrains.annotations.NotNull()
    public static final com.guzi.storage.util.ItemValueParser INSTANCE = null;
    
    private ItemValueParser() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<java.lang.String> parseMultiValue(@org.jetbrains.annotations.NotNull()
    java.lang.String value) {
        return null;
    }
    
    public final int parseQuantity(@org.jetbrains.annotations.NotNull()
    java.lang.String value) {
        return 0;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Integer parseOriginalBagQuantity(@org.jetbrains.annotations.NotNull()
    java.lang.String value) {
        return null;
    }
    
    public final boolean hasPipe(@org.jetbrains.annotations.NotNull()
    java.lang.String value) {
        return false;
    }
}