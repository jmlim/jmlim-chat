package io.jmlim.chat.config.auth;

import io.jmlim.chat.config.jwt.properties.JwtProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.micrometer.core.instrument.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.invocation.HandlerMethodArgumentResolver;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Slf4j
@Component
public class LoginUserArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        boolean isLoginUserAnnotation = Objects.nonNull(parameter.getParameterAnnotation(LoginUser.class));
        boolean isUserClass = CurrentUser.class.equals(parameter.getParameterType());
        return isLoginUserAnnotation && isUserClass;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, Message<?> message) throws Exception {
        StompHeaderAccessor wrap = StompHeaderAccessor.wrap(message);
        String token = wrap.getFirstNativeHeader(HttpHeaders.AUTHORIZATION);
        if (StringUtils.isNotBlank(token)) {
            Claims body = Jwts.parser()
                    .setSigningKey(JwtProperties.SECRET)
                    .parseClaimsJws(token.replace(JwtProperties.TOKEN_PREFIX, "")).getBody();

            return new CurrentUser(body);
        }

        throw new UnAuthorizedException("Not logged in.");
    }
}
