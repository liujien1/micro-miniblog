package website.liujie.util;


import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.io.IOException;

/**
 * @author 刘杰
 * @date 2019-1-28
 */
public class AuthUtil {
    public static final String APPID="wx4ac592363064d43b";
    public static final String APPSECRET="d4624c36b6795d1d99dcf0547af5443d";
    public static JSONObject doGetJson(String url) throws IOException {
        JSONObject jsonObject=null;
        DefaultHttpClient defaultHttpClient=new DefaultHttpClient();
        HttpGet httpGet=new HttpGet(url);
        HttpResponse httpResponse = defaultHttpClient.execute(httpGet);
        HttpEntity httpEntity=httpResponse.getEntity();
        if(httpEntity!=null){
            String result= EntityUtils.toString(httpEntity,"UTF-8");
            jsonObject=new JSONObject(result);
        }
        httpGet.releaseConnection();
        return jsonObject;
    }
}
