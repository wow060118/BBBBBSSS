package com.yfr.services;

import com.yfr.dao.UserDao;
import com.yfr.po.Bbsuser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by Koreyoshi on 2017/8/20.
 */
@Service
@Transactional
public class UserImpl {
    Map maps=new HashMap();
    @Autowired
    private UserDao dao;
    public UserImpl(){
        maps.put("image/jpeg", ".jpg");
        maps.put("image/gif", ".gif");
        maps.put("image/x-ms-bmp", ".bmp");
        maps.put("image/png", ".png");
    }
    public Bbsuser login(Bbsuser bbsuser){
        return dao.login(bbsuser.getUsername(),bbsuser.getPassword());
    }

    public Bbsuser uppic(HttpServletRequest req, CommonsMultipartResolver commonsMultipartResolver) {
        Bbsuser bbsuser=new Bbsuser();
        commonsMultipartResolver.setDefaultEncoding("utf-8");
        commonsMultipartResolver.setResolveLazily(true);
        commonsMultipartResolver.setMaxInMemorySize(4096*1024);
        commonsMultipartResolver.setMaxUploadSize(2*1024*1024);
        commonsMultipartResolver.setMaxUploadSizePerFile(1024*1024);
        if(commonsMultipartResolver.isMultipart(req)) {
            MultipartHttpServletRequest request = commonsMultipartResolver.resolveMultipart(req);
            MultipartFile file = request.getFile("file0");
            String type = file.getContentType();
            if (maps.containsKey(type)) {
                File tfile = new File("pic" + File.separator + req.getSession().getId() + maps.get(type));
                String username = request.getParameter("reusername");
                String password = request.getParameter("repassword");
                bbsuser.setUsername(username);
                bbsuser.setPassword(password);
                bbsuser.setPicPath(tfile.getPath());
                bbsuser.setPagenum(5);

                try {
                    file.transferTo(tfile);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                try {
                    FileInputStream fis = new FileInputStream(tfile);
                    byte buffer[] = new byte[fis.available()];
                    fis.read(buffer);
                    bbsuser.setPic(buffer);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
           return  bbsuser;
    }
    public Bbsuser findOne(int i) {
        return dao.findOne(i);
    }
    public void save(Bbsuser bbsuser){
         dao.save(bbsuser);
    }
    public Bbsuser judge(String reusername){
        return dao.judge(reusername);
    }
    public int setPagenum(int pagenum,int userid){
        return dao.setPagenum(pagenum,userid);
    }
    public int setflag(Bbsuser bbsuser){
        return dao.setflag(bbsuser.getFlag(),bbsuser.getUserid());
    }
}
