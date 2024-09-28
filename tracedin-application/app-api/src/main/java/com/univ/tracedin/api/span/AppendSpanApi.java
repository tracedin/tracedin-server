package com.univ.tracedin.api.span;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

import com.univ.tracedin.api.span.dto.AppendSpanRequest;
import com.univ.tracedin.domain.span.SpanService;

@RestController
@RequestMapping("/api/v1/spans")
@RequiredArgsConstructor
public class AppendSpanApi {

    private final SpanService spanService;

    @PostMapping
    public void appendSpan(@RequestBody AppendSpanRequest request) {
        spanService.appendSpan(request.toSpan());
    }
}
