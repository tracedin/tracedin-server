package com.univ.tracedin.infra.metric.messaging.listener;

import java.util.List;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import com.univ.tracedin.domain.metric.ServiceMetricsCollectedEvent;
import com.univ.tracedin.domain.metric.ServiceMetricsMessageProcessor;
import com.univ.tracedin.infra.kafka.KafkaConsumer;

@Slf4j
@Component
@RequiredArgsConstructor
public class ServiceMetricsKafkaListener implements KafkaConsumer<ServiceMetricsCollectedEvent> {

    private final ServiceMetricsMessageProcessor serviceMetricsMessageProcessor;

    @Override
    @KafkaListener(
            id = "${kafka-consumer-config.service-metrics-group-id}",
            topics = "${kafka.topic.service-metrics}")
    public void receive(
            List<ServiceMetricsCollectedEvent> messages,
            List<String> keys,
            List<Integer> partitions,
            List<Long> offsets) {
        log.info(
                "{} number of service metrics collected events received with keys:{}, partitions:{} and offsets: {}",
                messages.size(),
                keys.toString(),
                partitions.toString(),
                offsets.toString());
        serviceMetricsMessageProcessor.process(messages);
    }
}
