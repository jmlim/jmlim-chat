package io.jmlim.chat.config.auth;

import io.jmlim.chat.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
@Component
public class CustomLoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    private final ChatService chatService;

    /**
     * 로그인 시 계정의 로그인 한 계정의 채팅 참여정보 전부 삭제.
     *
     * @param request
     * @param response
     * @param authentication
     * @throws ServletException
     * @throws IOException
     */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws ServletException, IOException {

        Object principal = authentication.getPrincipal();
        String username = null;
        if (principal instanceof AccountAdapter) {
            username = ((AccountAdapter) principal).getUsername();
        }

        chatService.deleteByEmail(username);
        super.onAuthenticationSuccess(request, response, authentication);
    }
}