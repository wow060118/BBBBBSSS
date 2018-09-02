//package com.yfr.services.kafka.MsgProducer;
//
//import com.alibaba.fastjson.JSON;
//import com.yfr.po.Article;
//import com.yfr.po.Msg;
//import com.yfr.services.ArticleImpl;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.kafka.core.KafkaTemplate;
//import org.springframework.stereotype.Component;
//
//import java.util.Date;
//
///**
// * Created by Koreyoshi on 2017/8/29.
// */
//@Component
//public class MsgProducer {
//    @Autowired
//    private KafkaTemplate<String ,String> kafkaTemplate;
//
//    @Autowired
//    private ArticleImpl service;
//
//    public void sendMsg(Article a){
//        Msg msg =new Msg();
//        msg.setUid(service.findOne(a.getRootid()).getUser().getUserid());
//        msg.setMeid(a.getUser().getUserid());
//        msg.setTxt(service.findOne(a.getRootid()).getTitle());
//        msg.setSendTime(new Date());
//        msg.setRtitle(a.getTitle());
//        String topic="reply"+msg.getUid();
//        kafkaTemplate.setProducerListener(new MsgListener());
//        System.out.println(JSON.toJSONString(msg));
//        kafkaTemplate.send(topic,JSON.toJSONString(msg));
//
//
//    }
//
//}
