package com.univ.tracedin.common.dto;

import java.util.List;

public record Cursor(Integer page, Integer size, List<SortOrder> sortOrders) {}
