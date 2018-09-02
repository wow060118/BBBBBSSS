package com.yfr.Control;

import com.yfr.util.FreeMarker;

import javax.jws.WebService;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Koreyoshi on 2017/8/18.
 */
@WebServlet(name = "initcontrol" ,urlPatterns = {"/index"},initParams = {
     @WebInitParam(name = "ok",value = "article?action=quarryAll&page=1")
})
public class InitControl extends HttpServlet {
    private Map<String ,String > map=new HashMap();
    @Override
    public void init(ServletConfig config) throws ServletException {
        map.put("ok",config.getInitParameter("ok"));
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doGet(req,resp);
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher dispatcher=null;
        dispatcher=req.getRequestDispatcher(map.get("ok"));
        dispatcher.forward(req,resp);
    }
}
