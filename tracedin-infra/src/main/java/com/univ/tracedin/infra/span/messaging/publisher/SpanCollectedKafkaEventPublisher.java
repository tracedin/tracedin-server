package com.univ.tracedin.infra.span.messaging.publisher;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import com.univ.tracedin.domain.span.SpanCollectedEvent;
import com.univ.tracedin.domain.span.SpanCollectedMessagePublisher;
import com.univ.tracedin.infra.kafka.KafkaMessageHelper;
import com.univ.tracedin.infra.kafka.KafkaProducer;

@Slf4j
@Component
@RequiredArgsConstructor
public class SpanCollectedKafkaEventPublisher implements SpanCollectedMessagePublisher {

    private final KafkaProducer<String, SpanCollectedEvent> kafkaProducer;
    private final KafkaMessageHelper kafkaMessageHelper;

    @Value("${kafka.topic.span}")
    private String spanTopic;

    @Override
    public void publish(SpanCollectedEvent event) {
        kafkaProducer.send(
                spanTopic, event.getKey(), event, kafkaMessageHelper.getKafkaCallback(event));
    }
}
