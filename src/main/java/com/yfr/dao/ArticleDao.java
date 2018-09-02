package com.yfr.dao;

import com.yfr.po.Article;
import com.yfr.po.Bbsuser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

/**
 * Created by Koreyoshi on 2017/8/21.
 */

public interface ArticleDao extends CrudRepository<Article,Integer>{
    @Query("select a from Article a where a.rootid=:rid")
    Page<Article> findAll(Pageable pageabel, @Param("rid")Integer rid);

    @Modifying
    @Query("delete from Article  where id=:id or rootid=:id")
    int dela(@Param("id")Integer id);

    @Override
    Article save(Article article);

    @Modifying
    @Query("delete from Article  where id=:id ")
    int delc(@Param("id")Integer id);

    @Query("select a from Article a where id=rid")
    Article findOne(@Param("rid")Integer rid);
}
