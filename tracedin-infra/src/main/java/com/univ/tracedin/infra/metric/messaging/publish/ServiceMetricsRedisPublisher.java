package com.univ.tracedin.infra.metric.messaging.publish;

import static com.univ.tracedin.infra.metric.config.MetricRedisConfig.METRIC_CHANNEL_ID;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

import com.univ.tracedin.domain.metric.ServiceMetrics;
import com.univ.tracedin.domain.metric.ServiceMetricsSender;

@Component
@RequiredArgsConstructor
public class ServiceMetricsRedisPublisher implements ServiceMetricsSender {

    private final RedisTemplate<String, ServiceMetrics> redisTemplate;

    @Override
    public void send(ServiceMetrics serviceMetrics) {
        redisTemplate.convertAndSend(METRIC_CHANNEL_ID, serviceMetrics);
    }
}
