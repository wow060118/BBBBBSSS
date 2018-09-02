//package com.yfr.services.kafka.MsgProducer;
//
//import org.apache.kafka.clients.producer.RecordMetadata;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.kafka.support.ProducerListener;
//
///**
// * Created by Koreyoshi on 2017/8/29.
// */
//public class MsgListener implements ProducerListener {
//    protected final Logger LOG = LoggerFactory.getLogger("kafkaProducer");
//
//    @Override
//    public void onSuccess(String topic, Integer partition, Object key,
//                          Object value, RecordMetadata recordMetadata) {
//
//        LOG.info("==========kafka send data success start==========");
//        LOG.info("----------topic:"+topic);
//        LOG.info("----------partition:"+partition);
//        LOG.info("----------key:"+key);
//        LOG.info("----------value:"+value);
//        LOG.info("----------RecordMetadata:"+recordMetadata);
//        LOG.info("~~~~~~~~~~kafka send data success end~~~~~~~~~~");
//    }
//
//
//    @Override
//    public void onError(String topic, Integer partition, Object key,
//                        Object value, Exception exception) {
//        LOG.info("==========kafka send data success start==========");
//        LOG.info("----------topic:"+topic);
//        LOG.info("----------partition:"+partition);
//        LOG.info("----------key:"+key);
//        LOG.info("----------value:"+value);
//        LOG.info("----------Exception:"+exception);
//        LOG.info("~~~~~~~~~~kafka send data success end~~~~~~~~~~");
//        exception.printStackTrace();
//    }
//
//    @Override
//    public boolean isInterestedInSuccess() {
//        LOG.info("///kafkaProducer listener start///");
//        return true;
//    }
//
//}
