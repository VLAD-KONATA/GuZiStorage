package com.guzi.storage.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import dagger.hilt.android.qualifiers.ApplicationContext;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * 图片文件管理工具
 *
 * 负责图片文件的复制、压缩、存储和删除
 * 所有图片存储在应用私有目录 images/{itemId}/ 下
 */
@javax.inject.Singleton()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000X\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0012\n\u0002\b\u0002\b\u0007\u0018\u0000 (2\u00020\u0001:\u0001(B\u0011\b\u0007\u0012\b\b\u0001\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u001a\u0010\t\u001a\u0004\u0018\u00010\n2\u0006\u0010\u000b\u001a\u00020\n2\u0006\u0010\f\u001a\u00020\u0006H\u0002J \u0010\r\u001a\u0004\u0018\u00010\n2\u0006\u0010\u000b\u001a\u00020\n2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u0011J\"\u0010\u0012\u001a\u0004\u0018\u00010\n2\u0006\u0010\u000b\u001a\u00020\n2\u0006\u0010\f\u001a\u00020\u00062\b\b\u0002\u0010\u0013\u001a\u00020\u0011J\u0018\u0010\u0014\u001a\u0004\u0018\u00010\n2\u0006\u0010\u000b\u001a\u00020\n2\u0006\u0010\u000e\u001a\u00020\u000fJ*\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\n0\u00162\u0006\u0010\u000b\u001a\u00020\n2\f\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u000f0\u00162\u0006\u0010\u0010\u001a\u00020\u0011J\u000e\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\nJ\u000e\u0010\u001b\u001a\u00020\u00192\u0006\u0010\u000b\u001a\u00020\nJ\u000e\u0010\u001c\u001a\u00020\n2\u0006\u0010\u001a\u001a\u00020\nJ\u000e\u0010\u001d\u001a\u00020\u00062\u0006\u0010\u001a\u001a\u00020\nJ\u000e\u0010\u001e\u001a\u00020\u00062\u0006\u0010\u000b\u001a\u00020\nJ\u0014\u0010\u001f\u001a\b\u0012\u0004\u0012\u00020\n0\u00162\u0006\u0010\u000b\u001a\u00020\nJ\u0018\u0010 \u001a\u00020!2\u0006\u0010\"\u001a\u00020!2\u0006\u0010#\u001a\u00020$H\u0002J\u0018\u0010%\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\n2\u0006\u0010&\u001a\u00020\'H\u0002R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0005\u001a\u00020\u00068BX\u0082\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0007\u0010\b\u00a8\u0006)"}, d2 = {"Lcom/guzi/storage/util/ImageFileManager;", "", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "imagesDir", "Ljava/io/File;", "getImagesDir", "()Ljava/io/File;", "compressLargeImageFile", "", "itemId", "sourceFile", "copyImageCompressed", "uri", "Landroid/net/Uri;", "compress", "", "copyImageFile", "smartCompress", "copyImageToItemDir", "copyImages", "", "uris", "deleteImage", "", "relativePath", "deleteItemImages", "getAbsolutePath", "getFile", "getItemImageDir", "getItemImagePaths", "scaleBitmapToMaxDimension", "Landroid/graphics/Bitmap;", "bitmap", "maxDimension", "", "writeCompressedBytes", "bytes", "", "Companion", "app_debug"})
public final class ImageFileManager {
    @org.jetbrains.annotations.NotNull()
    private final android.content.Context context = null;
    private static final int COMPRESSED_MAX_DIMENSION = 1024;
    private static final int COMPRESSED_QUALITY = 85;
    private static final int SMART_COMPRESS_THRESHOLD_BYTES = 1048576;
    private static final int SMART_COMPRESS_TARGET_BYTES = 819200;
    private static final int SMART_COMPRESS_INITIAL_DIMENSION = 1600;
    private static final int SMART_COMPRESS_MIN_DIMENSION = 320;
    private static final int SMART_COMPRESS_MIN_QUALITY = 40;
    @org.jetbrains.annotations.NotNull()
    public static final com.guzi.storage.util.ImageFileManager.Companion Companion = null;
    
