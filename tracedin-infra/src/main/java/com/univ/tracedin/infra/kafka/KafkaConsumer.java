package com.univ.tracedin.infra.kafka;

import java.io.Serializable;
import java.util.List;

import org.apache.kafka.clients.consumer.ConsumerRecord;

public interface KafkaConsumer<K extends Serializable, T extends Serializable> {

    void receive(List<ConsumerRecord<K, T>> records);
}
