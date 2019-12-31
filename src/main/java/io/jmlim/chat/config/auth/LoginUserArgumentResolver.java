package io.jmlim.chat.config.auth;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.invocation.HandlerMethodArgumentResolver;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
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
        Object simpUser = wrap.getHeader("simpUser");
        if(simpUser instanceof UsernamePasswordAuthenticationToken) {
            UsernamePasswordAuthenticationToken userToken = (UsernamePasswordAuthenticationToken) simpUser;
            Object principal = userToken.getPrincipal();
            log.info("------> principal : {}", principal);
            if (principal instanceof AccountAdapter) {
                AccountAdapter accountAdapter = (AccountAdapter) principal;
                return new CurrentUser(accountAdapter);
            }
        }
        throw new UnAuthorizedException("Not logged in.");
    }
}
