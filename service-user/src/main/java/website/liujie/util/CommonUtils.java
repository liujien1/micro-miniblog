package website.liujie.util;

import website.liujie.entity.User;

/**
 * @author 刘杰
 * @date 2019-1-29
 */
public class CommonUtils {

    /**
     * 获取token
     * @param user
     * @return
     */
    public static String getToken(User user){
        String str=user.getId()+"_"+System.currentTimeMillis();
        String aesStr= MethodUtil.getDES(str,0);
        return aesStr;
    }
}
