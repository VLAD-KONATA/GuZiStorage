package com.guzi.storage.ui.home;

/**
 * 分组卡片排序选项（单选下拉）
 *
 * 分组卡片只有两个维度可排序：分组名 与 件数
 * 泛型 apply 可同时用于 HomeScreen(IpGroupDisplay) 和 GroupScreen(SubGroupDisplay)
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0010 \n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0006\b\u0086\u0081\u0002\u0018\u0000 \u00132\b\u0012\u0004\u0012\u00020\u00000\u0001:\u0001\u0013B\u000f\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004JH\u0010\u0007\u001a\b\u0012\u0004\u0012\u0002H\t0\b\"\u0004\b\u0000\u0010\t2\f\u0010\n\u001a\b\u0012\u0004\u0012\u0002H\t0\b2\u0012\u0010\u000b\u001a\u000e\u0012\u0004\u0012\u0002H\t\u0012\u0004\u0012\u00020\u00030\f2\u0012\u0010\r\u001a\u000e\u0012\u0004\u0012\u0002H\t\u0012\u0004\u0012\u00020\u000e0\fR\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006j\u0002\b\u000fj\u0002\b\u0010j\u0002\b\u0011j\u0002\b\u0012\u00a8\u0006\u0014"}, d2 = {"Lcom/guzi/storage/ui/home/HomeSortOption;", "", "displayLabel", "", "(Ljava/lang/String;ILjava/lang/String;)V", "getDisplayLabel", "()Ljava/lang/String;", "apply", "", "T", "groups", "attrValueSelector", "Lkotlin/Function1;", "countSelector", "", "NAME_ASC", "NAME_DESC", "COUNT_ASC", "COUNT_DESC", "Companion", "app_release"})
public enum HomeSortOption {
    /*public static final*/ NAME_ASC /* = new NAME_ASC(null) */,
    /*public static final*/ NAME_DESC /* = new NAME_DESC(null) */,
    /*public static final*/ COUNT_ASC /* = new COUNT_ASC(null) */,
    /*public static final*/ COUNT_DESC /* = new COUNT_DESC(null) */;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String displayLabel = null;
    @org.jetbrains.annotations.NotNull()
    private static final com.guzi.storage.ui.home.HomeSortOption DEFAULT = com.guzi.storage.ui.home.HomeSortOption.COUNT_DESC;
    @org.jetbrains.annotations.NotNull()
    public static final com.guzi.storage.ui.home.HomeSortOption.Companion Companion = null;
    
    HomeSortOption(java.lang.String displayLabel) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getDisplayLabel() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final <T extends java.lang.Object>java.util.List<T> apply(@org.jetbrains.annotations.NotNull()
    java.util.List<? extends T> groups, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super T, java.lang.String> attrValueSelector, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super T, java.lang.Integer> countSelector) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public static kotlin.enums.EnumEntries<com.guzi.storage.ui.home.HomeSortOption> getEntries() {
        return null;
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u0011\u0010\u0003\u001a\u00020\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006\u00a8\u0006\u0007"}, d2 = {"Lcom/guzi/storage/ui/home/HomeSortOption$Companion;", "", "()V", "DEFAULT", "Lcom/guzi/storage/ui/home/HomeSortOption;", "getDEFAULT", "()Lcom/guzi/storage/ui/home/HomeSortOption;", "app_release"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.guzi.storage.ui.home.HomeSortOption getDEFAULT() {
            return null;
        }
    }
}