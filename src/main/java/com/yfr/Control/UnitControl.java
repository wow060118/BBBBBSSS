package com.yfr.Control;

import com.yfr.services.ArticleImpl;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by Koreyoshi on 2017/8/22.
 */
@WebServlet(name = "UnitControl",urlPatterns = "/kindupload")
public class UnitControl extends HttpServlet{
    @Autowired
    private ArticleImpl article;
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String s=article.uppic(req);
        PrintWriter out=null;
        out=resp.getWriter();
        out.print(s);
        out.close();
    }
}
