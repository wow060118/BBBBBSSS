package com.yfr.services;


import com.alibaba.fastjson.JSONObject;
import com.yfr.dao.ArticleDao;
import com.yfr.dao.QueryIdDao;
import com.yfr.po.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Koreyoshi on 2017/8/21.
 */
@Service
@Transactional
public class ArticleImpl {
    @Autowired
    private ArticleDao article;

    @Autowired
    private QueryIdDao query;

    private Map maps=new HashMap();

    public ArticleImpl(){
        maps.put("image/jpeg", ".jpg");
        maps.put("image/gif", ".gif");
        maps.put("image/x-ms-bmp", ".bmp");
        maps.put("image/png", ".png");

    }
    public Page<Article> findAll(Pageable pb, Integer rid){
        return article.findAll(pb,rid);
    }
    public int dela(Integer id){
        return article.dela(id);
    }

    public String uppic(HttpServletRequest req) {
        CommonsMultipartResolver commonsMultipartResolver=new CommonsMultipartResolver(req.getSession().getServletContext());
        commonsMultipartResolver.setDefaultEncoding("utf-8");
        commonsMultipartResolver.setResolveLazily(true);
        commonsMultipartResolver.setMaxInMemorySize(4096*1024);
        commonsMultipartResolver.setMaxUploadSize(2*1024*1024);
        commonsMultipartResolver.setMaxUploadSizePerFile(1024*1024);
        if(commonsMultipartResolver.isMultipart(req)) {
            MultipartHttpServletRequest request = commonsMultipartResolver.resolveMultipart(req);
            MultipartFile file = request.getFile("imgFile");
            String type = file.getContentType();
            if (maps.containsKey(type)) {
                String s=ArticleImpl.class.getClassLoader().getResource("").toString();
                System.out.println(s);
                String dir=req.getParameter("dir");
                String id=req.getSession().getId().toString();
                String newfile=s+ "static/editor/upload/" +dir+"/"+ id+maps.get(type);
                newfile=newfile.substring(6);
                File imag=new File(newfile);
                try {
                    file.transferTo(imag);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                String tpath=req.getRequestURL().toString() ;
                tpath=tpath.substring(0,tpath.lastIndexOf("/"));
                String path=tpath+"/editor/upload/"+dir+"/";
                System.out.println(path);


                JSONObject obj = new JSONObject();
                obj.put("error", 0);//无错误
                obj.put("url", path+ id+maps.get(type));
                System.out.println("ok");
                return obj.toJSONString();

            }
        }
        return "";
    }

    public  Article save(Article a){
//        a=article.save(a);
//        if(!a.getRootid().equals(0)){
//
//        }
       return  article.save(a);
    }
    public  Map queryid(int id){
        return query.queryid(id);
    }
    public  int delc(Integer id){
        return article.delc(id);
    }
//    public Article findOne(Integer rid){
//        return article.findOne(rid);
//    }
}
