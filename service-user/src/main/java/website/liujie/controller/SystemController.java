package website.liujie.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import website.liujie.util.SessionUtil;
import website.liujie.util.VerifyCodeUtil;
import website.liujie.util.jedis.RedisUtil;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * 系统类
 * Created by liujie on 2017-3-22.
 */
@Controller
@RequestMapping
public class SystemController{

    @Resource
    public RedisUtil redisUtil;

    /**
     * 生成验证码
     * @param request
     * @param response
     * @throws IOException
     */
//    @RequestMapping("/public/verifyCode")
//    public void verifyCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
//        //设置浏览器不缓存本页
//        // Set to expire far in the past.
//        response.setDateHeader("Expires", 0);
//        // Set standard HTTP/1.1 no-cache headers.
//        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
//        // Set IE extended HTTP/1.1 no-cache headers (use addHeader).
//        response.addHeader("Cache-Control", "post-check=0, pre-check=0");
//        // Set standard HTTP/1.0 no-cache header.
//        response.setHeader("Pragma", "no-cache");
//
//        //生成验证码，写入用户session
//        String verifyCode = VerifyCodeUtil.generateTextCode(VerifyCodeUtil.TYPE_NUM_UPPER, 4, "0oOilJI1");
//        SessionUtil.setAttr(request, VerifyCodeUtil.VERIFY_TYPE_COMMENT, verifyCode);
//        System.out.println("verifyCode=" + verifyCode);
//        response.setHeader("uuid", UUID.randomUUID().toString());
//
//        //输出验证码给客户端
//        //response.setContentType("image/jpeg");
//        /*
//            textCode 文本验证码
//            width 图片宽度
//            height 图片高度
//            interLine 图片中干扰线的条数
//            randomLocation 每个字符的高低位置是否随机
//            backColor 图片颜色，若为null，则采用随机颜色
//            foreColor 字体颜色，若为null，则采用随机颜色
//            lineColor 干扰线颜色，若为null，则采用随机颜色
//        */
//        BufferedImage bim = VerifyCodeUtil.generateImageCode(verifyCode, 70, 22, 15, true, Color.WHITE, Color.BLACK, null);
//        ServletOutputStream out = response.getOutputStream();
//        ImageIO.write(bim, "JPEG", out);
//        try {
//            out.flush();
//        } finally {
//            out.close();
//        }
//
//
//    }

    @RequestMapping("/public/verifyCode")
    @ResponseBody
    public Object verifyCode(HttpServletRequest request, HttpServletResponse response) throws IOException {


        //生成验证码，写入用户session
        String verifyCode = VerifyCodeUtil.generateTextCode(VerifyCodeUtil.TYPE_NUM_UPPER, 4, null);
        SessionUtil.setAttr(request, VerifyCodeUtil.VERIFY_TYPE_COMMENT, verifyCode);
        System.out.println("verifyCode=" + verifyCode);

        BufferedImage bim = VerifyCodeUtil.generateImageCode(verifyCode, 70, 22, 15, true, Color.WHITE, Color.BLACK, null);
        ByteArrayOutputStream data=new ByteArrayOutputStream();
        ImageIO.write(bim, "JPEG", data);

        String result=new String(Base64.getEncoder().encode(data.toByteArray()));

        String uuid=UUID.randomUUID().toString();
        //request.getSession().setAttribute(uuid,verifyCode);
        redisUtil.setEx(uuid,30,verifyCode);

        Map<String,Object> resultMap=new HashMap<>();
        resultMap.put("imageSrc","data:image/png;base64,"+result);
        resultMap.put("uuid",uuid);
        return resultMap;
    }
}
