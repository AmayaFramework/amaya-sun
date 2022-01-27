package io.github.amayaframework.core.handlers;

import io.github.amayaframework.core.configurators.BaseSunConfigurator;
import io.github.amayaframework.core.contexts.HttpResponse;
import io.github.amayaframework.core.controllers.Controller;
import io.github.amayaframework.core.util.AmayaConfig;
import io.github.amayaframework.server.interfaces.HttpExchange;
import io.github.amayaframework.server.interfaces.HttpHandler;
import io.github.amayaframework.server.utils.HttpCode;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.util.Collections;

/**
 * <p>A class representing the sun handler used inside the server. Built on pipelines.</p>
 * <p>The http connection processing scheme is quite simple: first, the input pipeline is triggered,
 * resulting in an HttpResponse. Then the output pipeline is triggered, the purpose of which is to
 * process and verify the received HttpResponse. After that, the server receives a response.</p>
 */
public class SunHandler implements HttpHandler {
    private final Charset charset = AmayaConfig.INSTANCE.getCharset();
    private final IOHandler handler;

    public SunHandler(Controller controller) {
        handler = new BaseIOHandler(controller, Collections.singletonList(new BaseSunConfigurator()));
    }

    protected void reject(HttpExchange exchange) throws IOException {
        sendAnswer(exchange, HttpCode.INTERNAL_SERVER_ERROR, HttpCode.INTERNAL_SERVER_ERROR.getMessage());
    }

    protected void sendAnswer(HttpExchange exchange, HttpCode code, String body) throws IOException {
        if (body == null) {
            exchange.sendResponseHeaders(code, 0);
            exchange.close();
            return;
        }
        exchange.sendResponseHeaders(code, body.getBytes(charset).length);
        BufferedWriter writer = wrapOutputStream(exchange.getResponseBody());
        writer.write(body);
        writer.flush();
        exchange.close();
    }

    protected BufferedWriter wrapOutputStream(OutputStream stream) {
        return new BufferedWriter(new OutputStreamWriter(stream, charset));
    }

    public IOHandler getHandler() {
        return handler;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        HttpResponse response = (HttpResponse) handler.process(exchange).getResult();
        String body;
        try {
            body = (String) response.getBody();
        } catch (Exception e) {
            reject(exchange);
            return;
        }
        exchange.getResponseHeaders().putAll(response.getHeaderMap());
        sendAnswer(exchange, response.getCode(), body);
    }
}