package website.liujie.common;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by liujie on 2016/10/7.
 */
public class InitListener implements ServletContextListener{
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        //初始化数据

        InputStream is=this.getClass().getClassLoader().getResourceAsStream(SystemConstant.SYS_PROPERTIES_NAME);
        Properties p=new Properties();
        try {
            p.load(is);
        } catch (IOException e) {
            e.printStackTrace();
        }

        sce.getServletContext().setAttribute("SYS_PROPERTIES",p);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }
}
