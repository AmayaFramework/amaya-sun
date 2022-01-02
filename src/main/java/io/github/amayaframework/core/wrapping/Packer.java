package io.github.amayaframework.core.wrapping;

import io.github.amayaframework.core.contexts.HttpRequest;
import io.github.amayaframework.core.contexts.HttpResponse;

import java.lang.reflect.Method;
import java.util.function.Function;

public interface Packer {
    Function<HttpRequest, HttpResponse> pack(Object instance, Method method) throws Exception;

    Function<HttpRequest, HttpResponse> checkedPack(Object instance, Method method);
}
