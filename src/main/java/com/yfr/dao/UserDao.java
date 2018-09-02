package com.yfr.dao;

import com.yfr.po.Bbsuser;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

/**
 * Created by Koreyoshi on 2017/8/20.
 */
public interface UserDao extends CrudRepository<Bbsuser,Integer>{
    @Query("select b from Bbsuser as b where username=:u and password=:p")
    Bbsuser login(@Param("u")String username, @Param("p")String password);


    @Query("select b from Bbsuser as b where username=:u")
    Bbsuser judge(@Param("u")String username);

    @Override
    Bbsuser findOne(Integer id);

    @Override
    Bbsuser save(Bbsuser var1);

    @Modifying
    @Query("update  Bbsuser set pagenum=:p where userid=:id ")
    int setPagenum(@Param("p")Integer pagenum,@Param("id")Integer id);

    @Modifying
    @Query("update  Bbsuser set flag=:f where userid=:id ")
    int setflag(@Param("f")Integer flag,@Param("id")Integer id);
}

