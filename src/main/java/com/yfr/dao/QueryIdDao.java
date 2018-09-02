package com.yfr.dao;

import com.yfr.po.Article;
import com.yfr.po.Bbsuser;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Koreyoshi on 2017/8/25.
 */
@Repository
public class QueryIdDao {
    @PersistenceContext
    private EntityManager entityManager;
    public Map<String,Object> queryid(int id){
        Map<String,Object> map=new HashMap<>();
        StoredProcedureQuery query=entityManager.createStoredProcedureQuery("p_2");
        query.registerStoredProcedureParameter(1,Integer.class, ParameterMode.IN);
        query.registerStoredProcedureParameter(2,String.class,ParameterMode.OUT);
        query.setParameter(1,id);
        query.execute();
        List<Object[]> listo=query.getResultList();
        List<Article> lista=new ArrayList<>();
        listo.forEach((s)->{
            Article a=new Article();
            a.setId(Integer.parseInt(s[0].toString()));
            a.setRootid(Integer.parseInt(s[1].toString()));
            a.setTitle(s[2].toString());
            a.setContent(s[3].toString());
            a.setUser(new Bbsuser(Integer.parseInt(s[4].toString())));
            try {
                java.util.Date d=new SimpleDateFormat("yyyy-mm-dd").parse(s[5].toString());
                a.setDatetime(new java.sql.Date(d.getTime()));
            } catch (Exception e) {
                e.printStackTrace();
            }
            lista.add(a);
        });
        map.put("title",query.getOutputParameterValue(2));
         map.put("list",lista);
         return map;
    }

}
