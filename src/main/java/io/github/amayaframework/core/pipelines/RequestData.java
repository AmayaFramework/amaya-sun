package io.github.amayaframework.core.pipelines;

import io.github.amayaframework.core.contexts.HttpRequest;
import io.github.amayaframework.core.methods.HttpMethod;
import io.github.amayaframework.core.routers.Route;

import java.util.Objects;

/**
 * A simple container created to transfer data between pipeline actions.
 * Common form.
 */
public abstract class RequestData {
    private Route route;
    private String path;
    private HttpMethod method;
    private HttpRequest request;

    protected RequestData(Route route, String path, HttpMethod method) {
        this.route = route;
        this.path = path;
        this.method = method;
    }

    public Route getRoute() {
        return route;
    }

    public void setRoute(Route route) {
        this.route = Objects.requireNonNull(route);
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = Objects.requireNonNull(path);
    }

    public HttpMethod getMethod() {
        return method;
    }

    public void setMethod(HttpMethod method) {
        this.method = Objects.requireNonNull(method);
    }

    public HttpRequest getRequest() {
        return request;
    }

    public void setRequest(HttpRequest request) {
        this.request = Objects.requireNonNull(request);
    }
}
