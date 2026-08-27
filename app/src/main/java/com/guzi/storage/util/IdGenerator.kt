package com.guzi.storage.util

/**
 * 短 ID 生成器
 *
 * 生成 ≤12 位的唯一短 ID，格式：毫秒时间戳 base36（约 8 位）+ 3 位随机字符，共约 11 位。
 * 时间维度保证单调递增，随机后缀降低同毫秒碰撞概率。
 */
object IdGenerator {
    private const val RANDOM_SUFFIX_LENGTH = 3
    private val ALPHABET = "0123456789abcdefghijklmnopqrstuvwxyz".toCharArray()
    private val RANDOM = java.security.SecureRandom()

    fun generate(): String {
        val tsPart = java.lang.Long.toString(System.currentTimeMillis(), 36)
        val suffix = StringBuilder(RANDOM_SUFFIX_LENGTH)
        repeat(RANDOM_SUFFIX_LENGTH) {
            suffix.append(ALPHABET[RANDOM.nextInt(ALPHABET.size)])
        }
        return (tsPart + suffix).lowercase()
    }
}
