package com.univ.tracedin.infra.span.messaging.listener;

import java.util.List;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import com.univ.tracedin.domain.span.TraceId;
import com.univ.tracedin.infra.anomaly.AnomalyTraceResult;
import com.univ.tracedin.infra.kafka.KafkaConsumer;

@Slf4j
@Component
@RequiredArgsConstructor
public class AnomalyTraceKafkaListener implements KafkaConsumer<TraceId, AnomalyTraceResult> {

    @Override
    @KafkaListener(
            id = "${kafka-consumer-config.anomaly-trace-group-id}",
            topics = "${kafka.topic.anomaly-trace}")
    public void receive(
            List<AnomalyTraceResult> messages,
            List<TraceId> keys,
            List<Integer> partitions,
            List<Long> offsets) {
        log.info(
                "{} number of span collected events received with keys:{}, partitions:{} and offsets: {}",
                messages.size(),
                keys.toString(),
                partitions.toString(),
                offsets.toString());

        // TODO : AnomalySpanIds를 찾아 Abnormal 태그를 추가해 업데이트 작업 및 AnomalyTrace 테이블에 따로 저장
    }
}
