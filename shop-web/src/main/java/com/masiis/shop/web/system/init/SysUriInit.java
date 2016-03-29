package com.masiis.shop.web.system.init;

import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.ServletContext;
import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by lzh on 2016/3/21.
 */
@Component
public class SysUriInit implements ApplicationListener<ContextRefreshedEvent> {
    private List<String> uriLists = null;

    public List<String> getUriLists(){
        return uriLists;
    }

    public void onApplicationEvent(ContextRefreshedEvent event) {
        if(event.getApplicationContext().getParent() == null){
            ApplicationContext appContext = event.getApplicationContext();
            //获取所有的RequestMapping
            Map<String, Object> allRequestMappings = BeanFactoryUtils.beansOfTypeIncludingAncestors(appContext,
                    Object.class, true, false);
            List<String> pathList = new LinkedList<String>();
            for(Object obj:allRequestMappings.values()){
                Controller c = obj.getClass().getAnnotation(Controller.class);
                if(c != null){
                    RequestMapping rm = obj.getClass().getAnnotation(RequestMapping.class);
                    if(rm == null) { continue; }
                    for(String path:rm.value()){
                        // 获取类注解路径
                        String start = generatorPath(path);
                        for(Method method:obj.getClass().getMethods()) {
                            RequestMapping mrm = method.getAnnotation(RequestMapping.class);
                            if(mrm != null){
                                for(String innerPath:mrm.value()) {
                                    // 获取方法注解路径,并组织存起来
                                    String endPath = start + generatorPath(innerPath);
                                    int indexs = endPath.indexOf("{");
                                    int indexe = endPath.indexOf("}");
                                    if(indexs >= 0 && indexe>= 0 && indexe > indexs){
                                        endPath = endPath.replaceAll("\\{.*\\}", "(.*)");
                                    }
                                    pathList.add(endPath);
                                }
                            }
                        }
                    }
                }
            }
            // 增加域名访问
            pathList.add("/");
            pathList.add("/index");
            // 保存路径集合
            uriLists = pathList;
        }
    }

    private String generatorPath(String path){
        return path.startsWith("/")?path:"/" + path;
    }
}
