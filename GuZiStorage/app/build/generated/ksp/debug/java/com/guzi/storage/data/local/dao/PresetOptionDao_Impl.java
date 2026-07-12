package com.guzi.storage.data.local.dao;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.guzi.storage.data.local.entity.PresetOptionEntity;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.flow.Flow;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class PresetOptionDao_Impl implements PresetOptionDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<PresetOptionEntity> __insertionAdapterOfPresetOptionEntity;

  private final EntityDeletionOrUpdateAdapter<PresetOptionEntity> __deletionAdapterOfPresetOptionEntity;

  private final SharedSQLiteStatement __preparedStmtOfDeleteByAttributeAndValue;

  public PresetOptionDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfPresetOptionEntity = new EntityInsertionAdapter<PresetOptionEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR IGNORE INTO `preset_options` (`id`,`attribute_name`,`option_value`,`sort_order`) VALUES (nullif(?, 0),?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final PresetOptionEntity entity) {
        statement.bindLong(1, entity.getId());
        statement.bindString(2, entity.getAttributeName());
        statement.bindString(3, entity.getOptionValue());
        statement.bindLong(4, entity.getSortOrder());
      }
    };
    this.__deletionAdapterOfPresetOptionEntity = new EntityDeletionOrUpdateAdapter<PresetOptionEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `preset_options` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final PresetOptionEntity entity) {
        statement.bindLong(1, entity.getId());
      }
    };
    this.__preparedStmtOfDeleteByAttributeAndValue = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM preset_options WHERE attribute_name = ? AND option_value = ?";
        return _query;
      }
    };
  }

  @Override
  public Object insert(final PresetOptionEntity option,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfPresetOptionEntity.insert(option);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object insertAll(final List<PresetOptionEntity> options,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfPresetOptionEntity.insert(options);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object delete(final PresetOptionEntity option,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __deletionAdapterOfPresetOptionEntity.handle(option);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteByAttributeAndValue(final String attributeName, final String optionValue,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteByAttributeAndValue.acquire();
        int _argIndex = 1;
        _stmt.bindString(_argIndex, attributeName);
        _argIndex = 2;
        _stmt.bindString(_argIndex, optionValue);
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfDeleteByAttributeAndValue.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<PresetOptionEntity>> getByAttribute(final String attributeName) {
    final String _sql = "SELECT * FROM preset_options WHERE attribute_name = ? ORDER BY sort_order, option_value";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, attributeName);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"preset_options"}, new Callable<List<PresetOptionEntity>>() {
      @Override
      @NonNull
      public List<PresetOptionEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfAttributeName = CursorUtil.getColumnIndexOrThrow(_cursor, "attribute_name");
          final int _cursorIndexOfOptionValue = CursorUtil.getColumnIndexOrThrow(_cursor, "option_value");
          final int _cursorIndexOfSortOrder = CursorUtil.getColumnIndexOrThrow(_cursor, "sort_order");
          final List<PresetOptionEntity> _result = new ArrayList<PresetOptionEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final PresetOptionEntity _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpAttributeName;
            _tmpAttributeName = _cursor.getString(_cursorIndexOfAttributeName);
            final String _tmpOptionValue;
            _tmpOptionValue = _cursor.getString(_cursorIndexOfOptionValue);
            final int _tmpSortOrder;
            _tmpSortOrder = _cursor.getInt(_cursorIndexOfSortOrder);
            _item = new PresetOptionEntity(_tmpId,_tmpAttributeName,_tmpOptionValue,_tmpSortOrder);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Object getOptionValues(final String attributeName,
      final Continuation<? super List<String>> $completion) {
    final String _sql = "SELECT option_value FROM preset_options WHERE attribute_name = ? ORDER BY sort_order, option_value";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, attributeName);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<String>>() {
      @Override
      @NonNull
      public List<String> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final List<String> _result = new ArrayList<String>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final String _item;
            _item = _cursor.getString(0);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Object getAllAttributeNames(final Continuation<? super List<String>> $completion) {
    final String _sql = "SELECT DISTINCT attribute_name FROM preset_options";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<String>>() {
      @Override
      @NonNull
      public List<String> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final List<String> _result = new ArrayList<String>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final String _item;
            _item = _cursor.getString(0);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
