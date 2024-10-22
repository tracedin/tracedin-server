package com.univ.tracedin.domain.span;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

import com.univ.tracedin.common.dto.SearchCursor;
import com.univ.tracedin.common.dto.SearchResult;
import com.univ.tracedin.domain.project.Project;
import com.univ.tracedin.domain.project.ProjectReader;
import com.univ.tracedin.domain.project.ProjectValidator;
import com.univ.tracedin.domain.user.User;
import com.univ.tracedin.domain.user.UserId;
import com.univ.tracedin.domain.user.UserReader;

@Service
@RequiredArgsConstructor
public class SpanService {

    private final SpanReader spanReader;
    private final UserReader userReader;
    private final ProjectReader projectReader;
    private final ProjectValidator projectValidator;
    private final ConditionValidator conditionValidator;
    private final SpanMessagePublisher spanMessagePublisher;

    public void publishSpans(List<Span> spans) {
        spanMessagePublisher.publish(SpanCollectedEvent.from(spans));
    }

    public SearchResult<Trace> getTraces(UserId userId, TraceSearchCond cond, SearchCursor cursor) {
        User user = userReader.read(userId);
        Project targetProject = projectReader.read(cond.serviceNode().getProjectKey());
        projectValidator.validate(user, targetProject);
        conditionValidator.validate(cond);
        return spanReader.read(cond, cursor);
    }

    public SpanTree getSpanTree(TraceId traceId) {
        List<Span> spans = spanReader.read(traceId);
        return SpanTreeBuilder.build(spans);
    }
}
