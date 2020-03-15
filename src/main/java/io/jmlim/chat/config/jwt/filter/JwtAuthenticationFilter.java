package io.jmlim.chat.config.jwt.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jmlim.chat.config.auth.AccountAdapter;
import io.jmlim.chat.config.auth.LoginRequestDto;
import io.jmlim.chat.config.jwt.properties.JwtProperties;
import io.jmlim.chat.service.ChatService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;

    private final ChatService chatService;


    /**
     * Trigger when we issue POST request to /login
     * We also need to pass in {"username":"jmlim", "password":"password"}
     * in the request body
     *
     * @param request
     * @param response
     * @return
     * @throws AuthenticationException
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        // Grab credentials and map then to LoginViewModel
        LoginRequestDto credentials = null;
        try {
            credentials = new ObjectMapper()
                    .readValue(request.getInputStream(), LoginRequestDto.class);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }

        // Create login token
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                credentials.getUsername(),
                credentials.getPassword(),
                new ArrayList<>()
        );

        // Authenticate user
        Authentication auth = authenticationManager.authenticate(authenticationToken);
        return auth;
    }

    /**
     * 인증 후 처리.
     *
     * @param request
     * @param response
     * @param chain
     * @param authentication
     */
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                            FilterChain chain, Authentication authentication) {
        Object principal = authentication.getPrincipal();
        AccountAdapter accountAdapter = null;
        if (principal instanceof AccountAdapter) {
            accountAdapter = ((AccountAdapter) principal);
        }

        Date now = new Date();
        Map<String, Object> claims = new HashMap<>();
        String username = accountAdapter.getUsername();

        claims.put("id", accountAdapter.getId());
        claims.put("name", accountAdapter.getName());
        claims.put("username", username);

        Collection<GrantedAuthority> authorities = accountAdapter.getAuthorities();
        List<String> roles = authorities.stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        claims.put("roles", roles);

        String token = Jwts.builder()
                .setSubject(username)
                .setClaims(claims) // 데이터
                // .setIssuedAt(now) // 토큰 발행일자
                .setExpiration(new Date(now.getTime() + JwtProperties.EXPIRATION_TIME)) // set Expire Time
                .signWith(SignatureAlgorithm.HS256, JwtProperties.SECRET) // 암호화 알고리즘, secret값 세팅
                .compact();

        chatService.deleteByEmail(username);
        // Add token in response
        response.addHeader(HttpHeaders.AUTHORIZATION, JwtProperties.TOKEN_PREFIX + token);
    }
}