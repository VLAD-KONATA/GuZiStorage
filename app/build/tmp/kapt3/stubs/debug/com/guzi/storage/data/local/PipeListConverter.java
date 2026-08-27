package com.guzi.storage.data.local;

/**
 * 管道符分隔列表转换工具
 *
 * 用于在 Entity（String）和领域模型（List<String>）之间转换多选字段
 * 存储格式示例: "中|日" → ["中", "日"]
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\u0003\b\u00c7\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0014\u0010\u0005\u001a\u00020\u00042\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00040\u0007J\u0014\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00040\u00072\u0006\u0010\t\u001a\u00020\u0004R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\n"}, d2 = {"Lcom/guzi/storage/data/local/PipeListConverter;", "", "()V", "DELIMITER", "", "fromList", "list", "", "toList", "value", "app_debug"})
public final class PipeListConverter {
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String DELIMITER = "|";
    @org.jetbrains.annotations.NotNull()
    public static final com.guzi.storage.data.local.PipeListConverter INSTANCE = null;
    
    private PipeListConverter() {
        super();
    }
    
    /**
     * List<String> → 管道符分隔字符串
     * 空列表返回空字符串
     */
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String fromList(@org.jetbrains.annotations.NotNull()
    java.util.List<java.lang.String> list) {
        return null;
    }
    
    /**
     * 管道符分隔字符串 → List<String>
     * 空字符串返回空列表
     */
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<java.lang.String> toList(@org.jetbrains.annotations.NotNull()
    java.lang.String value) {
        return null;
    }
}