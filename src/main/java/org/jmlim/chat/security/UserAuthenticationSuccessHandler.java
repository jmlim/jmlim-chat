package org.jmlim.chat.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Component("authenticationSuccessHandler")
public class UserAuthenticationSuccessHandler extends
		SavedRequestAwareAuthenticationSuccessHandler implements
		AuthenticationSuccessHandler {

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request,
			HttpServletResponse response, Authentication authentication)
			throws IOException, ServletException {
		HttpSession session = request.getSession();
		UserPrincipal principal = (UserPrincipal) authentication.getPrincipal();
		session.setAttribute("currentUser", principal.getUser());
		super.onAuthenticationSuccess(request, response, authentication);

	}

}
