package com.guzi.storage.domain.model;

import com.guzi.storage.data.local.PipeListConverter;
import com.guzi.storage.data.local.entity.TransactionEntity;

/**
 * 交易类型枚举
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0007\b\u0086\u0081\u0002\u0018\u0000 \t2\b\u0012\u0004\u0012\u00020\u00000\u0001:\u0001\tB\u000f\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006j\u0002\b\u0007j\u0002\b\b\u00a8\u0006\n"}, d2 = {"Lcom/guzi/storage/domain/model/TransactionType;", "", "value", "", "(Ljava/lang/String;ILjava/lang/String;)V", "getValue", "()Ljava/lang/String;", "BUY", "SELL", "Companion", "app_release"})
public enum TransactionType {
    /*public static final*/ BUY /* = new BUY(null) */,
    /*public static final*/ SELL /* = new SELL(null) */;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String value = null;
    @org.jetbrains.annotations.NotNull()
    public static final com.guzi.storage.domain.model.TransactionType.Companion Companion = null;
    
    TransactionType(java.lang.String value) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getValue() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public static kotlin.enums.EnumEntries<com.guzi.storage.domain.model.TransactionType> getEntries() {
        return null;
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006\u00a8\u0006\u0007"}, d2 = {"Lcom/guzi/storage/domain/model/TransactionType$Companion;", "", "()V", "fromValue", "Lcom/guzi/storage/domain/model/TransactionType;", "value", "", "app_release"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.guzi.storage.domain.model.TransactionType fromValue(@org.jetbrains.annotations.NotNull()
        java.lang.String value) {
            return null;
        }
    }
}