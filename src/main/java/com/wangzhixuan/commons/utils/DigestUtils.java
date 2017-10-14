package com.wangzhixuan.commons.utils;

import java.util.Random;

import org.apache.shiro.crypto.hash.SimpleHash;

/**
 * 加密相关工具类直接使用Spring util封装，减少jar依赖
 * @author L.cm
 */
public class DigestUtils extends org.springframework.util.DigestUtils {

    /**
     * Calculates the MD5 digest and returns the value as a 32 character hex string.
     *
     * @param data Data to digest
     * @return MD5 digest as a hex string
     */
    public static String md5Hex(final String data) {
        return DigestUtils.md5DigestAsHex(data.getBytes(Charsets.UTF_8));
    }
    
    /**
     * Return a hexadecimal string representation of the MD5 digest of the given bytes.
     * @param bytes the bytes to calculate the digest over
     * @return a hexadecimal digest string
     */
    public static String md5Hex(final byte[] bytes) {
        return DigestUtils.md5DigestAsHex(bytes);
    }
    
    /**
     * 使用shiro的hash方式
     * @param algorithmName 算法
     * @param source 源对象
     * @param salt 加密盐
     * @param hashIterations hash次数
     * @return 加密后的字符
     */
    public static String hashByShiro(String algorithmName, Object source, Object salt, int hashIterations) {
        return new SimpleHash(algorithmName, source, salt, hashIterations).toHex();
    }
    /**
	 * 生成 length 位0-F 随机字符串
	 * @param length 表示生成字符串的长度  
	 * @return Str
	 */
	public static String getRandomString(int length) { 
	    String base = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";     
	    Random random = new Random();     
	    StringBuffer Str = new StringBuffer();     
	    for (int i = 0; i < length; i++) {     
	        int number = random.nextInt(base.length());     
	        Str.append(base.charAt(number));     
	    }     
	    return Str.toString();     
	 }     
	/**
	 * 计算两坐标点的距离
	 */
	// 地球平均半径
	private static final double EARTH_RADIUS = 6378137;

	// 把经纬度转为度（°）
	private static double rad(double d) {
		return d * Math.PI / 180.0;
	}

	/**
	 * 根据两点间经纬度坐标（double值），计算两点间距离，单位为米
	 * 
	 * @param lng1
	 * @param lat1
	 * @param lng2
	 * @param lat2
	 * @return
	 */
	public static double GetDistance(double lng1, double lat1, double lng2, double lat2) {
		if (validatelat(lat1, lat2) && validatelng(lng1, lng2)) {
			double radLat1 = rad(lat1);
			double radLat2 = rad(lat2);
			double a = radLat1 - radLat2;
			double b = rad(lng1) - rad(lng2);
			double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2)
					+ Math.cos(radLat1) * Math.cos(radLat2) * Math.pow(Math.sin(b / 2), 2)));
			s = s * EARTH_RADIUS;
			s = Math.round(s * 10000) / 10000;
			return s;
		}else {
			return -1;
		}
	}

	public static boolean validatelng(double lng1, double lng2) {
		if (lng1 > 180 || lng1 < -180) {
			return false;
		}
		if (lng2 > 180 || lng2 < -180) {
			return false;
		}
		return true;
	}

	public static boolean validatelat(double lat1, double lat2) {
		if (lat1 > 90 || lat1 < -90) {
			return false;
		}
		if (lat2 > 90 || lat2 < -90) {
			return false;
		}
		return true;
	}
    
}
