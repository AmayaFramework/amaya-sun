package io.github.amayaframework.core.actions;

import com.github.romanqed.jutils.util.Checks;
import io.github.amayaframework.core.pipeline.InputAction;
import io.github.amayaframework.core.util.ParseUtil;

import javax.servlet.http.Cookie;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>The action during which the request cookies are parsed.</p>
 * <p>Receives: {@link SunRequestData}</p>
 * <p>Returns: {@link SunRequestData}</p>
 */
public class ParseRequestCookiesAction extends InputAction<SunRequestData, SunRequestData> {

    @Override
    public SunRequestData execute(SunRequestData requestData) {
        String header = requestData.getRequest().getHeader(ParseUtil.COOKIE_HEADER);
        if (header == null) {
            requestData.getRequest().setCookies(Collections.unmodifiableMap(new HashMap<>()));
            return requestData;
        }
        Map<String, Cookie> cookies = Checks.requireNonException(
                () -> ParseUtil.parseCookieHeader(header),
                HashMap::new
        );
        requestData.getRequest().setCookies(Collections.unmodifiableMap(cookies));
        return requestData;
    }
}