package com.univ.tracedin.infra.kafka;

import java.io.Serializable;
import java.util.List;

public interface KafkaConsumer<K extends Serializable, T extends Serializable> {

    void receive(List<T> messages, List<K> keys, List<Integer> partitions, List<Long> offsets);
}
