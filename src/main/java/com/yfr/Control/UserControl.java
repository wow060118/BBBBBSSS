package com.yfr.Control;

import com.yfr.po.Bbsuser;
import com.yfr.services.UserImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by Koreyoshi on 2017/8/20.
 */
@WebServlet(name = "UserControl",urlPatterns = {"/user"})
public class UserControl extends HttpServlet{
    @Autowired
    private UserImpl dao;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        CommonsMultipartResolver commonsMultipartResolver=new CommonsMultipartResolver(req.getSession().getServletContext());
        if(commonsMultipartResolver.isMultipart(req)){
            reg(req,resp,commonsMultipartResolver);

        }else {
            String action = req.getParameter("action");
            if (action.equals("login")) {
                login(req, resp);
            } else if (action.equals("logout")) {
                logout(req, resp);
            } else if (action.equals("pic")) {
                pic(req, resp);
            }
            else if(action.equals("judge")){
                judge(req,resp);
            }else if(action.equals("setpage")){
                setpage(req,resp);
            }
        }
    }

    private void setpage(HttpServletRequest req, HttpServletResponse resp) {
        String pagenum=req.getParameter("page");
        Bbsuser bbsuser=(Bbsuser) req.getSession().getAttribute("user");
        bbsuser.setPagenum(Integer.parseInt(pagenum));
        dao.setPagenum(bbsuser.getPagenum(),bbsuser.getUserid());
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

    private void judge(HttpServletRequest req, HttpServletResponse resp) {
        String reusername=req.getParameter("reusername");
        Bbsuser bbsuser=new Bbsuser();
        String flag=((bbsuser=dao.judge(reusername))==null)?"true":"false";
        PrintWriter out=null;
        resp.setCharacterEncoding("utf-8");
        resp.setContentType("text/html");
        try {
            out=resp.getWriter();
            out.println(flag);
        } catch (IOException e) {
            e.printStackTrace();
        }
        out.flush();

    }


    private void reg(HttpServletRequest req, HttpServletResponse resp, CommonsMultipartResolver commonsMultipartResolver) {
            Bbsuser bbsuser=dao.uppic(req,commonsMultipartResolver);
            dao.save(bbsuser);

            RequestDispatcher request=null;
            request=req.getRequestDispatcher("index");
        try {
            request.forward(req,resp);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void pic(HttpServletRequest req, HttpServletResponse resp) {
        String id=req.getParameter("id");
        Bbsuser bbsuser=dao.findOne(Integer.parseInt(id));
        byte buffer[]=bbsuser.getPic();
        try {
            ServletOutputStream sos=resp.getOutputStream();
            sos.write(buffer);
            sos.flush();
            sos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void login(HttpServletRequest req, HttpServletResponse resp) {
        String username=req.getParameter("username");
        String password=req.getParameter("password");
        Bbsuser bbsuser=new Bbsuser(username,password);
            String flag = ((bbsuser = dao.login(bbsuser)) == null) ? "false" : "true";

            if (flag.equals("true")) {
                if(bbsuser.getFlag()==0) {
                    req.getSession().setAttribute("user", bbsuser);
                    System.out.println(req.getSession().getAttribute(username));
                    String sun = req.getParameter("sun");
                    if (sun != null) {
                        Cookie cookieu = new Cookie("useru", bbsuser.getUsername());
                        cookieu.setMaxAge(3600 * 24 * 7);
                        resp.addCookie(cookieu);
                        Cookie cookiep = new Cookie("useru", bbsuser.getUsername());
                        cookiep.setMaxAge(3600 * 24 * 7);
                        resp.addCookie(cookiep);
                    }
                    bbsuser.setFlag(1);
                    dao.setflag(bbsuser);
                }else{
                    flag="false";
                }
            }
        resp.setCharacterEncoding("utf-8");
        resp.setContentType("text/html");
        PrintWriter out=null;
        try {
            out=resp.getWriter();
            out.println(flag);
        } catch (IOException e) {
            e.printStackTrace();
        }
        out.flush();
        out.close();

    }
    private void logout(HttpServletRequest req, HttpServletResponse resp) {
        RequestDispatcher request=null;
        Bbsuser bbsuser=(Bbsuser) req.getSession().getAttribute("user");
        bbsuser.setFlag(0);
        dao.setflag(bbsuser);
        req.getSession().removeAttribute("user");
        request=req.getRequestDispatcher("index");
        try {
            request.forward(req,resp);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
