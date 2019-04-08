package website.liujie.util;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.util.ObjectUtils;

import java.io.UnsupportedEncodingException;
import java.math.RoundingMode;
import java.security.MessageDigest;
import java.sql.Blob;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.*;


/**
 * @Description :常用工具类
 * @Copyright : Excenon. ALL Rights Reserved
 * @Company : jie
 * @Author : liujie
 * @Version : 1.0
 * @Date : 2015年11月27日 下午7:58:05
 */
public class ObjectUtil extends ObjectUtils {

    /**
     * 判断对象不为null或为空
     */
    public static boolean isNotEmpty(Object obj) {
        return !isNEmpty(obj);
    }

    /**
     * 判断对象为null或为空
     */
    public static boolean isNEmpty(Object obj) {
        return obj == null ? true : isEmpty(obj);
    }

    /**
     * 判断字符串为空，集合为空，数组为空(后续可以拓展hashSet,hashMap ...)
     */
    public static boolean isEmpty(Object obj) {
        boolean isEmpty = true;
        if (obj != null) {
            if (obj instanceof String || obj instanceof StringBuilder || obj instanceof StringBuffer) {
                // 字符串
                String tmp = obj.toString();
                isEmpty = StringUtils.isEmpty(tmp);

            } else if (obj instanceof Collection<?>) {
                // 集合
                Collection<?> collections = (Collection<?>) obj;
                isEmpty = CollectionUtils.isEmpty(collections);

            } else if (obj instanceof Map<?, ?>) {
                // Map
                Map<?, ?> map = (Map<?, ?>) obj;
                isEmpty = map.size() == 0;

            } else if (obj instanceof Object[]) {
                // 数组
                Object[] objarray = (Object[]) obj;
                isEmpty = objarray.length == 0;

            } else if (obj instanceof Float) {
                Float f = (Float) obj;
                isEmpty = f == 0f;
            } else {
                isEmpty = obj == null;
            }
        }
        return isEmpty;
    }

    /**
     * 字符串数组去重
     *
     * @param s
     * @return
     */
    public static String[] StringArrayrepeat(String[] s) {
        List<String> list = Arrays.asList(s);
        Set<String> set = new HashSet<String>(list);
        String[] strs = (String[]) set.toArray(new String[0]);
        return strs;
    }

    /**
     * 百分比换算
     *
     * @param num
     * @param total
     * @param scale
     * @return
     */
    public static String accuracy(double num, double total, int scale) {
        if (num == 0 || total == 0) {
            return "0%";
        }
        DecimalFormat df = (DecimalFormat) NumberFormat.getInstance();
        //可以设置精确几位小数  
        df.setMaximumFractionDigits(scale);
        //模式 例如四舍五入  
        df.setRoundingMode(RoundingMode.HALF_UP);
        double accuracy_num = num / total * 100;
        return df.format(accuracy_num) + "%";
    }

    /**
     * map是否有空值（用来判断必填字段是否完整）
     *
     * @param m
     * @return
     */
    public static boolean isContainEmpty(Map<String, Object> m) {
        boolean flag = false;
        for (Map.Entry<String, Object> s : m.entrySet()) {
            if (isEmpty(s.getValue())) {
                flag = true;
                break;
            }
        }
        return flag;
    }

    /**
     * list是否有空值（用来判断必填字段是否完整）
     *
     * @param list
     * @return
     */
    public static boolean isContainEmpty(List<Object> list) {
        boolean flag = false;
        for (Object o : list) {
            if (isEmpty(o)) {
                flag = true;
                break;
            }
        }
        return flag;
    }

    /**
     * String 的工具类
     */
    public static String getMd5(String s) {
        char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D',
                'E', 'F'};
        try {
            byte[] strTemp = s.getBytes();
            MessageDigest mdTemp = MessageDigest.getInstance("MD5");
            mdTemp.update(strTemp);
            byte[] md = mdTemp.digest();
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 获取全局唯一编码
     *
     * @return
     */
    public static String GUID() {
        // 标识本服务区别其他服务的种子数
        String SERVER_UNIQUE = Long.toString(System.currentTimeMillis());
        String zs = getRand(50) + SERVER_UNIQUE + Thread.currentThread().getName();
        zs = zs + Long.toString(System.currentTimeMillis());
        return getMd5(zs);
    }

    /**
     * 得到一个n位的随机数 第一位不能为0
     *
     * @param n 位数
     * @return
     */
    public static String getRand(int n) {
        Random rnd = new Random();
        String pass = "0";
        int x = rnd.nextInt(10);
        /** 过滤第一位为0 */
        while (x == 0) {
            x = rnd.nextInt(10);
        }
        pass = String.valueOf(x);
        for (int i = 1; i < n; i++) {
            pass = pass + String.valueOf(rnd.nextInt(10));
        }
        return pass;
    }

    /**
     * 获取内容字符串
     *
     * @param o
     * @return
     * @throws SQLException
     * @throws UnsupportedEncodingException
     */
    public static String BlobToString(Object o) throws SQLException, UnsupportedEncodingException {
        String str = null;
        byte[] inbyte = null;
        if (o instanceof Blob) {
            Blob blob = (Blob) o;
            if (blob != null) {
                inbyte = blob.getBytes(1, (int) blob.length());
            }
            str = new String(inbyte, "utf-8");
        }
        return str;
    }
}