    @javax.inject.Inject()
    public ImageFileManager(@dagger.hilt.android.qualifiers.ApplicationContext()
    @org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        super();
    }
    
    private final java.io.File getImagesDir() {
        return null;
    }
    
    /**
     * 获取指定物品的图片目录
     */
    @org.jetbrains.annotations.NotNull()
    public final java.io.File getItemImageDir(@org.jetbrains.annotations.NotNull()
    java.lang.String itemId) {
        return null;
    }
    
    /**
     * 从 URI 复制图片到物品目录（原图）
     *
     * @param itemId 物品 ID
     * @param uri 图片来源 URI（从相册选择）
     * @return 保存后的相对路径（相对于 filesDir），如 "images/xxx/abc123.jpg"
     */
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String copyImageToItemDir(@org.jetbrains.annotations.NotNull()
    java.lang.String itemId, @org.jetbrains.annotations.NotNull()
    android.net.Uri uri) {
        return null;
    }
    
    /**
     * 从 URI 复制图片到物品目录，可选择是否压缩
     *
     * @param compress true=压缩存储（最大1024px，JPEG质量85），false=存储原图
     */
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String copyImageCompressed(@org.jetbrains.annotations.NotNull()
    java.lang.String itemId, @org.jetbrains.annotations.NotNull()
    android.net.Uri uri, boolean compress) {
        return null;
    }
    
    /**
     * 从 File 复制图片到物品目录（用于 ZIP 导入，保留原始扩展名）
     */
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String copyImageFile(@org.jetbrains.annotations.NotNull()
    java.lang.String itemId, @org.jetbrains.annotations.NotNull()
    java.io.File sourceFile, boolean smartCompress) {
        return null;
    }
    
    private final java.lang.String compressLargeImageFile(java.lang.String itemId, java.io.File sourceFile) {
        return null;
    }
    
    private final android.graphics.Bitmap scaleBitmapToMaxDimension(android.graphics.Bitmap bitmap, int maxDimension) {
        return null;
    }
    
    private final java.lang.String writeCompressedBytes(java.lang.String itemId, byte[] bytes) {
        return null;
    }
    
    /**
     * 批量从 URI 添加图片
     *
     * @param compress true=压缩存储，false=存储原图
     * @return 成功保存的相对路径列表
     */
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<java.lang.String> copyImages(@org.jetbrains.annotations.NotNull()
    java.lang.String itemId, @org.jetbrains.annotations.NotNull()
    java.util.List<? extends android.net.Uri> uris, boolean compress) {
        return null;
    }
    
    /**
     * 获取图片的绝对文件路径
     */
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getAbsolutePath(@org.jetbrains.annotations.NotNull()
    java.lang.String relativePath) {
        return null;
    }
    
    /**
     * 获取图片 File 对象
     */
    @org.jetbrains.annotations.NotNull()
    public final java.io.File getFile(@org.jetbrains.annotations.NotNull()
    java.lang.String relativePath) {
        return null;
    }
    
    /**
     * 删除物品的所有图片
     */
    public final void deleteItemImages(@org.jetbrains.annotations.NotNull()
    java.lang.String itemId) {
    }
    
    /**
     * 删除指定图片
     */
    public final void deleteImage(@org.jetbrains.annotations.NotNull()
    java.lang.String relativePath) {
    }
    
    /**
     * 获取物品所有图片的相对路径列表
     */
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<java.lang.String> getItemImagePaths(@org.jetbrains.annotations.NotNull()
    java.lang.String itemId) {
        return null;
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0007\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u000b"}, d2 = {"Lcom/guzi/storage/util/ImageFileManager$Companion;", "", "()V", "COMPRESSED_MAX_DIMENSION", "", "COMPRESSED_QUALITY", "SMART_COMPRESS_INITIAL_DIMENSION", "SMART_COMPRESS_MIN_DIMENSION", "SMART_COMPRESS_MIN_QUALITY", "SMART_COMPRESS_TARGET_BYTES", "SMART_COMPRESS_THRESHOLD_BYTES", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
    }
}