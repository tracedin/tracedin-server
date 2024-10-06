package com.univ.tracedin.infra.span.repository;

import java.io.IOException;

public interface ESSupplier<T> {

    T get() throws IOException;
}
