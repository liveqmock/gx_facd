package com.wangzhixuan.commons.utils;

import java.util.Collection;
import java.util.Date;
import java.util.UUID;

/**
 * 继承自Spring util的工具类，减少jar依赖
 * @author L.cm
 */
public class StringUtils extends org.springframework.util.StringUtils {
    /**
     * Check whether the given {@code CharSequence} contains actual <em>text</em>.
     * <p>More specifically, this method returns {@code true} if the
     * {@code CharSequence} is not {@code null}, its length is greater than
     * 0, and it contains at least one non-whitespace character.
     * <p><pre class="code">
     * StringUtils.isBlank(null) = true
     * StringUtils.isBlank("") = true
     * StringUtils.isBlank(" ") = true
     * StringUtils.isBlank("12345") = false
     * StringUtils.isBlank(" 12345 ") = false
     * </pre>
     * @param str the {@code CharSequence} to check (may be {@code null})
     * @return {@code true} if the {@code CharSequence} is not {@code null},
     * its length is greater than 0, and it does not contain whitespace only
     * @see Character#isWhitespace
     */
    public static boolean isBlank(final CharSequence cs) {
        return !StringUtils.isNotBlank(cs);
    }
    
    /**
     * <p>Checks if a CharSequence is not empty (""), not null and not whitespace only.</p>
     *
     * <pre>
     * StringUtils.isNotBlank(null)      = false
     * StringUtils.isNotBlank("")        = false
     * StringUtils.isNotBlank(" ")       = false
     * StringUtils.isNotBlank("bob")     = true
     * StringUtils.isNotBlank("  bob  ") = true
     * </pre>
     *
     * @param cs  the CharSequence to check, may be null
     * @return {@code true} if the CharSequence is
     *  not empty and not null and not whitespace
     * @see Character#isWhitespace
     */
    public static boolean isNotBlank(final CharSequence cs) {
        return StringUtils.hasText(cs);
    }
    
    /**
     * Convert a {@code Collection} into a delimited {@code String} (e.g. CSV).
     * <p>Useful for {@code toString()} implementations.
     * @param coll the {@code Collection} to convert
     * @param delim the delimiter to use (typically a ",")
     * @return the delimited {@code String}
     */
    public static String join(Collection<?> coll, String delim) {
        return StringUtils.collectionToDelimitedString(coll, delim);
    }
    
    /**
     * Convert a {@code String} array into a delimited {@code String} (e.g. CSV).
     * <p>Useful for {@code toString()} implementations.
     * @param arr the array to display
     * @param delim the delimiter to use (typically a ",")
     * @return the delimited {@code String}
     */
    public static String join(Object[] arr, String delim) {
        return StringUtils.arrayToDelimitedString(arr, delim);
    }
    
    /**
     * 生成uuid
     * @return UUID
     */
    public static String getUUId() {
        return UUID.randomUUID().toString();
    }
    /**
	 * @return 0 is file type wrong!
	 */
	public static int getType(String mMimeType) {
		if (mMimeType.startsWith("image/"))
			return 1;
		if (mMimeType.startsWith("audio/"))
			return 2;
		if (mMimeType.startsWith("video/"))
			return 3;
		return 0;
	}
	public static int getTime(Date a , Date b){
		long c = a.getTime();
		long d = b.getTime();
		return (int)Math.abs(c-d)/60000;
	}
	public static String politicalStatus(String code){
//		01 中共党员；02 中共预备党员；03 共青团员；04 民革党员；05 民盟盟员；06 民建会员；
//		07 民进会员；08 农工党党员；09 致公党党员；10 九三学社社员；11 台盟盟员；12 无党派人士；13 群众
		switch(code){
		case "01":return "中共党员";
		case "02":return "中共预备党员";
		case "03":return "共青团员";
		case "04":return "民革党员";
		case "05":return "民盟盟员";
		case "06":return "民建会员";
		case "07":return "民进会员";
		case "08":return "农工党党员";
		case "09":return "致公党党员";
		case "10":return "九三学社社员";
		case "11":return "台盟盟员";
		case "12":return "无党派人士";
		case "13":return "群众";
		default:return "未知";
		}
	}
}
