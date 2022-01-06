package io.github.amayaframework.core.pipelines;

import com.github.romanqed.jutils.structs.pipeline.PipelineInterruptException;
import io.github.amayaframework.core.contexts.HttpResponse;
import io.github.amayaframework.server.utils.HttpCode;

import java.util.Objects;
import java.util.function.Function;

/**
 * <p>An abstract class containing supporting code for implementing an action.
 * All custom pipeline actions should inherit from this class.</p>
 * <p>@param {@link T} type of receiving parameter</p>
 * <p>@param {@link R} type of parameter to be returned</p>
 */
public abstract class PipelineAction<T, R> implements Function<T, R> {
    private final String name;

    public PipelineAction(String name) {
        this.name = Objects.requireNonNull(name);
    }

    /**
     * Returns action name, specified at constructor
     *
     * @return {@link String} name can not be null
     */
    public String getName() {
        return name;
    }

    /**
     * <p>A method that allows you to break the pipeline with the given result.</p>
     * <p>Note: Java does not recognize it as return or throw, so in some cases it is necessary to make an
     * additional return after the call, but this code will never be executed, since execution will
     * terminate immediately after calling this method.</p>
     *
     * @param ret {@link Object} the object to be returned
     */
    protected void interrupt(Object ret) {
        throw new PipelineInterruptException(null, ret);
    }

    /**
     * <p>A method that allows you to interrupt the pipeline by returning an HttpResponse with the specified code.</p>
     * <p>Note: Java does not recognize it as return or throw, so in some cases it is necessary to make an
     * additional return after the call, but this code will never be executed, since execution will
     * terminate immediately after calling this method.</p>
     *
     * @param code {@link HttpCode} code to be returned
     */
    protected void reject(HttpCode code) {
        HttpResponse ret = new HttpResponse(code);
        ret.setBody(code.getMessage());
        interrupt(ret);
    }
}
