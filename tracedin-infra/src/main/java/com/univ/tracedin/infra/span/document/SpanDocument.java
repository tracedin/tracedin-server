package com.univ.tracedin.infra.span.document;

import java.io.Serializable;
import java.util.Map;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.Mapping;
import org.springframework.data.elasticsearch.annotations.Setting;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import com.univ.tracedin.domain.span.Span;

@Document(indexName = "span")
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Getter
@Mapping(mappingPath = "elastic/span-mappings.json")
@Setting(settingPath = "elastic/span-settings.json")
public class SpanDocument implements Serializable {

    @Id
    @Field(type = FieldType.Keyword)
    private String spanId;

    @Field(type = FieldType.Keyword)
    private String serviceName;

    @Field(type = FieldType.Keyword)
    private String traceId;

    @Field(type = FieldType.Keyword)
    private String parentSpanId;

    @Field(type = FieldType.Text)
    private String name;

    @Field(type = FieldType.Keyword)
    private String kind;

    @Field(type = FieldType.Long)
    private Long startEpochNanos;

    @Field(type = FieldType.Long)
    private Long endEpochNanos;

    @Field(type = FieldType.Nested)
    private Attributes attributes;

    public static SpanDocument from(Span span) {
        return SpanDocument.builder()
                .spanId(span.getSpanId())
                .serviceName(span.getServiceName())
                .traceId(span.getTraceId())
                .parentSpanId(span.getParentSpanId())
                .name(span.getName())
                .kind(span.getKind())
                .startEpochNanos(span.getStartEpochNanos())
                .endEpochNanos(span.getEndEpochNanos())
                .attributes(Attributes.from(span.getAttributes()))
                .build();
    }

    public Span toSpan() {
        return Span.builder()
                .spanId(spanId)
                .serviceName(serviceName)
                .traceId(traceId)
                .parentSpanId(parentSpanId)
                .name(name)
                .kind(kind)
                .startEpochNanos(startEpochNanos)
                .endEpochNanos(endEpochNanos)
                .attributes(attributes.toSpanAttributes())
                .build();
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @Builder
    public static class Attributes {

        @Field(type = FieldType.Object)
        private Map<String, Object> data;

        @Field(type = FieldType.Integer)
        private Integer capacity;

        @Field(type = FieldType.Integer)
        private Integer totalAddedValues;

        public static Attributes from(Span.Attributes spanAttributes) {
            return Attributes.builder()
                    .data(spanAttributes.getData())
                    .capacity(spanAttributes.getCapacity())
                    .totalAddedValues(spanAttributes.getTotalAddedValues())
                    .build();
        }

        public Span.Attributes toSpanAttributes() {
            return Span.Attributes.builder()
                    .data(data)
                    .capacity(capacity)
                    .totalAddedValues(totalAddedValues)
                    .build();
        }
    }
}
