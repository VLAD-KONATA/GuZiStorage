package com.guzi.storage.ui.transaction;

import androidx.compose.foundation.layout.Arrangement;
import androidx.compose.material.icons.Icons;
import androidx.compose.material3.CardDefaults;
import androidx.compose.material3.ExperimentalMaterial3Api;
import androidx.compose.material3.SuggestionChipDefaults;
import androidx.compose.runtime.Composable;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.text.font.FontWeight;
import com.guzi.storage.domain.model.Transaction;
import com.guzi.storage.domain.model.TransactionType;
import com.guzi.storage.ui.components.ConfirmDialog;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000,\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u001a,\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00010\u00052\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00010\u0005H\u0003\u001aV\u0010\u0007\u001a\u00020\u00012\u0006\u0010\b\u001a\u00020\t2\u000e\b\u0002\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00010\u00052\u0014\b\u0002\u0010\u000b\u001a\u000e\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\u00010\f2\u0014\b\u0002\u0010\r\u001a\u000e\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\u00010\f2\b\b\u0002\u0010\u000e\u001a\u00020\u000fH\u0007\u00a8\u0006\u0010"}, d2 = {"TransactionCard", "", "transaction", "Lcom/guzi/storage/domain/model/Transaction;", "onEditClick", "Lkotlin/Function0;", "onDeleteClick", "TransactionScreen", "itemId", "", "onNavigateBack", "onAddTransaction", "Lkotlin/Function1;", "onEditTransaction", "viewModel", "Lcom/guzi/storage/ui/transaction/TransactionViewModel;", "app_debug"})
public final class TransactionScreenKt {
    
    /**
     * 交易记录列表页 - 展示某物品的所有购入/卖出记录
     */
    @kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class})
    @androidx.compose.runtime.Composable()
    public static final void TransactionScreen(@org.jetbrains.annotations.NotNull()
    java.lang.String itemId, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onNavigateBack, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onAddTransaction, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onEditTransaction, @org.jetbrains.annotations.NotNull()
    com.guzi.storage.ui.transaction.TransactionViewModel viewModel) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void TransactionCard(com.guzi.storage.domain.model.Transaction transaction, kotlin.jvm.functions.Function0<kotlin.Unit> onEditClick, kotlin.jvm.functions.Function0<kotlin.Unit> onDeleteClick) {
    }
}