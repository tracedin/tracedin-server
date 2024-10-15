package com.univ.tracedin.infra.kafka;

import java.io.Serializable;
import java.util.concurrent.CompletableFuture;

import org.springframework.kafka.support.SendResult;

public interface KafkaProducer<K extends Serializable, V extends Serializable> {

    void send(String topicName, K key, V message, CompletableFuture<SendResult<K, V>> callback);
}
