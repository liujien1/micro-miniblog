package website.liujie.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;

/**
 * @Description	: MD5工具类
 * @Copyright	: Excenon. ALL Rights Reserved
 * @Company		: 深圳市华磊移动设备科技有限公司 
 * @author		: Cosmo
 * @version		: 1.0
 * @Date		: 2015年11月27日 下午8:02:56
 */
public final class MD5Coder{
	
	private static final char[] HEX_DIGITS = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };

	private MD5Coder() {
	}

	/**
	 * 对文件进行加密
	 */
	public static String getMD5File(String filename) {
		InputStream fis = null;
		byte[] buffer = new byte[1024];
		int numRead = 0;
		MessageDigest md5;
		try {
			// 不存在则提前返回
			File file = new File(filename);
			if (!file.exists()) {
				return null;
			}
			fis = new FileInputStream(file);
			md5 = MessageDigest.getInstance("MD5");
			while ((numRead = fis.read(buffer)) > 0) {
				md5.update(buffer, 0, numRead);
			}
			return toHexString(md5.digest());
		} catch (Exception e) {
			return null;
		} finally {
			if (fis != null) {
				try {
					fis.close();
				} catch (IOException e) {
				}
			}
		}
	}

	/**
	 * toHexString
	 * @param b b 
	 * @return String
	 */
	public static String toHexString(byte[] b) {
		StringBuilder sb = new StringBuilder(b.length * 2);
		for (int i = 0; i < b.length; i++) {
			sb.append(HEX_DIGITS[(b[i] & 0xf0) >>> 4]);
			sb.append(HEX_DIGITS[b[i] & 0x0f]);
		}
		return sb.toString();
	}

	/**
	 * 对字符串进行签名的操作 用MD5对字符串进行签名
	 * @param s s
	 * @return String
	 */
	public static String getMD5String(String s) {
		try {
			byte[] strTemp = s.getBytes();
			MessageDigest mdTemp = MessageDigest.getInstance("MD5");
			mdTemp.update(strTemp);

			byte[] md = mdTemp.digest();
			int j = md.length;

			char[] str = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte byte0 = md[i];
				str[k++] = HEX_DIGITS[byte0 >>> 4 & 0xf];
				str[k++] = HEX_DIGITS[byte0 & 0xf];
			}
			return new String(str);
		} catch (Exception e) {
			return s;
		}
	}
	
	public static void main(String[] args) {
//		double arbitrationAmount = 0.5;
//		double arbitrationPrice = 0.5;
//		boolean s = Math.floor(arbitrationAmount * arbitrationPrice) != 0;
//		String fileNameMd5Str = getMD5File("D://glshop_test_app-gl-release-1.4.apk");
//		System.out.println(fileNameMd5Str);
//		System.out.println(s);
//		
//		Float f1 = new Float(456.23F);
//
//		Float f2 = new Float(456.24F);
//
//		int fol = f1.compareTo(f2);
//
//		System.out.println(fol);
//		
//		String num = RandomStringUtils.random(2, false, true);
//		int i = RandomUtils.nextInt(50);
//		System.out.println(num);
//		System.out.println(i);
	}
	
}
