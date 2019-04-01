package website.liujie.util;

import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.util.DefaultPropertiesPersister;
import org.springframework.util.PropertiesPersister;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * 配置文件读取工具类
 *
 * @author liujie
 */
public final class PropertyUtils {

    public static final String DEFAULT_PROPERTIES = "config.properties";
    private Properties props;
    private String allfilePath;


    public PropertyUtils() {
        loadLocalProp();

    }

    public PropertyUtils(String filePath) {
        String rootPath = System.getProperty("user.dir");
        this.allfilePath = rootPath + File.separator + "src\\main\\resource" + File.separator + filePath;
        props = new Properties();
        try {
            props.load(new FileInputStream(allfilePath));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public PropertyUtils(int type) {
        props = new Properties();
        if (type == 1) {
            String propPath = new PropertyUtils().getKeyValue("remoteProUrl");
            try {
                Map<String, String> params = new HashMap<>();
                InputStream inputStream = httpRequestToStream(propPath, "GET", params);
                props.load(new InputStreamReader(inputStream, "utf-8"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            loadLocalProp();
        }


    }

    public static String get(Object key, String file) {
        Properties props = new Properties();
        PropertiesPersister propertiesPersister = new DefaultPropertiesPersister();
        ResourceLoader resourceLoader = new DefaultResourceLoader();
        Resource resource = resourceLoader.getResource(file);
        InputStream is;
        try {
            is = resource.getInputStream();
            propertiesPersister.load(props, new InputStreamReader(is, "UTF-8"));
        } catch (IOException e) {

        }
        Object result = props.get(key);
        return result == null ? "" : result.toString();
    }

    public static String get(Object key) {
        return get(key, DEFAULT_PROPERTIES);
    }


    private void loadLocalProp() {
        props = new Properties();
        try {
            props.load(this.getClass().getResourceAsStream(DEFAULT_PROPERTIES));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 读取属性文件中相应键的值
     *
     * @param key 主键
     * @return String
     */
    public String getKeyValue(String key) {
        return props.getProperty(key);
    }

    /**
     * 根据主键key读取主键的值value
     *
     * @param key 键名
     */
    public String readValue(String key) {
        Properties props = new Properties();
        try {
            InputStream in = new BufferedInputStream(new FileInputStream(allfilePath));
            props.load(in);
            String value = props.getProperty(key);
            System.out.println(key + "键的值是：" + value);
            return value;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 更新（或插入）一对properties信息(主键及其键值)
     * 如果该主键已经存在，更新该主键的值；
     * 如果该主键不存在，则插件一对键值。
     *
     * @param keyname  键名
     * @param keyvalue 键值
     */
    public void writeProperties(String keyname, String keyvalue) {
        try {
            // 调用 Hashtable 的方法 put，使用 getProperty 方法提供并行性。
            // 强制要求为属性的键和值使用字符串。返回值是 Hashtable 调用 put 的结果。
            OutputStream fos = new FileOutputStream(allfilePath);
            props.setProperty(keyname, keyvalue);
            // 以适合使用 load 方法加载到 Properties 表中的格式，
            // 将此 Properties 表中的属性列表（键和元素对）写入输出流
            props.store(fos, "Update '" + keyname + "' value");
        } catch (IOException e) {
            System.err.println("属性文件更新错误");
        }
    }

    /**
     * 更新properties文件的键值对
     * 如果该主键已经存在，更新该主键的值；
     * 如果该主键不存在，则插件一对键值。
     *
     * @param keyname  键名
     * @param keyvalue 键值
     */
    public void updateProperties(String keyname, String keyvalue) {
        try {
            props.load(new FileInputStream(allfilePath));
            // 调用 Hashtable 的方法 put，使用 getProperty 方法提供并行性。
            // 强制要求为属性的键和值使用字符串。返回值是 Hashtable 调用 put 的结果。
            OutputStream fos = new FileOutputStream(allfilePath);
            props.setProperty(keyname, keyvalue);
            // 以适合使用 load 方法加载到 Properties 表中的格式，
            // 将此 Properties 表中的属性列表（键和元素对）写入输出流
            props.store(fos, "Update '" + keyname + "' value");
        } catch (IOException e) {
            System.err.println("属性文件更新错误");
        }
    }

    /**
     * <pre>
     * 方法体说明：向远程接口发起请求，返回字节流类型结果
     * 作者：高会于
     * 日期：2015年3月17日 上午11:20:25
     * @param url 接口地址
     * @param requestMethod 请求方式
     * @param params 传递参数     重点：参数值需要用Base64进行转码
     * @return InputStream 返回结果
     * </pre>
     */
    public static InputStream httpRequestToStream(String url, String requestMethod, Map<String, String> params) {

        InputStream is = null;
        try {
            String parameters = "";
            boolean hasParams = false;
            //将参数集合拼接成特定格式，如name=zhangsan&age=24
            for (String key : params.keySet()) {
                String value = URLEncoder.encode(params.get(key), "UTF-8");
                parameters += key + "=" + value + "&";
                hasParams = true;
            }
            if (hasParams) {
                parameters = parameters.substring(0, parameters.length() - 1);
            }

            //请求方式是否为get
            boolean isGet = "get".equalsIgnoreCase(requestMethod);
            //请求方式是否为post
            boolean isPost = "post".equalsIgnoreCase(requestMethod);
            if (isGet) {
                url += "?" + parameters;
            }

            URL u = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) u.openConnection();

            //请求的参数类型(使用restlet框架时，为了兼容框架，必须设置Content-Type为“”空)
            conn.setRequestProperty("Content-Type", "application/octet-stream");
            conn.setRequestProperty("Accept-Charset", "utf-8");
            conn.setRequestProperty("contentType", "utf-8");
            //conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            //设置连接超时时间
            conn.setConnectTimeout(50000);
            //设置读取返回内容超时时间
            conn.setReadTimeout(50000);
            //设置向HttpURLConnection对象中输出，因为post方式将请求参数放在http正文内，因此需要设置为ture，默认false
            if (isPost) {
                conn.setDoOutput(true);
            }
            //设置从HttpURLConnection对象读入，默认为true
            conn.setDoInput(true);
            //设置是否使用缓存，post方式不能使用缓存
            if (isPost) {
                conn.setUseCaches(false);
            }
            //设置请求方式，默认为GET
            conn.setRequestMethod(requestMethod);

            //post方式需要将传递的参数输出到conn对象中
            if (isPost) {
                DataOutputStream dos = new DataOutputStream(conn.getOutputStream());
                dos.writeBytes(parameters);
                dos.flush();
                dos.close();
            }

            //从HttpURLConnection对象中读取响应的消息
            //执行该语句时才正式发起请求
            is = conn.getInputStream();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return is;
    }

    //测试代码
    public static void main(String[] args) {

        PropertyUtils property = new PropertyUtils("config.properties");
        property.readValue("source_path");
        System.out.println("操作完成");
    }
}
