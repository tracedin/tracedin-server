package com.univ.tracedin.infra.elasticsearch;

import java.io.IOException;

public interface ESSupplier<T> {

    T get() throws IOException;
}
