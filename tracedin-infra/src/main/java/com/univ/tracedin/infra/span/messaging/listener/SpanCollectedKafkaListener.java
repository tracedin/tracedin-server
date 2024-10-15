package com.univ.tracedin.infra.span.messaging.listener;

import java.util.List;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import com.univ.tracedin.domain.span.Span;
import com.univ.tracedin.domain.span.SpanAppender;
import com.univ.tracedin.domain.span.SpanCollectedEvent;
import com.univ.tracedin.infra.kafka.KafkaConsumer;

@Slf4j
@Component
@RequiredArgsConstructor
public class SpanCollectedKafkaListener implements KafkaConsumer<SpanCollectedEvent> {

    private final SpanAppender spanAppender;

    @Override
    @KafkaListener(id = "${kafka-consumer-config.span-group-id}", topics = "${kafka.topic.span}")
    public void receive(
            List<SpanCollectedEvent> messages,
            List<String> keys,
            List<Integer> partitions,
            List<Long> offsets) {
        log.info(
                "{} number of span collected events received with keys:{}, partitions:{} and offsets: {}",
                messages.size(),
                keys.toString(),
                partitions.toString(),
                offsets.toString());

        List<Span> spans =
                messages.stream().map(SpanCollectedEvent::spans).flatMap(List::stream).toList();

        spanAppender.appendAll(spans);
    }
}
