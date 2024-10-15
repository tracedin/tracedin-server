package com.univ.tracedin.infra.kafka;

import java.io.Serializable;
import java.util.List;

public interface KafkaConsumer<T extends Serializable> {

    void receive(List<T> messages, List<String> keys, List<Integer> partitions, List<Long> offsets);
}
