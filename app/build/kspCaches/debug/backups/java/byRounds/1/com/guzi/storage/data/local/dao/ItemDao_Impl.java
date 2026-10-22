package com.guzi.storage.data.local.dao;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.room.util.StringUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.guzi.storage.data.local.entity.ItemEntity;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Integer;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.StringBuilder;
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
public final class ItemDao_Impl implements ItemDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<ItemEntity> __insertionAdapterOfItemEntity;

  private final EntityDeletionOrUpdateAdapter<ItemEntity> __deletionAdapterOfItemEntity;

  private final EntityDeletionOrUpdateAdapter<ItemEntity> __updateAdapterOfItemEntity;

  public ItemDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfItemEntity = new EntityInsertionAdapter<ItemEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `items` (`id`,`name_cn`,`name_en`,`ip_name`,`producer`,`sell_method`,`price`,`category`,`release_date`,`version`,`status`,`quantity`,`original_bag_quantity`,`note`,`origin_places`,`sale_format`,`sizes`,`materials`,`characters`,`art_types`,`damage_details`,`image_paths`,`reference_links`,`extra_json`,`created_at`,`updated_at`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final ItemEntity entity) {
        statement.bindString(1, entity.getId());
        statement.bindString(2, entity.getNameCn());
        if (entity.getNameEn() == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.getNameEn());
        }
        statement.bindString(4, entity.getIpName());
        if (entity.getProducer() == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, entity.getProducer());
        }
        if (entity.getSellMethod() == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, entity.getSellMethod());
        }
        if (entity.getPrice() == null) {
          statement.bindNull(7);
        } else {
          statement.bindString(7, entity.getPrice());
        }
        if (entity.getCategory() == null) {
          statement.bindNull(8);
        } else {
          statement.bindString(8, entity.getCategory());
        }
        if (entity.getReleaseDate() == null) {
          statement.bindNull(9);
        } else {
          statement.bindString(9, entity.getReleaseDate());
        }
        if (entity.getVersion() == null) {
          statement.bindNull(10);
        } else {
          statement.bindString(10, entity.getVersion());
        }
        statement.bindString(11, entity.getStatus());
        statement.bindLong(12, entity.getQuantity());
        if (entity.getOriginalBagQuantity() == null) {
          statement.bindNull(13);
        } else {
          statement.bindLong(13, entity.getOriginalBagQuantity());
        }
        if (entity.getNote() == null) {
          statement.bindNull(14);
        } else {
          statement.bindString(14, entity.getNote());
        }
        statement.bindString(15, entity.getOriginPlaces());
        if (entity.getSaleFormat() == null) {
          statement.bindNull(16);
        } else {
          statement.bindString(16, entity.getSaleFormat());
        }
        statement.bindString(17, entity.getSizes());
        statement.bindString(18, entity.getMaterials());
        statement.bindString(19, entity.getCharacters());
        statement.bindString(20, entity.getArtTypes());
        statement.bindString(21, entity.getDamageDetails());
        statement.bindString(22, entity.getImagePaths());
        statement.bindString(23, entity.getReferenceLinks());
        statement.bindString(24, entity.getExtraJson());
        statement.bindLong(25, entity.getCreatedAt());
        statement.bindLong(26, entity.getUpdatedAt());
      }
    };
    this.__deletionAdapterOfItemEntity = new EntityDeletionOrUpdateAdapter<ItemEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `items` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final ItemEntity entity) {
        statement.bindString(1, entity.getId());
      }
    };
    this.__updateAdapterOfItemEntity = new EntityDeletionOrUpdateAdapter<ItemEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `items` SET `id` = ?,`name_cn` = ?,`name_en` = ?,`ip_name` = ?,`producer` = ?,`sell_method` = ?,`price` = ?,`category` = ?,`release_date` = ?,`version` = ?,`status` = ?,`quantity` = ?,`original_bag_quantity` = ?,`note` = ?,`origin_places` = ?,`sale_format` = ?,`sizes` = ?,`materials` = ?,`characters` = ?,`art_types` = ?,`damage_details` = ?,`image_paths` = ?,`reference_links` = ?,`extra_json` = ?,`created_at` = ?,`updated_at` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final ItemEntity entity) {
        statement.bindString(1, entity.getId());
        statement.bindString(2, entity.getNameCn());
        if (entity.getNameEn() == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.getNameEn());
        }
        statement.bindString(4, entity.getIpName());
        if (entity.getProducer() == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, entity.getProducer());
        }
        if (entity.getSellMethod() == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, entity.getSellMethod());
        }
        if (entity.getPrice() == null) {
          statement.bindNull(7);
        } else {
          statement.bindString(7, entity.getPrice());
        }
        if (entity.getCategory() == null) {
          statement.bindNull(8);
        } else {
          statement.bindString(8, entity.getCategory());
        }
        if (entity.getReleaseDate() == null) {
          statement.bindNull(9);
        } else {
          statement.bindString(9, entity.getReleaseDate());
        }
        if (entity.getVersion() == null) {
          statement.bindNull(10);
        } else {
          statement.bindString(10, entity.getVersion());
        }
        statement.bindString(11, entity.getStatus());
        statement.bindLong(12, entity.getQuantity());
        if (entity.getOriginalBagQuantity() == null) {
          statement.bindNull(13);
        } else {
          statement.bindLong(13, entity.getOriginalBagQuantity());
        }
        if (entity.getNote() == null) {
          statement.bindNull(14);
        } else {
          statement.bindString(14, entity.getNote());
        }
        statement.bindString(15, entity.getOriginPlaces());
        if (entity.getSaleFormat() == null) {
          statement.bindNull(16);
        } else {
          statement.bindString(16, entity.getSaleFormat());
        }
        statement.bindString(17, entity.getSizes());
        statement.bindString(18, entity.getMaterials());
        statement.bindString(19, entity.getCharacters());
        statement.bindString(20, entity.getArtTypes());
        statement.bindString(21, entity.getDamageDetails());
        statement.bindString(22, entity.getImagePaths());
        statement.bindString(23, entity.getReferenceLinks());
        statement.bindString(24, entity.getExtraJson());
        statement.bindLong(25, entity.getCreatedAt());
        statement.bindLong(26, entity.getUpdatedAt());
        statement.bindString(27, entity.getId());
      }
    };
  }

  @Override
  public Object insert(final ItemEntity item, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfItemEntity.insert(item);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object insertAll(final List<ItemEntity> items,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfItemEntity.insert(items);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object delete(final ItemEntity item, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __deletionAdapterOfItemEntity.handle(item);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object update(final ItemEntity item, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfItemEntity.handle(item);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<ItemEntity>> getAll() {
    final String _sql = "SELECT * FROM items ORDER BY created_at ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"items"}, new Callable<List<ItemEntity>>() {
      @Override
      @NonNull
      public List<ItemEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfNameCn = CursorUtil.getColumnIndexOrThrow(_cursor, "name_cn");
          final int _cursorIndexOfNameEn = CursorUtil.getColumnIndexOrThrow(_cursor, "name_en");
          final int _cursorIndexOfIpName = CursorUtil.getColumnIndexOrThrow(_cursor, "ip_name");
          final int _cursorIndexOfProducer = CursorUtil.getColumnIndexOrThrow(_cursor, "producer");
          final int _cursorIndexOfSellMethod = CursorUtil.getColumnIndexOrThrow(_cursor, "sell_method");
          final int _cursorIndexOfPrice = CursorUtil.getColumnIndexOrThrow(_cursor, "price");
          final int _cursorIndexOfCategory = CursorUtil.getColumnIndexOrThrow(_cursor, "category");
          final int _cursorIndexOfReleaseDate = CursorUtil.getColumnIndexOrThrow(_cursor, "release_date");
          final int _cursorIndexOfVersion = CursorUtil.getColumnIndexOrThrow(_cursor, "version");
          final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
          final int _cursorIndexOfQuantity = CursorUtil.getColumnIndexOrThrow(_cursor, "quantity");
          final int _cursorIndexOfOriginalBagQuantity = CursorUtil.getColumnIndexOrThrow(_cursor, "original_bag_quantity");
          final int _cursorIndexOfNote = CursorUtil.getColumnIndexOrThrow(_cursor, "note");
          final int _cursorIndexOfOriginPlaces = CursorUtil.getColumnIndexOrThrow(_cursor, "origin_places");
          final int _cursorIndexOfSaleFormat = CursorUtil.getColumnIndexOrThrow(_cursor, "sale_format");
          final int _cursorIndexOfSizes = CursorUtil.getColumnIndexOrThrow(_cursor, "sizes");
          final int _cursorIndexOfMaterials = CursorUtil.getColumnIndexOrThrow(_cursor, "materials");
          final int _cursorIndexOfCharacters = CursorUtil.getColumnIndexOrThrow(_cursor, "characters");
          final int _cursorIndexOfArtTypes = CursorUtil.getColumnIndexOrThrow(_cursor, "art_types");
          final int _cursorIndexOfDamageDetails = CursorUtil.getColumnIndexOrThrow(_cursor, "damage_details");
          final int _cursorIndexOfImagePaths = CursorUtil.getColumnIndexOrThrow(_cursor, "image_paths");
          final int _cursorIndexOfReferenceLinks = CursorUtil.getColumnIndexOrThrow(_cursor, "reference_links");
          final int _cursorIndexOfExtraJson = CursorUtil.getColumnIndexOrThrow(_cursor, "extra_json");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "created_at");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updated_at");
          final List<ItemEntity> _result = new ArrayList<ItemEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final ItemEntity _item;
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            final String _tmpNameCn;
            _tmpNameCn = _cursor.getString(_cursorIndexOfNameCn);
            final String _tmpNameEn;
            if (_cursor.isNull(_cursorIndexOfNameEn)) {
              _tmpNameEn = null;
            } else {
              _tmpNameEn = _cursor.getString(_cursorIndexOfNameEn);
            }
            final String _tmpIpName;
            _tmpIpName = _cursor.getString(_cursorIndexOfIpName);
            final String _tmpProducer;
            if (_cursor.isNull(_cursorIndexOfProducer)) {
              _tmpProducer = null;
            } else {
              _tmpProducer = _cursor.getString(_cursorIndexOfProducer);
            }
            final String _tmpSellMethod;
            if (_cursor.isNull(_cursorIndexOfSellMethod)) {
              _tmpSellMethod = null;
            } else {
              _tmpSellMethod = _cursor.getString(_cursorIndexOfSellMethod);
            }
            final String _tmpPrice;
            if (_cursor.isNull(_cursorIndexOfPrice)) {
              _tmpPrice = null;
            } else {
              _tmpPrice = _cursor.getString(_cursorIndexOfPrice);
            }
            final String _tmpCategory;
            if (_cursor.isNull(_cursorIndexOfCategory)) {
              _tmpCategory = null;
            } else {
              _tmpCategory = _cursor.getString(_cursorIndexOfCategory);
            }
            final String _tmpReleaseDate;
            if (_cursor.isNull(_cursorIndexOfReleaseDate)) {
              _tmpReleaseDate = null;
            } else {
              _tmpReleaseDate = _cursor.getString(_cursorIndexOfReleaseDate);
            }
            final String _tmpVersion;
            if (_cursor.isNull(_cursorIndexOfVersion)) {
              _tmpVersion = null;
            } else {
              _tmpVersion = _cursor.getString(_cursorIndexOfVersion);
            }
            final String _tmpStatus;
            _tmpStatus = _cursor.getString(_cursorIndexOfStatus);
            final int _tmpQuantity;
            _tmpQuantity = _cursor.getInt(_cursorIndexOfQuantity);
            final Integer _tmpOriginalBagQuantity;
            if (_cursor.isNull(_cursorIndexOfOriginalBagQuantity)) {
              _tmpOriginalBagQuantity = null;
            } else {
              _tmpOriginalBagQuantity = _cursor.getInt(_cursorIndexOfOriginalBagQuantity);
            }
            final String _tmpNote;
            if (_cursor.isNull(_cursorIndexOfNote)) {
              _tmpNote = null;
            } else {
              _tmpNote = _cursor.getString(_cursorIndexOfNote);
            }
            final String _tmpOriginPlaces;
            _tmpOriginPlaces = _cursor.getString(_cursorIndexOfOriginPlaces);
            final String _tmpSaleFormat;
            if (_cursor.isNull(_cursorIndexOfSaleFormat)) {
              _tmpSaleFormat = null;
            } else {
              _tmpSaleFormat = _cursor.getString(_cursorIndexOfSaleFormat);
            }
            final String _tmpSizes;
            _tmpSizes = _cursor.getString(_cursorIndexOfSizes);
            final String _tmpMaterials;
            _tmpMaterials = _cursor.getString(_cursorIndexOfMaterials);
            final String _tmpCharacters;
            _tmpCharacters = _cursor.getString(_cursorIndexOfCharacters);
            final String _tmpArtTypes;
            _tmpArtTypes = _cursor.getString(_cursorIndexOfArtTypes);
            final String _tmpDamageDetails;
            _tmpDamageDetails = _cursor.getString(_cursorIndexOfDamageDetails);
            final String _tmpImagePaths;
            _tmpImagePaths = _cursor.getString(_cursorIndexOfImagePaths);
            final String _tmpReferenceLinks;
            _tmpReferenceLinks = _cursor.getString(_cursorIndexOfReferenceLinks);
            final String _tmpExtraJson;
            _tmpExtraJson = _cursor.getString(_cursorIndexOfExtraJson);
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            final long _tmpUpdatedAt;
            _tmpUpdatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
            _item = new ItemEntity(_tmpId,_tmpNameCn,_tmpNameEn,_tmpIpName,_tmpProducer,_tmpSellMethod,_tmpPrice,_tmpCategory,_tmpReleaseDate,_tmpVersion,_tmpStatus,_tmpQuantity,_tmpOriginalBagQuantity,_tmpNote,_tmpOriginPlaces,_tmpSaleFormat,_tmpSizes,_tmpMaterials,_tmpCharacters,_tmpArtTypes,_tmpDamageDetails,_tmpImagePaths,_tmpReferenceLinks,_tmpExtraJson,_tmpCreatedAt,_tmpUpdatedAt);
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
  public Object getById(final String id, final Continuation<? super ItemEntity> $completion) {
    final String _sql = "SELECT * FROM items WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, id);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<ItemEntity>() {
      @Override
      @Nullable
      public ItemEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfNameCn = CursorUtil.getColumnIndexOrThrow(_cursor, "name_cn");
          final int _cursorIndexOfNameEn = CursorUtil.getColumnIndexOrThrow(_cursor, "name_en");
          final int _cursorIndexOfIpName = CursorUtil.getColumnIndexOrThrow(_cursor, "ip_name");
          final int _cursorIndexOfProducer = CursorUtil.getColumnIndexOrThrow(_cursor, "producer");
          final int _cursorIndexOfSellMethod = CursorUtil.getColumnIndexOrThrow(_cursor, "sell_method");
          final int _cursorIndexOfPrice = CursorUtil.getColumnIndexOrThrow(_cursor, "price");
          final int _cursorIndexOfCategory = CursorUtil.getColumnIndexOrThrow(_cursor, "category");
          final int _cursorIndexOfReleaseDate = CursorUtil.getColumnIndexOrThrow(_cursor, "release_date");
          final int _cursorIndexOfVersion = CursorUtil.getColumnIndexOrThrow(_cursor, "version");
          final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
          final int _cursorIndexOfQuantity = CursorUtil.getColumnIndexOrThrow(_cursor, "quantity");
          final int _cursorIndexOfOriginalBagQuantity = CursorUtil.getColumnIndexOrThrow(_cursor, "original_bag_quantity");
          final int _cursorIndexOfNote = CursorUtil.getColumnIndexOrThrow(_cursor, "note");
          final int _cursorIndexOfOriginPlaces = CursorUtil.getColumnIndexOrThrow(_cursor, "origin_places");
          final int _cursorIndexOfSaleFormat = CursorUtil.getColumnIndexOrThrow(_cursor, "sale_format");
          final int _cursorIndexOfSizes = CursorUtil.getColumnIndexOrThrow(_cursor, "sizes");
          final int _cursorIndexOfMaterials = CursorUtil.getColumnIndexOrThrow(_cursor, "materials");
          final int _cursorIndexOfCharacters = CursorUtil.getColumnIndexOrThrow(_cursor, "characters");
          final int _cursorIndexOfArtTypes = CursorUtil.getColumnIndexOrThrow(_cursor, "art_types");
          final int _cursorIndexOfDamageDetails = CursorUtil.getColumnIndexOrThrow(_cursor, "damage_details");
          final int _cursorIndexOfImagePaths = CursorUtil.getColumnIndexOrThrow(_cursor, "image_paths");
          final int _cursorIndexOfReferenceLinks = CursorUtil.getColumnIndexOrThrow(_cursor, "reference_links");
          final int _cursorIndexOfExtraJson = CursorUtil.getColumnIndexOrThrow(_cursor, "extra_json");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "created_at");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updated_at");
          final ItemEntity _result;
          if (_cursor.moveToFirst()) {
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            final String _tmpNameCn;
            _tmpNameCn = _cursor.getString(_cursorIndexOfNameCn);
            final String _tmpNameEn;
            if (_cursor.isNull(_cursorIndexOfNameEn)) {
              _tmpNameEn = null;
            } else {
              _tmpNameEn = _cursor.getString(_cursorIndexOfNameEn);
            }
            final String _tmpIpName;
            _tmpIpName = _cursor.getString(_cursorIndexOfIpName);
            final String _tmpProducer;
            if (_cursor.isNull(_cursorIndexOfProducer)) {
              _tmpProducer = null;
            } else {
              _tmpProducer = _cursor.getString(_cursorIndexOfProducer);
            }
            final String _tmpSellMethod;
            if (_cursor.isNull(_cursorIndexOfSellMethod)) {
              _tmpSellMethod = null;
            } else {
              _tmpSellMethod = _cursor.getString(_cursorIndexOfSellMethod);
            }
            final String _tmpPrice;
            if (_cursor.isNull(_cursorIndexOfPrice)) {
              _tmpPrice = null;
            } else {
              _tmpPrice = _cursor.getString(_cursorIndexOfPrice);
            }
            final String _tmpCategory;
            if (_cursor.isNull(_cursorIndexOfCategory)) {
              _tmpCategory = null;
            } else {
              _tmpCategory = _cursor.getString(_cursorIndexOfCategory);
            }
            final String _tmpReleaseDate;
            if (_cursor.isNull(_cursorIndexOfReleaseDate)) {
              _tmpReleaseDate = null;
            } else {
              _tmpReleaseDate = _cursor.getString(_cursorIndexOfReleaseDate);
            }
            final String _tmpVersion;
            if (_cursor.isNull(_cursorIndexOfVersion)) {
              _tmpVersion = null;
            } else {
              _tmpVersion = _cursor.getString(_cursorIndexOfVersion);
            }
            final String _tmpStatus;
            _tmpStatus = _cursor.getString(_cursorIndexOfStatus);
            final int _tmpQuantity;
            _tmpQuantity = _cursor.getInt(_cursorIndexOfQuantity);
            final Integer _tmpOriginalBagQuantity;
            if (_cursor.isNull(_cursorIndexOfOriginalBagQuantity)) {
              _tmpOriginalBagQuantity = null;
            } else {
              _tmpOriginalBagQuantity = _cursor.getInt(_cursorIndexOfOriginalBagQuantity);
            }
            final String _tmpNote;
            if (_cursor.isNull(_cursorIndexOfNote)) {
              _tmpNote = null;
            } else {
              _tmpNote = _cursor.getString(_cursorIndexOfNote);
            }
            final String _tmpOriginPlaces;
            _tmpOriginPlaces = _cursor.getString(_cursorIndexOfOriginPlaces);
            final String _tmpSaleFormat;
            if (_cursor.isNull(_cursorIndexOfSaleFormat)) {
              _tmpSaleFormat = null;
            } else {
              _tmpSaleFormat = _cursor.getString(_cursorIndexOfSaleFormat);
            }
            final String _tmpSizes;
            _tmpSizes = _cursor.getString(_cursorIndexOfSizes);
            final String _tmpMaterials;
            _tmpMaterials = _cursor.getString(_cursorIndexOfMaterials);
            final String _tmpCharacters;
            _tmpCharacters = _cursor.getString(_cursorIndexOfCharacters);
            final String _tmpArtTypes;
            _tmpArtTypes = _cursor.getString(_cursorIndexOfArtTypes);
            final String _tmpDamageDetails;
            _tmpDamageDetails = _cursor.getString(_cursorIndexOfDamageDetails);
            final String _tmpImagePaths;
            _tmpImagePaths = _cursor.getString(_cursorIndexOfImagePaths);
            final String _tmpReferenceLinks;
            _tmpReferenceLinks = _cursor.getString(_cursorIndexOfReferenceLinks);
            final String _tmpExtraJson;
            _tmpExtraJson = _cursor.getString(_cursorIndexOfExtraJson);
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            final long _tmpUpdatedAt;
            _tmpUpdatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
            _result = new ItemEntity(_tmpId,_tmpNameCn,_tmpNameEn,_tmpIpName,_tmpProducer,_tmpSellMethod,_tmpPrice,_tmpCategory,_tmpReleaseDate,_tmpVersion,_tmpStatus,_tmpQuantity,_tmpOriginalBagQuantity,_tmpNote,_tmpOriginPlaces,_tmpSaleFormat,_tmpSizes,_tmpMaterials,_tmpCharacters,_tmpArtTypes,_tmpDamageDetails,_tmpImagePaths,_tmpReferenceLinks,_tmpExtraJson,_tmpCreatedAt,_tmpUpdatedAt);
          } else {
            _result = null;
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
  public Flow<ItemEntity> getByIdFlow(final String id) {
    final String _sql = "SELECT * FROM items WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, id);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"items"}, new Callable<ItemEntity>() {
      @Override
      @Nullable
      public ItemEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfNameCn = CursorUtil.getColumnIndexOrThrow(_cursor, "name_cn");
          final int _cursorIndexOfNameEn = CursorUtil.getColumnIndexOrThrow(_cursor, "name_en");
          final int _cursorIndexOfIpName = CursorUtil.getColumnIndexOrThrow(_cursor, "ip_name");
          final int _cursorIndexOfProducer = CursorUtil.getColumnIndexOrThrow(_cursor, "producer");
          final int _cursorIndexOfSellMethod = CursorUtil.getColumnIndexOrThrow(_cursor, "sell_method");
          final int _cursorIndexOfPrice = CursorUtil.getColumnIndexOrThrow(_cursor, "price");
          final int _cursorIndexOfCategory = CursorUtil.getColumnIndexOrThrow(_cursor, "category");
          final int _cursorIndexOfReleaseDate = CursorUtil.getColumnIndexOrThrow(_cursor, "release_date");
          final int _cursorIndexOfVersion = CursorUtil.getColumnIndexOrThrow(_cursor, "version");
          final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
          final int _cursorIndexOfQuantity = CursorUtil.getColumnIndexOrThrow(_cursor, "quantity");
          final int _cursorIndexOfOriginalBagQuantity = CursorUtil.getColumnIndexOrThrow(_cursor, "original_bag_quantity");
          final int _cursorIndexOfNote = CursorUtil.getColumnIndexOrThrow(_cursor, "note");
          final int _cursorIndexOfOriginPlaces = CursorUtil.getColumnIndexOrThrow(_cursor, "origin_places");
          final int _cursorIndexOfSaleFormat = CursorUtil.getColumnIndexOrThrow(_cursor, "sale_format");
          final int _cursorIndexOfSizes = CursorUtil.getColumnIndexOrThrow(_cursor, "sizes");
          final int _cursorIndexOfMaterials = CursorUtil.getColumnIndexOrThrow(_cursor, "materials");
          final int _cursorIndexOfCharacters = CursorUtil.getColumnIndexOrThrow(_cursor, "characters");
          final int _cursorIndexOfArtTypes = CursorUtil.getColumnIndexOrThrow(_cursor, "art_types");
          final int _cursorIndexOfDamageDetails = CursorUtil.getColumnIndexOrThrow(_cursor, "damage_details");
          final int _cursorIndexOfImagePaths = CursorUtil.getColumnIndexOrThrow(_cursor, "image_paths");
          final int _cursorIndexOfReferenceLinks = CursorUtil.getColumnIndexOrThrow(_cursor, "reference_links");
          final int _cursorIndexOfExtraJson = CursorUtil.getColumnIndexOrThrow(_cursor, "extra_json");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "created_at");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updated_at");
          final ItemEntity _result;
          if (_cursor.moveToFirst()) {
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            final String _tmpNameCn;
            _tmpNameCn = _cursor.getString(_cursorIndexOfNameCn);
            final String _tmpNameEn;
            if (_cursor.isNull(_cursorIndexOfNameEn)) {
              _tmpNameEn = null;
            } else {
              _tmpNameEn = _cursor.getString(_cursorIndexOfNameEn);
            }
            final String _tmpIpName;
            _tmpIpName = _cursor.getString(_cursorIndexOfIpName);
            final String _tmpProducer;
            if (_cursor.isNull(_cursorIndexOfProducer)) {
              _tmpProducer = null;
            } else {
              _tmpProducer = _cursor.getString(_cursorIndexOfProducer);
            }
            final String _tmpSellMethod;
            if (_cursor.isNull(_cursorIndexOfSellMethod)) {
              _tmpSellMethod = null;
            } else {
              _tmpSellMethod = _cursor.getString(_cursorIndexOfSellMethod);
            }
            final String _tmpPrice;
            if (_cursor.isNull(_cursorIndexOfPrice)) {
              _tmpPrice = null;
            } else {
              _tmpPrice = _cursor.getString(_cursorIndexOfPrice);
            }
            final String _tmpCategory;
            if (_cursor.isNull(_cursorIndexOfCategory)) {
              _tmpCategory = null;
            } else {
              _tmpCategory = _cursor.getString(_cursorIndexOfCategory);
            }
            final String _tmpReleaseDate;
            if (_cursor.isNull(_cursorIndexOfReleaseDate)) {
              _tmpReleaseDate = null;
            } else {
              _tmpReleaseDate = _cursor.getString(_cursorIndexOfReleaseDate);
            }
            final String _tmpVersion;
            if (_cursor.isNull(_cursorIndexOfVersion)) {
              _tmpVersion = null;
            } else {
              _tmpVersion = _cursor.getString(_cursorIndexOfVersion);
            }
            final String _tmpStatus;
            _tmpStatus = _cursor.getString(_cursorIndexOfStatus);
            final int _tmpQuantity;
            _tmpQuantity = _cursor.getInt(_cursorIndexOfQuantity);
            final Integer _tmpOriginalBagQuantity;
            if (_cursor.isNull(_cursorIndexOfOriginalBagQuantity)) {
              _tmpOriginalBagQuantity = null;
            } else {
              _tmpOriginalBagQuantity = _cursor.getInt(_cursorIndexOfOriginalBagQuantity);
            }
            final String _tmpNote;
            if (_cursor.isNull(_cursorIndexOfNote)) {
              _tmpNote = null;
            } else {
              _tmpNote = _cursor.getString(_cursorIndexOfNote);
            }
            final String _tmpOriginPlaces;
            _tmpOriginPlaces = _cursor.getString(_cursorIndexOfOriginPlaces);
            final String _tmpSaleFormat;
            if (_cursor.isNull(_cursorIndexOfSaleFormat)) {
              _tmpSaleFormat = null;
            } else {
              _tmpSaleFormat = _cursor.getString(_cursorIndexOfSaleFormat);
            }
            final String _tmpSizes;
            _tmpSizes = _cursor.getString(_cursorIndexOfSizes);
            final String _tmpMaterials;
            _tmpMaterials = _cursor.getString(_cursorIndexOfMaterials);
            final String _tmpCharacters;
            _tmpCharacters = _cursor.getString(_cursorIndexOfCharacters);
            final String _tmpArtTypes;
            _tmpArtTypes = _cursor.getString(_cursorIndexOfArtTypes);
            final String _tmpDamageDetails;
            _tmpDamageDetails = _cursor.getString(_cursorIndexOfDamageDetails);
            final String _tmpImagePaths;
            _tmpImagePaths = _cursor.getString(_cursorIndexOfImagePaths);
            final String _tmpReferenceLinks;
            _tmpReferenceLinks = _cursor.getString(_cursorIndexOfReferenceLinks);
            final String _tmpExtraJson;
            _tmpExtraJson = _cursor.getString(_cursorIndexOfExtraJson);
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            final long _tmpUpdatedAt;
            _tmpUpdatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
            _result = new ItemEntity(_tmpId,_tmpNameCn,_tmpNameEn,_tmpIpName,_tmpProducer,_tmpSellMethod,_tmpPrice,_tmpCategory,_tmpReleaseDate,_tmpVersion,_tmpStatus,_tmpQuantity,_tmpOriginalBagQuantity,_tmpNote,_tmpOriginPlaces,_tmpSaleFormat,_tmpSizes,_tmpMaterials,_tmpCharacters,_tmpArtTypes,_tmpDamageDetails,_tmpImagePaths,_tmpReferenceLinks,_tmpExtraJson,_tmpCreatedAt,_tmpUpdatedAt);
          } else {
            _result = null;
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
  public Flow<List<ItemDao.IpGroupInfo>> getIpGroups() {
    final String _sql = "SELECT ip_name AS ipName, COUNT(*) AS count FROM items GROUP BY ip_name ORDER BY ip_name";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"items"}, new Callable<List<ItemDao.IpGroupInfo>>() {
      @Override
      @NonNull
      public List<ItemDao.IpGroupInfo> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfIpName = 0;
          final int _cursorIndexOfCount = 1;
          final List<ItemDao.IpGroupInfo> _result = new ArrayList<ItemDao.IpGroupInfo>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final ItemDao.IpGroupInfo _item;
            final String _tmpIpName;
            _tmpIpName = _cursor.getString(_cursorIndexOfIpName);
            final int _tmpCount;
            _tmpCount = _cursor.getInt(_cursorIndexOfCount);
            _item = new ItemDao.IpGroupInfo(_tmpIpName,_tmpCount);
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
  public Flow<List<ItemEntity>> getItemsByIp(final String ipName) {
    final String _sql = "\n"
            + "        SELECT * FROM items\n"
            + "        WHERE ip_name = ?\n"
            + "           OR ip_name LIKE ? || '|%'\n"
            + "           OR ip_name LIKE '%|' || ?\n"
            + "           OR ip_name LIKE '%|' || ? || '|%'\n"
            + "        ORDER BY created_at ASC\n"
            + "    ";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 4);
    int _argIndex = 1;
    _statement.bindString(_argIndex, ipName);
    _argIndex = 2;
    _statement.bindString(_argIndex, ipName);
    _argIndex = 3;
    _statement.bindString(_argIndex, ipName);
    _argIndex = 4;
    _statement.bindString(_argIndex, ipName);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"items"}, new Callable<List<ItemEntity>>() {
      @Override
      @NonNull
      public List<ItemEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfNameCn = CursorUtil.getColumnIndexOrThrow(_cursor, "name_cn");
          final int _cursorIndexOfNameEn = CursorUtil.getColumnIndexOrThrow(_cursor, "name_en");
          final int _cursorIndexOfIpName = CursorUtil.getColumnIndexOrThrow(_cursor, "ip_name");
          final int _cursorIndexOfProducer = CursorUtil.getColumnIndexOrThrow(_cursor, "producer");
          final int _cursorIndexOfSellMethod = CursorUtil.getColumnIndexOrThrow(_cursor, "sell_method");
          final int _cursorIndexOfPrice = CursorUtil.getColumnIndexOrThrow(_cursor, "price");
          final int _cursorIndexOfCategory = CursorUtil.getColumnIndexOrThrow(_cursor, "category");
          final int _cursorIndexOfReleaseDate = CursorUtil.getColumnIndexOrThrow(_cursor, "release_date");
          final int _cursorIndexOfVersion = CursorUtil.getColumnIndexOrThrow(_cursor, "version");
          final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
          final int _cursorIndexOfQuantity = CursorUtil.getColumnIndexOrThrow(_cursor, "quantity");
          final int _cursorIndexOfOriginalBagQuantity = CursorUtil.getColumnIndexOrThrow(_cursor, "original_bag_quantity");
          final int _cursorIndexOfNote = CursorUtil.getColumnIndexOrThrow(_cursor, "note");
          final int _cursorIndexOfOriginPlaces = CursorUtil.getColumnIndexOrThrow(_cursor, "origin_places");
          final int _cursorIndexOfSaleFormat = CursorUtil.getColumnIndexOrThrow(_cursor, "sale_format");
          final int _cursorIndexOfSizes = CursorUtil.getColumnIndexOrThrow(_cursor, "sizes");
          final int _cursorIndexOfMaterials = CursorUtil.getColumnIndexOrThrow(_cursor, "materials");
          final int _cursorIndexOfCharacters = CursorUtil.getColumnIndexOrThrow(_cursor, "characters");
          final int _cursorIndexOfArtTypes = CursorUtil.getColumnIndexOrThrow(_cursor, "art_types");
          final int _cursorIndexOfDamageDetails = CursorUtil.getColumnIndexOrThrow(_cursor, "damage_details");
          final int _cursorIndexOfImagePaths = CursorUtil.getColumnIndexOrThrow(_cursor, "image_paths");
          final int _cursorIndexOfReferenceLinks = CursorUtil.getColumnIndexOrThrow(_cursor, "reference_links");
          final int _cursorIndexOfExtraJson = CursorUtil.getColumnIndexOrThrow(_cursor, "extra_json");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "created_at");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updated_at");
          final List<ItemEntity> _result = new ArrayList<ItemEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final ItemEntity _item;
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            final String _tmpNameCn;
            _tmpNameCn = _cursor.getString(_cursorIndexOfNameCn);
            final String _tmpNameEn;
            if (_cursor.isNull(_cursorIndexOfNameEn)) {
              _tmpNameEn = null;
            } else {
              _tmpNameEn = _cursor.getString(_cursorIndexOfNameEn);
            }
            final String _tmpIpName;
            _tmpIpName = _cursor.getString(_cursorIndexOfIpName);
            final String _tmpProducer;
            if (_cursor.isNull(_cursorIndexOfProducer)) {
              _tmpProducer = null;
            } else {
              _tmpProducer = _cursor.getString(_cursorIndexOfProducer);
            }
            final String _tmpSellMethod;
            if (_cursor.isNull(_cursorIndexOfSellMethod)) {
              _tmpSellMethod = null;
            } else {
              _tmpSellMethod = _cursor.getString(_cursorIndexOfSellMethod);
            }
            final String _tmpPrice;
            if (_cursor.isNull(_cursorIndexOfPrice)) {
              _tmpPrice = null;
            } else {
              _tmpPrice = _cursor.getString(_cursorIndexOfPrice);
            }
            final String _tmpCategory;
            if (_cursor.isNull(_cursorIndexOfCategory)) {
              _tmpCategory = null;
            } else {
              _tmpCategory = _cursor.getString(_cursorIndexOfCategory);
            }
            final String _tmpReleaseDate;
            if (_cursor.isNull(_cursorIndexOfReleaseDate)) {
              _tmpReleaseDate = null;
            } else {
              _tmpReleaseDate = _cursor.getString(_cursorIndexOfReleaseDate);
            }
            final String _tmpVersion;
            if (_cursor.isNull(_cursorIndexOfVersion)) {
              _tmpVersion = null;
            } else {
              _tmpVersion = _cursor.getString(_cursorIndexOfVersion);
            }
            final String _tmpStatus;
            _tmpStatus = _cursor.getString(_cursorIndexOfStatus);
            final int _tmpQuantity;
            _tmpQuantity = _cursor.getInt(_cursorIndexOfQuantity);
            final Integer _tmpOriginalBagQuantity;
            if (_cursor.isNull(_cursorIndexOfOriginalBagQuantity)) {
              _tmpOriginalBagQuantity = null;
            } else {
              _tmpOriginalBagQuantity = _cursor.getInt(_cursorIndexOfOriginalBagQuantity);
            }
            final String _tmpNote;
            if (_cursor.isNull(_cursorIndexOfNote)) {
              _tmpNote = null;
            } else {
              _tmpNote = _cursor.getString(_cursorIndexOfNote);
            }
            final String _tmpOriginPlaces;
            _tmpOriginPlaces = _cursor.getString(_cursorIndexOfOriginPlaces);
            final String _tmpSaleFormat;
            if (_cursor.isNull(_cursorIndexOfSaleFormat)) {
              _tmpSaleFormat = null;
            } else {
              _tmpSaleFormat = _cursor.getString(_cursorIndexOfSaleFormat);
            }
            final String _tmpSizes;
            _tmpSizes = _cursor.getString(_cursorIndexOfSizes);
            final String _tmpMaterials;
            _tmpMaterials = _cursor.getString(_cursorIndexOfMaterials);
            final String _tmpCharacters;
            _tmpCharacters = _cursor.getString(_cursorIndexOfCharacters);
            final String _tmpArtTypes;
            _tmpArtTypes = _cursor.getString(_cursorIndexOfArtTypes);
            final String _tmpDamageDetails;
            _tmpDamageDetails = _cursor.getString(_cursorIndexOfDamageDetails);
            final String _tmpImagePaths;
            _tmpImagePaths = _cursor.getString(_cursorIndexOfImagePaths);
            final String _tmpReferenceLinks;
            _tmpReferenceLinks = _cursor.getString(_cursorIndexOfReferenceLinks);
            final String _tmpExtraJson;
            _tmpExtraJson = _cursor.getString(_cursorIndexOfExtraJson);
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            final long _tmpUpdatedAt;
            _tmpUpdatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
            _item = new ItemEntity(_tmpId,_tmpNameCn,_tmpNameEn,_tmpIpName,_tmpProducer,_tmpSellMethod,_tmpPrice,_tmpCategory,_tmpReleaseDate,_tmpVersion,_tmpStatus,_tmpQuantity,_tmpOriginalBagQuantity,_tmpNote,_tmpOriginPlaces,_tmpSaleFormat,_tmpSizes,_tmpMaterials,_tmpCharacters,_tmpArtTypes,_tmpDamageDetails,_tmpImagePaths,_tmpReferenceLinks,_tmpExtraJson,_tmpCreatedAt,_tmpUpdatedAt);
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
  public Flow<List<ItemEntity>> getItemsByCategory(final String category) {
    final String _sql = "\n"
            + "        SELECT * FROM items\n"
            + "        WHERE category = ?\n"
            + "           OR category LIKE ? || '|%'\n"
            + "           OR category LIKE '%|' || ?\n"
            + "           OR category LIKE '%|' || ? || '|%'\n"
            + "        ORDER BY created_at ASC\n"
            + "    ";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 4);
    int _argIndex = 1;
    _statement.bindString(_argIndex, category);
    _argIndex = 2;
    _statement.bindString(_argIndex, category);
    _argIndex = 3;
    _statement.bindString(_argIndex, category);
    _argIndex = 4;
    _statement.bindString(_argIndex, category);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"items"}, new Callable<List<ItemEntity>>() {
      @Override
      @NonNull
      public List<ItemEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfNameCn = CursorUtil.getColumnIndexOrThrow(_cursor, "name_cn");
          final int _cursorIndexOfNameEn = CursorUtil.getColumnIndexOrThrow(_cursor, "name_en");
          final int _cursorIndexOfIpName = CursorUtil.getColumnIndexOrThrow(_cursor, "ip_name");
          final int _cursorIndexOfProducer = CursorUtil.getColumnIndexOrThrow(_cursor, "producer");
          final int _cursorIndexOfSellMethod = CursorUtil.getColumnIndexOrThrow(_cursor, "sell_method");
          final int _cursorIndexOfPrice = CursorUtil.getColumnIndexOrThrow(_cursor, "price");
          final int _cursorIndexOfCategory = CursorUtil.getColumnIndexOrThrow(_cursor, "category");
          final int _cursorIndexOfReleaseDate = CursorUtil.getColumnIndexOrThrow(_cursor, "release_date");
          final int _cursorIndexOfVersion = CursorUtil.getColumnIndexOrThrow(_cursor, "version");
          final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
          final int _cursorIndexOfQuantity = CursorUtil.getColumnIndexOrThrow(_cursor, "quantity");
          final int _cursorIndexOfOriginalBagQuantity = CursorUtil.getColumnIndexOrThrow(_cursor, "original_bag_quantity");
          final int _cursorIndexOfNote = CursorUtil.getColumnIndexOrThrow(_cursor, "note");
          final int _cursorIndexOfOriginPlaces = CursorUtil.getColumnIndexOrThrow(_cursor, "origin_places");
          final int _cursorIndexOfSaleFormat = CursorUtil.getColumnIndexOrThrow(_cursor, "sale_format");
          final int _cursorIndexOfSizes = CursorUtil.getColumnIndexOrThrow(_cursor, "sizes");
          final int _cursorIndexOfMaterials = CursorUtil.getColumnIndexOrThrow(_cursor, "materials");
          final int _cursorIndexOfCharacters = CursorUtil.getColumnIndexOrThrow(_cursor, "characters");
          final int _cursorIndexOfArtTypes = CursorUtil.getColumnIndexOrThrow(_cursor, "art_types");
          final int _cursorIndexOfDamageDetails = CursorUtil.getColumnIndexOrThrow(_cursor, "damage_details");
          final int _cursorIndexOfImagePaths = CursorUtil.getColumnIndexOrThrow(_cursor, "image_paths");
          final int _cursorIndexOfReferenceLinks = CursorUtil.getColumnIndexOrThrow(_cursor, "reference_links");
          final int _cursorIndexOfExtraJson = CursorUtil.getColumnIndexOrThrow(_cursor, "extra_json");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "created_at");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updated_at");
          final List<ItemEntity> _result = new ArrayList<ItemEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final ItemEntity _item;
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            final String _tmpNameCn;
            _tmpNameCn = _cursor.getString(_cursorIndexOfNameCn);
            final String _tmpNameEn;
            if (_cursor.isNull(_cursorIndexOfNameEn)) {
              _tmpNameEn = null;
            } else {
              _tmpNameEn = _cursor.getString(_cursorIndexOfNameEn);
            }
            final String _tmpIpName;
            _tmpIpName = _cursor.getString(_cursorIndexOfIpName);
            final String _tmpProducer;
            if (_cursor.isNull(_cursorIndexOfProducer)) {
              _tmpProducer = null;
            } else {
              _tmpProducer = _cursor.getString(_cursorIndexOfProducer);
            }
            final String _tmpSellMethod;
            if (_cursor.isNull(_cursorIndexOfSellMethod)) {
              _tmpSellMethod = null;
            } else {
              _tmpSellMethod = _cursor.getString(_cursorIndexOfSellMethod);
            }
            final String _tmpPrice;
            if (_cursor.isNull(_cursorIndexOfPrice)) {
              _tmpPrice = null;
            } else {
              _tmpPrice = _cursor.getString(_cursorIndexOfPrice);
            }
            final String _tmpCategory;
            if (_cursor.isNull(_cursorIndexOfCategory)) {
              _tmpCategory = null;
            } else {
              _tmpCategory = _cursor.getString(_cursorIndexOfCategory);
            }
            final String _tmpReleaseDate;
            if (_cursor.isNull(_cursorIndexOfReleaseDate)) {
              _tmpReleaseDate = null;
            } else {
              _tmpReleaseDate = _cursor.getString(_cursorIndexOfReleaseDate);
            }
            final String _tmpVersion;
            if (_cursor.isNull(_cursorIndexOfVersion)) {
              _tmpVersion = null;
            } else {
              _tmpVersion = _cursor.getString(_cursorIndexOfVersion);
            }
            final String _tmpStatus;
            _tmpStatus = _cursor.getString(_cursorIndexOfStatus);
            final int _tmpQuantity;
            _tmpQuantity = _cursor.getInt(_cursorIndexOfQuantity);
            final Integer _tmpOriginalBagQuantity;
            if (_cursor.isNull(_cursorIndexOfOriginalBagQuantity)) {
              _tmpOriginalBagQuantity = null;
            } else {
              _tmpOriginalBagQuantity = _cursor.getInt(_cursorIndexOfOriginalBagQuantity);
            }
            final String _tmpNote;
            if (_cursor.isNull(_cursorIndexOfNote)) {
              _tmpNote = null;
            } else {
              _tmpNote = _cursor.getString(_cursorIndexOfNote);
            }
            final String _tmpOriginPlaces;
            _tmpOriginPlaces = _cursor.getString(_cursorIndexOfOriginPlaces);
            final String _tmpSaleFormat;
            if (_cursor.isNull(_cursorIndexOfSaleFormat)) {
              _tmpSaleFormat = null;
            } else {
              _tmpSaleFormat = _cursor.getString(_cursorIndexOfSaleFormat);
            }
            final String _tmpSizes;
            _tmpSizes = _cursor.getString(_cursorIndexOfSizes);
            final String _tmpMaterials;
            _tmpMaterials = _cursor.getString(_cursorIndexOfMaterials);
            final String _tmpCharacters;
            _tmpCharacters = _cursor.getString(_cursorIndexOfCharacters);
            final String _tmpArtTypes;
            _tmpArtTypes = _cursor.getString(_cursorIndexOfArtTypes);
            final String _tmpDamageDetails;
            _tmpDamageDetails = _cursor.getString(_cursorIndexOfDamageDetails);
            final String _tmpImagePaths;
            _tmpImagePaths = _cursor.getString(_cursorIndexOfImagePaths);
            final String _tmpReferenceLinks;
            _tmpReferenceLinks = _cursor.getString(_cursorIndexOfReferenceLinks);
            final String _tmpExtraJson;
            _tmpExtraJson = _cursor.getString(_cursorIndexOfExtraJson);
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            final long _tmpUpdatedAt;
            _tmpUpdatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
            _item = new ItemEntity(_tmpId,_tmpNameCn,_tmpNameEn,_tmpIpName,_tmpProducer,_tmpSellMethod,_tmpPrice,_tmpCategory,_tmpReleaseDate,_tmpVersion,_tmpStatus,_tmpQuantity,_tmpOriginalBagQuantity,_tmpNote,_tmpOriginPlaces,_tmpSaleFormat,_tmpSizes,_tmpMaterials,_tmpCharacters,_tmpArtTypes,_tmpDamageDetails,_tmpImagePaths,_tmpReferenceLinks,_tmpExtraJson,_tmpCreatedAt,_tmpUpdatedAt);
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
  public Flow<List<ItemEntity>> getItemsByProducer(final String producer) {
    final String _sql = "\n"
            + "        SELECT * FROM items\n"
            + "        WHERE producer = ?\n"
            + "           OR producer LIKE ? || '|%'\n"
            + "           OR producer LIKE '%|' || ?\n"
            + "           OR producer LIKE '%|' || ? || '|%'\n"
            + "        ORDER BY created_at ASC\n"
            + "    ";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 4);
    int _argIndex = 1;
    _statement.bindString(_argIndex, producer);
    _argIndex = 2;
    _statement.bindString(_argIndex, producer);
    _argIndex = 3;
    _statement.bindString(_argIndex, producer);
    _argIndex = 4;
    _statement.bindString(_argIndex, producer);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"items"}, new Callable<List<ItemEntity>>() {
      @Override
      @NonNull
      public List<ItemEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfNameCn = CursorUtil.getColumnIndexOrThrow(_cursor, "name_cn");
          final int _cursorIndexOfNameEn = CursorUtil.getColumnIndexOrThrow(_cursor, "name_en");
          final int _cursorIndexOfIpName = CursorUtil.getColumnIndexOrThrow(_cursor, "ip_name");
          final int _cursorIndexOfProducer = CursorUtil.getColumnIndexOrThrow(_cursor, "producer");
          final int _cursorIndexOfSellMethod = CursorUtil.getColumnIndexOrThrow(_cursor, "sell_method");
          final int _cursorIndexOfPrice = CursorUtil.getColumnIndexOrThrow(_cursor, "price");
          final int _cursorIndexOfCategory = CursorUtil.getColumnIndexOrThrow(_cursor, "category");
          final int _cursorIndexOfReleaseDate = CursorUtil.getColumnIndexOrThrow(_cursor, "release_date");
          final int _cursorIndexOfVersion = CursorUtil.getColumnIndexOrThrow(_cursor, "version");
          final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
          final int _cursorIndexOfQuantity = CursorUtil.getColumnIndexOrThrow(_cursor, "quantity");
          final int _cursorIndexOfOriginalBagQuantity = CursorUtil.getColumnIndexOrThrow(_cursor, "original_bag_quantity");
          final int _cursorIndexOfNote = CursorUtil.getColumnIndexOrThrow(_cursor, "note");
          final int _cursorIndexOfOriginPlaces = CursorUtil.getColumnIndexOrThrow(_cursor, "origin_places");
          final int _cursorIndexOfSaleFormat = CursorUtil.getColumnIndexOrThrow(_cursor, "sale_format");
          final int _cursorIndexOfSizes = CursorUtil.getColumnIndexOrThrow(_cursor, "sizes");
          final int _cursorIndexOfMaterials = CursorUtil.getColumnIndexOrThrow(_cursor, "materials");
          final int _cursorIndexOfCharacters = CursorUtil.getColumnIndexOrThrow(_cursor, "characters");
          final int _cursorIndexOfArtTypes = CursorUtil.getColumnIndexOrThrow(_cursor, "art_types");
          final int _cursorIndexOfDamageDetails = CursorUtil.getColumnIndexOrThrow(_cursor, "damage_details");
          final int _cursorIndexOfImagePaths = CursorUtil.getColumnIndexOrThrow(_cursor, "image_paths");
          final int _cursorIndexOfReferenceLinks = CursorUtil.getColumnIndexOrThrow(_cursor, "reference_links");
          final int _cursorIndexOfExtraJson = CursorUtil.getColumnIndexOrThrow(_cursor, "extra_json");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "created_at");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updated_at");
          final List<ItemEntity> _result = new ArrayList<ItemEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final ItemEntity _item;
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            final String _tmpNameCn;
            _tmpNameCn = _cursor.getString(_cursorIndexOfNameCn);
            final String _tmpNameEn;
            if (_cursor.isNull(_cursorIndexOfNameEn)) {
              _tmpNameEn = null;
            } else {
              _tmpNameEn = _cursor.getString(_cursorIndexOfNameEn);
            }
            final String _tmpIpName;
            _tmpIpName = _cursor.getString(_cursorIndexOfIpName);
            final String _tmpProducer;
            if (_cursor.isNull(_cursorIndexOfProducer)) {
              _tmpProducer = null;
            } else {
              _tmpProducer = _cursor.getString(_cursorIndexOfProducer);
            }
            final String _tmpSellMethod;
            if (_cursor.isNull(_cursorIndexOfSellMethod)) {
              _tmpSellMethod = null;
            } else {
              _tmpSellMethod = _cursor.getString(_cursorIndexOfSellMethod);
            }
            final String _tmpPrice;
            if (_cursor.isNull(_cursorIndexOfPrice)) {
              _tmpPrice = null;
            } else {
              _tmpPrice = _cursor.getString(_cursorIndexOfPrice);
            }
            final String _tmpCategory;
            if (_cursor.isNull(_cursorIndexOfCategory)) {
              _tmpCategory = null;
            } else {
              _tmpCategory = _cursor.getString(_cursorIndexOfCategory);
            }
            final String _tmpReleaseDate;
            if (_cursor.isNull(_cursorIndexOfReleaseDate)) {
              _tmpReleaseDate = null;
            } else {
              _tmpReleaseDate = _cursor.getString(_cursorIndexOfReleaseDate);
            }
            final String _tmpVersion;
            if (_cursor.isNull(_cursorIndexOfVersion)) {
              _tmpVersion = null;
            } else {
              _tmpVersion = _cursor.getString(_cursorIndexOfVersion);
            }
            final String _tmpStatus;
            _tmpStatus = _cursor.getString(_cursorIndexOfStatus);
            final int _tmpQuantity;
            _tmpQuantity = _cursor.getInt(_cursorIndexOfQuantity);
            final Integer _tmpOriginalBagQuantity;
            if (_cursor.isNull(_cursorIndexOfOriginalBagQuantity)) {
              _tmpOriginalBagQuantity = null;
            } else {
              _tmpOriginalBagQuantity = _cursor.getInt(_cursorIndexOfOriginalBagQuantity);
            }
            final String _tmpNote;
            if (_cursor.isNull(_cursorIndexOfNote)) {
              _tmpNote = null;
            } else {
              _tmpNote = _cursor.getString(_cursorIndexOfNote);
            }
            final String _tmpOriginPlaces;
            _tmpOriginPlaces = _cursor.getString(_cursorIndexOfOriginPlaces);
            final String _tmpSaleFormat;
            if (_cursor.isNull(_cursorIndexOfSaleFormat)) {
              _tmpSaleFormat = null;
            } else {
              _tmpSaleFormat = _cursor.getString(_cursorIndexOfSaleFormat);
            }
            final String _tmpSizes;
            _tmpSizes = _cursor.getString(_cursorIndexOfSizes);
            final String _tmpMaterials;
            _tmpMaterials = _cursor.getString(_cursorIndexOfMaterials);
            final String _tmpCharacters;
            _tmpCharacters = _cursor.getString(_cursorIndexOfCharacters);
            final String _tmpArtTypes;
            _tmpArtTypes = _cursor.getString(_cursorIndexOfArtTypes);
            final String _tmpDamageDetails;
            _tmpDamageDetails = _cursor.getString(_cursorIndexOfDamageDetails);
            final String _tmpImagePaths;
            _tmpImagePaths = _cursor.getString(_cursorIndexOfImagePaths);
            final String _tmpReferenceLinks;
            _tmpReferenceLinks = _cursor.getString(_cursorIndexOfReferenceLinks);
            final String _tmpExtraJson;
            _tmpExtraJson = _cursor.getString(_cursorIndexOfExtraJson);
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            final long _tmpUpdatedAt;
            _tmpUpdatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
            _item = new ItemEntity(_tmpId,_tmpNameCn,_tmpNameEn,_tmpIpName,_tmpProducer,_tmpSellMethod,_tmpPrice,_tmpCategory,_tmpReleaseDate,_tmpVersion,_tmpStatus,_tmpQuantity,_tmpOriginalBagQuantity,_tmpNote,_tmpOriginPlaces,_tmpSaleFormat,_tmpSizes,_tmpMaterials,_tmpCharacters,_tmpArtTypes,_tmpDamageDetails,_tmpImagePaths,_tmpReferenceLinks,_tmpExtraJson,_tmpCreatedAt,_tmpUpdatedAt);
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
  public Flow<List<ItemEntity>> search(final String keyword) {
    final String _sql = "\n"
            + "        SELECT * FROM items \n"
            + "        WHERE name_cn LIKE '%' || ? || '%' \n"
            + "           OR ip_name LIKE '%' || ? || '%' \n"
            + "           OR name_en LIKE '%' || ? || '%'\n"
            + "        ORDER BY created_at ASC\n"
            + "    ";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 3);
    int _argIndex = 1;
    _statement.bindString(_argIndex, keyword);
    _argIndex = 2;
    _statement.bindString(_argIndex, keyword);
    _argIndex = 3;
    _statement.bindString(_argIndex, keyword);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"items"}, new Callable<List<ItemEntity>>() {
      @Override
      @NonNull
      public List<ItemEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfNameCn = CursorUtil.getColumnIndexOrThrow(_cursor, "name_cn");
          final int _cursorIndexOfNameEn = CursorUtil.getColumnIndexOrThrow(_cursor, "name_en");
          final int _cursorIndexOfIpName = CursorUtil.getColumnIndexOrThrow(_cursor, "ip_name");
          final int _cursorIndexOfProducer = CursorUtil.getColumnIndexOrThrow(_cursor, "producer");
          final int _cursorIndexOfSellMethod = CursorUtil.getColumnIndexOrThrow(_cursor, "sell_method");
          final int _cursorIndexOfPrice = CursorUtil.getColumnIndexOrThrow(_cursor, "price");
          final int _cursorIndexOfCategory = CursorUtil.getColumnIndexOrThrow(_cursor, "category");
          final int _cursorIndexOfReleaseDate = CursorUtil.getColumnIndexOrThrow(_cursor, "release_date");
          final int _cursorIndexOfVersion = CursorUtil.getColumnIndexOrThrow(_cursor, "version");
          final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
          final int _cursorIndexOfQuantity = CursorUtil.getColumnIndexOrThrow(_cursor, "quantity");
          final int _cursorIndexOfOriginalBagQuantity = CursorUtil.getColumnIndexOrThrow(_cursor, "original_bag_quantity");
          final int _cursorIndexOfNote = CursorUtil.getColumnIndexOrThrow(_cursor, "note");
          final int _cursorIndexOfOriginPlaces = CursorUtil.getColumnIndexOrThrow(_cursor, "origin_places");
          final int _cursorIndexOfSaleFormat = CursorUtil.getColumnIndexOrThrow(_cursor, "sale_format");
          final int _cursorIndexOfSizes = CursorUtil.getColumnIndexOrThrow(_cursor, "sizes");
          final int _cursorIndexOfMaterials = CursorUtil.getColumnIndexOrThrow(_cursor, "materials");
          final int _cursorIndexOfCharacters = CursorUtil.getColumnIndexOrThrow(_cursor, "characters");
          final int _cursorIndexOfArtTypes = CursorUtil.getColumnIndexOrThrow(_cursor, "art_types");
          final int _cursorIndexOfDamageDetails = CursorUtil.getColumnIndexOrThrow(_cursor, "damage_details");
          final int _cursorIndexOfImagePaths = CursorUtil.getColumnIndexOrThrow(_cursor, "image_paths");
          final int _cursorIndexOfReferenceLinks = CursorUtil.getColumnIndexOrThrow(_cursor, "reference_links");
          final int _cursorIndexOfExtraJson = CursorUtil.getColumnIndexOrThrow(_cursor, "extra_json");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "created_at");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updated_at");
          final List<ItemEntity> _result = new ArrayList<ItemEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final ItemEntity _item;
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            final String _tmpNameCn;
            _tmpNameCn = _cursor.getString(_cursorIndexOfNameCn);
            final String _tmpNameEn;
            if (_cursor.isNull(_cursorIndexOfNameEn)) {
              _tmpNameEn = null;
            } else {
              _tmpNameEn = _cursor.getString(_cursorIndexOfNameEn);
            }
            final String _tmpIpName;
            _tmpIpName = _cursor.getString(_cursorIndexOfIpName);
            final String _tmpProducer;
            if (_cursor.isNull(_cursorIndexOfProducer)) {
              _tmpProducer = null;
            } else {
              _tmpProducer = _cursor.getString(_cursorIndexOfProducer);
            }
            final String _tmpSellMethod;
            if (_cursor.isNull(_cursorIndexOfSellMethod)) {
              _tmpSellMethod = null;
            } else {
              _tmpSellMethod = _cursor.getString(_cursorIndexOfSellMethod);
            }
            final String _tmpPrice;
            if (_cursor.isNull(_cursorIndexOfPrice)) {
              _tmpPrice = null;
            } else {
              _tmpPrice = _cursor.getString(_cursorIndexOfPrice);
            }
            final String _tmpCategory;
            if (_cursor.isNull(_cursorIndexOfCategory)) {
              _tmpCategory = null;
            } else {
              _tmpCategory = _cursor.getString(_cursorIndexOfCategory);
            }
            final String _tmpReleaseDate;
            if (_cursor.isNull(_cursorIndexOfReleaseDate)) {
              _tmpReleaseDate = null;
            } else {
              _tmpReleaseDate = _cursor.getString(_cursorIndexOfReleaseDate);
            }
            final String _tmpVersion;
            if (_cursor.isNull(_cursorIndexOfVersion)) {
              _tmpVersion = null;
            } else {
              _tmpVersion = _cursor.getString(_cursorIndexOfVersion);
            }
            final String _tmpStatus;
            _tmpStatus = _cursor.getString(_cursorIndexOfStatus);
            final int _tmpQuantity;
            _tmpQuantity = _cursor.getInt(_cursorIndexOfQuantity);
            final Integer _tmpOriginalBagQuantity;
            if (_cursor.isNull(_cursorIndexOfOriginalBagQuantity)) {
              _tmpOriginalBagQuantity = null;
            } else {
              _tmpOriginalBagQuantity = _cursor.getInt(_cursorIndexOfOriginalBagQuantity);
            }
            final String _tmpNote;
            if (_cursor.isNull(_cursorIndexOfNote)) {
              _tmpNote = null;
            } else {
              _tmpNote = _cursor.getString(_cursorIndexOfNote);
            }
            final String _tmpOriginPlaces;
            _tmpOriginPlaces = _cursor.getString(_cursorIndexOfOriginPlaces);
            final String _tmpSaleFormat;
            if (_cursor.isNull(_cursorIndexOfSaleFormat)) {
              _tmpSaleFormat = null;
            } else {
              _tmpSaleFormat = _cursor.getString(_cursorIndexOfSaleFormat);
            }
            final String _tmpSizes;
            _tmpSizes = _cursor.getString(_cursorIndexOfSizes);
            final String _tmpMaterials;
            _tmpMaterials = _cursor.getString(_cursorIndexOfMaterials);
            final String _tmpCharacters;
            _tmpCharacters = _cursor.getString(_cursorIndexOfCharacters);
            final String _tmpArtTypes;
            _tmpArtTypes = _cursor.getString(_cursorIndexOfArtTypes);
            final String _tmpDamageDetails;
            _tmpDamageDetails = _cursor.getString(_cursorIndexOfDamageDetails);
            final String _tmpImagePaths;
            _tmpImagePaths = _cursor.getString(_cursorIndexOfImagePaths);
            final String _tmpReferenceLinks;
            _tmpReferenceLinks = _cursor.getString(_cursorIndexOfReferenceLinks);
            final String _tmpExtraJson;
            _tmpExtraJson = _cursor.getString(_cursorIndexOfExtraJson);
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            final long _tmpUpdatedAt;
            _tmpUpdatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
            _item = new ItemEntity(_tmpId,_tmpNameCn,_tmpNameEn,_tmpIpName,_tmpProducer,_tmpSellMethod,_tmpPrice,_tmpCategory,_tmpReleaseDate,_tmpVersion,_tmpStatus,_tmpQuantity,_tmpOriginalBagQuantity,_tmpNote,_tmpOriginPlaces,_tmpSaleFormat,_tmpSizes,_tmpMaterials,_tmpCharacters,_tmpArtTypes,_tmpDamageDetails,_tmpImagePaths,_tmpReferenceLinks,_tmpExtraJson,_tmpCreatedAt,_tmpUpdatedAt);
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
  public Flow<List<ItemEntity>> getItemsByCharacter(final String character) {
    final String _sql = "SELECT * FROM items WHERE characters LIKE '%' || ? || '%' ORDER BY created_at ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, character);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"items"}, new Callable<List<ItemEntity>>() {
      @Override
      @NonNull
      public List<ItemEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfNameCn = CursorUtil.getColumnIndexOrThrow(_cursor, "name_cn");
          final int _cursorIndexOfNameEn = CursorUtil.getColumnIndexOrThrow(_cursor, "name_en");
          final int _cursorIndexOfIpName = CursorUtil.getColumnIndexOrThrow(_cursor, "ip_name");
          final int _cursorIndexOfProducer = CursorUtil.getColumnIndexOrThrow(_cursor, "producer");
          final int _cursorIndexOfSellMethod = CursorUtil.getColumnIndexOrThrow(_cursor, "sell_method");
          final int _cursorIndexOfPrice = CursorUtil.getColumnIndexOrThrow(_cursor, "price");
          final int _cursorIndexOfCategory = CursorUtil.getColumnIndexOrThrow(_cursor, "category");
          final int _cursorIndexOfReleaseDate = CursorUtil.getColumnIndexOrThrow(_cursor, "release_date");
          final int _cursorIndexOfVersion = CursorUtil.getColumnIndexOrThrow(_cursor, "version");
          final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
          final int _cursorIndexOfQuantity = CursorUtil.getColumnIndexOrThrow(_cursor, "quantity");
          final int _cursorIndexOfOriginalBagQuantity = CursorUtil.getColumnIndexOrThrow(_cursor, "original_bag_quantity");
          final int _cursorIndexOfNote = CursorUtil.getColumnIndexOrThrow(_cursor, "note");
          final int _cursorIndexOfOriginPlaces = CursorUtil.getColumnIndexOrThrow(_cursor, "origin_places");
          final int _cursorIndexOfSaleFormat = CursorUtil.getColumnIndexOrThrow(_cursor, "sale_format");
          final int _cursorIndexOfSizes = CursorUtil.getColumnIndexOrThrow(_cursor, "sizes");
          final int _cursorIndexOfMaterials = CursorUtil.getColumnIndexOrThrow(_cursor, "materials");
          final int _cursorIndexOfCharacters = CursorUtil.getColumnIndexOrThrow(_cursor, "characters");
          final int _cursorIndexOfArtTypes = CursorUtil.getColumnIndexOrThrow(_cursor, "art_types");
          final int _cursorIndexOfDamageDetails = CursorUtil.getColumnIndexOrThrow(_cursor, "damage_details");
          final int _cursorIndexOfImagePaths = CursorUtil.getColumnIndexOrThrow(_cursor, "image_paths");
          final int _cursorIndexOfReferenceLinks = CursorUtil.getColumnIndexOrThrow(_cursor, "reference_links");
          final int _cursorIndexOfExtraJson = CursorUtil.getColumnIndexOrThrow(_cursor, "extra_json");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "created_at");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updated_at");
          final List<ItemEntity> _result = new ArrayList<ItemEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final ItemEntity _item;
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            final String _tmpNameCn;
            _tmpNameCn = _cursor.getString(_cursorIndexOfNameCn);
            final String _tmpNameEn;
            if (_cursor.isNull(_cursorIndexOfNameEn)) {
              _tmpNameEn = null;
            } else {
              _tmpNameEn = _cursor.getString(_cursorIndexOfNameEn);
            }
            final String _tmpIpName;
            _tmpIpName = _cursor.getString(_cursorIndexOfIpName);
            final String _tmpProducer;
            if (_cursor.isNull(_cursorIndexOfProducer)) {
              _tmpProducer = null;
            } else {
              _tmpProducer = _cursor.getString(_cursorIndexOfProducer);
            }
            final String _tmpSellMethod;
            if (_cursor.isNull(_cursorIndexOfSellMethod)) {
              _tmpSellMethod = null;
            } else {
              _tmpSellMethod = _cursor.getString(_cursorIndexOfSellMethod);
            }
            final String _tmpPrice;
            if (_cursor.isNull(_cursorIndexOfPrice)) {
              _tmpPrice = null;
            } else {
              _tmpPrice = _cursor.getString(_cursorIndexOfPrice);
            }
            final String _tmpCategory;
            if (_cursor.isNull(_cursorIndexOfCategory)) {
              _tmpCategory = null;
            } else {
              _tmpCategory = _cursor.getString(_cursorIndexOfCategory);
            }
            final String _tmpReleaseDate;
            if (_cursor.isNull(_cursorIndexOfReleaseDate)) {
              _tmpReleaseDate = null;
            } else {
              _tmpReleaseDate = _cursor.getString(_cursorIndexOfReleaseDate);
            }
            final String _tmpVersion;
            if (_cursor.isNull(_cursorIndexOfVersion)) {
              _tmpVersion = null;
            } else {
              _tmpVersion = _cursor.getString(_cursorIndexOfVersion);
            }
            final String _tmpStatus;
            _tmpStatus = _cursor.getString(_cursorIndexOfStatus);
            final int _tmpQuantity;
            _tmpQuantity = _cursor.getInt(_cursorIndexOfQuantity);
            final Integer _tmpOriginalBagQuantity;
            if (_cursor.isNull(_cursorIndexOfOriginalBagQuantity)) {
              _tmpOriginalBagQuantity = null;
            } else {
              _tmpOriginalBagQuantity = _cursor.getInt(_cursorIndexOfOriginalBagQuantity);
            }
            final String _tmpNote;
            if (_cursor.isNull(_cursorIndexOfNote)) {
              _tmpNote = null;
            } else {
              _tmpNote = _cursor.getString(_cursorIndexOfNote);
            }
            final String _tmpOriginPlaces;
            _tmpOriginPlaces = _cursor.getString(_cursorIndexOfOriginPlaces);
            final String _tmpSaleFormat;
            if (_cursor.isNull(_cursorIndexOfSaleFormat)) {
              _tmpSaleFormat = null;
            } else {
              _tmpSaleFormat = _cursor.getString(_cursorIndexOfSaleFormat);
            }
            final String _tmpSizes;
            _tmpSizes = _cursor.getString(_cursorIndexOfSizes);
            final String _tmpMaterials;
            _tmpMaterials = _cursor.getString(_cursorIndexOfMaterials);
            final String _tmpCharacters;
            _tmpCharacters = _cursor.getString(_cursorIndexOfCharacters);
            final String _tmpArtTypes;
            _tmpArtTypes = _cursor.getString(_cursorIndexOfArtTypes);
            final String _tmpDamageDetails;
            _tmpDamageDetails = _cursor.getString(_cursorIndexOfDamageDetails);
            final String _tmpImagePaths;
            _tmpImagePaths = _cursor.getString(_cursorIndexOfImagePaths);
            final String _tmpReferenceLinks;
            _tmpReferenceLinks = _cursor.getString(_cursorIndexOfReferenceLinks);
            final String _tmpExtraJson;
            _tmpExtraJson = _cursor.getString(_cursorIndexOfExtraJson);
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            final long _tmpUpdatedAt;
            _tmpUpdatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
            _item = new ItemEntity(_tmpId,_tmpNameCn,_tmpNameEn,_tmpIpName,_tmpProducer,_tmpSellMethod,_tmpPrice,_tmpCategory,_tmpReleaseDate,_tmpVersion,_tmpStatus,_tmpQuantity,_tmpOriginalBagQuantity,_tmpNote,_tmpOriginPlaces,_tmpSaleFormat,_tmpSizes,_tmpMaterials,_tmpCharacters,_tmpArtTypes,_tmpDamageDetails,_tmpImagePaths,_tmpReferenceLinks,_tmpExtraJson,_tmpCreatedAt,_tmpUpdatedAt);
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
  public Flow<List<ItemEntity>> getItemsByCategoryExact(final String category) {
    final String _sql = "\n"
            + "        SELECT * FROM items\n"
            + "        WHERE category = ?\n"
            + "           OR category LIKE ? || '|%'\n"
            + "           OR category LIKE '%|' || ?\n"
            + "           OR category LIKE '%|' || ? || '|%'\n"
            + "        ORDER BY created_at ASC\n"
            + "    ";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 4);
    int _argIndex = 1;
    _statement.bindString(_argIndex, category);
    _argIndex = 2;
    _statement.bindString(_argIndex, category);
    _argIndex = 3;
    _statement.bindString(_argIndex, category);
    _argIndex = 4;
    _statement.bindString(_argIndex, category);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"items"}, new Callable<List<ItemEntity>>() {
      @Override
      @NonNull
      public List<ItemEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfNameCn = CursorUtil.getColumnIndexOrThrow(_cursor, "name_cn");
          final int _cursorIndexOfNameEn = CursorUtil.getColumnIndexOrThrow(_cursor, "name_en");
          final int _cursorIndexOfIpName = CursorUtil.getColumnIndexOrThrow(_cursor, "ip_name");
          final int _cursorIndexOfProducer = CursorUtil.getColumnIndexOrThrow(_cursor, "producer");
          final int _cursorIndexOfSellMethod = CursorUtil.getColumnIndexOrThrow(_cursor, "sell_method");
          final int _cursorIndexOfPrice = CursorUtil.getColumnIndexOrThrow(_cursor, "price");
          final int _cursorIndexOfCategory = CursorUtil.getColumnIndexOrThrow(_cursor, "category");
          final int _cursorIndexOfReleaseDate = CursorUtil.getColumnIndexOrThrow(_cursor, "release_date");
          final int _cursorIndexOfVersion = CursorUtil.getColumnIndexOrThrow(_cursor, "version");
          final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
          final int _cursorIndexOfQuantity = CursorUtil.getColumnIndexOrThrow(_cursor, "quantity");
          final int _cursorIndexOfOriginalBagQuantity = CursorUtil.getColumnIndexOrThrow(_cursor, "original_bag_quantity");
          final int _cursorIndexOfNote = CursorUtil.getColumnIndexOrThrow(_cursor, "note");
          final int _cursorIndexOfOriginPlaces = CursorUtil.getColumnIndexOrThrow(_cursor, "origin_places");
          final int _cursorIndexOfSaleFormat = CursorUtil.getColumnIndexOrThrow(_cursor, "sale_format");
          final int _cursorIndexOfSizes = CursorUtil.getColumnIndexOrThrow(_cursor, "sizes");
          final int _cursorIndexOfMaterials = CursorUtil.getColumnIndexOrThrow(_cursor, "materials");
          final int _cursorIndexOfCharacters = CursorUtil.getColumnIndexOrThrow(_cursor, "characters");
          final int _cursorIndexOfArtTypes = CursorUtil.getColumnIndexOrThrow(_cursor, "art_types");
          final int _cursorIndexOfDamageDetails = CursorUtil.getColumnIndexOrThrow(_cursor, "damage_details");
          final int _cursorIndexOfImagePaths = CursorUtil.getColumnIndexOrThrow(_cursor, "image_paths");
          final int _cursorIndexOfReferenceLinks = CursorUtil.getColumnIndexOrThrow(_cursor, "reference_links");
          final int _cursorIndexOfExtraJson = CursorUtil.getColumnIndexOrThrow(_cursor, "extra_json");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "created_at");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updated_at");
          final List<ItemEntity> _result = new ArrayList<ItemEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final ItemEntity _item;
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            final String _tmpNameCn;
            _tmpNameCn = _cursor.getString(_cursorIndexOfNameCn);
            final String _tmpNameEn;
            if (_cursor.isNull(_cursorIndexOfNameEn)) {
              _tmpNameEn = null;
            } else {
              _tmpNameEn = _cursor.getString(_cursorIndexOfNameEn);
            }
            final String _tmpIpName;
            _tmpIpName = _cursor.getString(_cursorIndexOfIpName);
            final String _tmpProducer;
            if (_cursor.isNull(_cursorIndexOfProducer)) {
              _tmpProducer = null;
            } else {
              _tmpProducer = _cursor.getString(_cursorIndexOfProducer);
            }
            final String _tmpSellMethod;
            if (_cursor.isNull(_cursorIndexOfSellMethod)) {
              _tmpSellMethod = null;
            } else {
              _tmpSellMethod = _cursor.getString(_cursorIndexOfSellMethod);
            }
            final String _tmpPrice;
            if (_cursor.isNull(_cursorIndexOfPrice)) {
              _tmpPrice = null;
            } else {
              _tmpPrice = _cursor.getString(_cursorIndexOfPrice);
            }
            final String _tmpCategory;
            if (_cursor.isNull(_cursorIndexOfCategory)) {
              _tmpCategory = null;
            } else {
              _tmpCategory = _cursor.getString(_cursorIndexOfCategory);
            }
            final String _tmpReleaseDate;
            if (_cursor.isNull(_cursorIndexOfReleaseDate)) {
              _tmpReleaseDate = null;
            } else {
              _tmpReleaseDate = _cursor.getString(_cursorIndexOfReleaseDate);
            }
            final String _tmpVersion;
            if (_cursor.isNull(_cursorIndexOfVersion)) {
              _tmpVersion = null;
            } else {
              _tmpVersion = _cursor.getString(_cursorIndexOfVersion);
            }
            final String _tmpStatus;
            _tmpStatus = _cursor.getString(_cursorIndexOfStatus);
            final int _tmpQuantity;
            _tmpQuantity = _cursor.getInt(_cursorIndexOfQuantity);
            final Integer _tmpOriginalBagQuantity;
            if (_cursor.isNull(_cursorIndexOfOriginalBagQuantity)) {
              _tmpOriginalBagQuantity = null;
            } else {
              _tmpOriginalBagQuantity = _cursor.getInt(_cursorIndexOfOriginalBagQuantity);
            }
            final String _tmpNote;
            if (_cursor.isNull(_cursorIndexOfNote)) {
              _tmpNote = null;
            } else {
              _tmpNote = _cursor.getString(_cursorIndexOfNote);
            }
            final String _tmpOriginPlaces;
            _tmpOriginPlaces = _cursor.getString(_cursorIndexOfOriginPlaces);
            final String _tmpSaleFormat;
            if (_cursor.isNull(_cursorIndexOfSaleFormat)) {
              _tmpSaleFormat = null;
            } else {
              _tmpSaleFormat = _cursor.getString(_cursorIndexOfSaleFormat);
            }
            final String _tmpSizes;
            _tmpSizes = _cursor.getString(_cursorIndexOfSizes);
            final String _tmpMaterials;
            _tmpMaterials = _cursor.getString(_cursorIndexOfMaterials);
            final String _tmpCharacters;
            _tmpCharacters = _cursor.getString(_cursorIndexOfCharacters);
            final String _tmpArtTypes;
            _tmpArtTypes = _cursor.getString(_cursorIndexOfArtTypes);
            final String _tmpDamageDetails;
            _tmpDamageDetails = _cursor.getString(_cursorIndexOfDamageDetails);
            final String _tmpImagePaths;
            _tmpImagePaths = _cursor.getString(_cursorIndexOfImagePaths);
            final String _tmpReferenceLinks;
            _tmpReferenceLinks = _cursor.getString(_cursorIndexOfReferenceLinks);
            final String _tmpExtraJson;
            _tmpExtraJson = _cursor.getString(_cursorIndexOfExtraJson);
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            final long _tmpUpdatedAt;
            _tmpUpdatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
            _item = new ItemEntity(_tmpId,_tmpNameCn,_tmpNameEn,_tmpIpName,_tmpProducer,_tmpSellMethod,_tmpPrice,_tmpCategory,_tmpReleaseDate,_tmpVersion,_tmpStatus,_tmpQuantity,_tmpOriginalBagQuantity,_tmpNote,_tmpOriginPlaces,_tmpSaleFormat,_tmpSizes,_tmpMaterials,_tmpCharacters,_tmpArtTypes,_tmpDamageDetails,_tmpImagePaths,_tmpReferenceLinks,_tmpExtraJson,_tmpCreatedAt,_tmpUpdatedAt);
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
  public Object count(final Continuation<? super Integer> $completion) {
    final String _sql = "SELECT COUNT(*) FROM items";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Integer>() {
      @Override
      @NonNull
      public Integer call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Integer _result;
          if (_cursor.moveToFirst()) {
            final int _tmp;
            _tmp = _cursor.getInt(0);
            _result = _tmp;
          } else {
            _result = 0;
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
  public Object deleteByIds(final List<String> ids, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final StringBuilder _stringBuilder = StringUtil.newStringBuilder();
        _stringBuilder.append("DELETE FROM items WHERE id IN (");
        final int _inputSize = ids.size();
        StringUtil.appendPlaceholders(_stringBuilder, _inputSize);
        _stringBuilder.append(")");
        final String _sql = _stringBuilder.toString();
        final SupportSQLiteStatement _stmt = __db.compileStatement(_sql);
        int _argIndex = 1;
        for (String _item : ids) {
          _stmt.bindString(_argIndex, _item);
          _argIndex++;
        }
        __db.beginTransaction();
        try {
          _stmt.executeUpdateDelete();
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
