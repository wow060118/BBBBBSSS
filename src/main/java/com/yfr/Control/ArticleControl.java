package com.yfr.Control;

import com.alibaba.fastjson.JSON;
import com.yfr.po.Article;
import com.yfr.po.Bbsuser;
import com.yfr.po.PageBean;
import com.yfr.services.ArticleImpl;
import com.yfr.util.FreeMarker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Created by Koreyoshi on 2017/8/21.
 */
@WebServlet(name = "ArticleControl",urlPatterns = "/article",
initParams = {@WebInitParam(name="success",value = "/show.ftl")})
public class ArticleControl extends HttpServlet{
    private Map<String ,String > map=new HashMap();
    @Autowired
    private ArticleImpl Aservices;
    @Override
    public void init(ServletConfig config) throws ServletException {
        map.put("success",config.getInitParameter("success"));
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map map1=new HashMap();
        String action=req.getParameter("action");
        if(action.equals("quarryAll")){
            PageBean pb=page(req,resp);
            map1.put("PB",pb);
        }
        if(action.equals("del")){
            dela(req,resp);
        }
        if(action.equals("add")){
            add(req,resp);
        }
        if(action.equals("queryid")){
            queryid(req,resp);
        }
        if(action.equals("delc")){
            delc(req,resp);
        }
        if(action.equals("addc")){
            add(req,resp);
        }
        map1.put("user",req.getSession().getAttribute("user"));
        FreeMarker.forward(map.get("success").toString(),resp,map1);
    }

    private void delc(HttpServletRequest req, HttpServletResponse resp) {
        String id=req.getParameter("rid");
        Aservices.delc(Integer.parseInt(id));
        queryid(req,resp);

    }

    private void queryid(HttpServletRequest req, HttpServletResponse resp) {
        String id=req.getParameter("id");
        Map<String,Object> map= Aservices.queryid(Integer.parseInt(id));
        String json= JSON.toJSONString(map,true);
        resp.setContentType("text/html");
        resp.setCharacterEncoding("utf-8");
        try {
            PrintWriter out=resp.getWriter();
            out.println(json);
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void add(HttpServletRequest req, HttpServletResponse resp) {
        String title =req.getParameter("title");
        String rootid=req.getParameter("rootid");
        String content=req.getParameter("content");
        String userid=req.getParameter("userid");
        Article a=new Article();
        a.setTitle(title);
        a.setRootid(Integer.parseInt(rootid));
        a.setContent(content);
        a.setUser(new Bbsuser(Integer.parseInt(userid)));
        a.setDatetime(new Date(System.currentTimeMillis()));
        if(Aservices.save(a)!=null){
            if(a.getRootid().equals(0)) {
                RequestDispatcher dispatcher = null;
                dispatcher = req.getRequestDispatcher("index");
                try {
                    dispatcher.forward(req, resp);
                } catch (ServletException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }else{
                this.queryid(req,resp);
            }

        }

    }

    private void dela(HttpServletRequest req, HttpServletResponse resp) {
        String id=req.getParameter("id");
        Aservices.dela(Integer.parseInt(id));
        RequestDispatcher dispatcher=null;
        dispatcher=req.getRequestDispatcher("index");
        try {
            dispatcher.forward(req,resp);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private PageBean page(HttpServletRequest req, HttpServletResponse resp) {
        String page=req.getParameter("page");
        Bbsuser user=(Bbsuser) req.getSession().getAttribute("user");
        int pageNum=5;
        if(user!=null){
            pageNum=user.getPagenum();
        }
        Sort sort=new Sort(Sort.Direction.DESC,"id");
        Pageable pageable=new PageRequest(Integer.parseInt(page)-1,pageNum,sort);
        Page<Article> pa=Aservices.findAll(pageable,0);

        PageBean pb=new PageBean();
        pb.setRowsPerPage(pageNum);
        pb.setCurPage(Integer.parseInt(page));
        pb.setMaxRowCount(pa.getTotalElements());
        pb.setData(pa.getContent());
        pb.setMaxPage(pa.getTotalPages());


        return pb;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doGet(req,resp);
    }
}
