package com.univ.tracedin.api.global.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.univ.tracedin.common.annotation.CursorDefault;
import com.univ.tracedin.common.dto.Cursor;
import com.univ.tracedin.common.dto.SortDirection;
import com.univ.tracedin.common.dto.SortOrder;

public class CursorArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().equals(Cursor.class)
                && parameter.hasParameterAnnotation(CursorDefault.class);
    }

    @Override
    public Object resolveArgument(
            MethodParameter parameter,
            ModelAndViewContainer mavContainer,
            NativeWebRequest webRequest,
            WebDataBinderFactory binderFactory) {
        CursorDefault cursorDefault = parameter.getParameterAnnotation(CursorDefault.class);

        String pageParam = webRequest.getParameter("page");
        int page = (pageParam != null) ? Integer.parseInt(pageParam) : cursorDefault.page();

        String sizeParam = webRequest.getParameter("size");
        int size = (sizeParam != null) ? Integer.parseInt(sizeParam) : cursorDefault.size();

        List<SortOrder> sortOrders = new ArrayList<>();
        webRequest
                .getParameterMap()
                .forEach(
                        (key, value) -> {
                            System.out.println(key + " : " + value);
                        });
        String sortFieldsParam = webRequest.getParameter("sortOrders[key]");
        String sortDirectionsParam = webRequest.getParameter("sortOrders[direction]");

        String[] fields =
                (sortFieldsParam != null)
                        ? sortFieldsParam.split(",")
                        : cursorDefault.sortFields().split(",");
        String[] directions =
                (sortDirectionsParam != null)
                        ? sortDirectionsParam.split(",")
                        : cursorDefault.sortDirections().split(",");

        for (int i = 0; i < Math.min(fields.length, directions.length); i++) {
            SortDirection direction = SortDirection.valueOf(directions[i].trim().toUpperCase());
            sortOrders.add(new SortOrder(fields[i].trim(), direction));
        }

        return new Cursor(page, size, sortOrders);
    }
}
