package com.yfr.util;

import freemarker.template.Configuration;
import freemarker.template.Template;


import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.util.Map;

/**
 * Created by Koreyoshi on 2017/8/18.
 */
public class FreeMarker {
   private static Configuration config;
   public static void forward(String fname, HttpServletResponse resp,Map map){
       Template template=null;

       config=new Configuration(Configuration.VERSION_2_3_26);
       PrintWriter out=null;
       try {
           config.setDefaultEncoding("utf-8");
           config.setDirectoryForTemplateLoading(new File(FreeMarker.class.getResource("/").getPath()+ File.separator+"templates"));
           template=config.getTemplate(fname);
           resp.setContentType("text/html");
           resp.setCharacterEncoding("utf-8");
           out=resp.getWriter();
           template.process(map,out);

       } catch (Exception e) {
           e.printStackTrace();
       }
       out.flush();


   }

}
