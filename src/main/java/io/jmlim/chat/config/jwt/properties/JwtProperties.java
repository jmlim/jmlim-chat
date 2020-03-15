package io.jmlim.chat.config.jwt.properties;

public class JwtProperties {
    public static final String SECRET = "jmlimjwt@$&;";
    public static final Long EXPIRATION_TIME = 1000L * 60 * 60; // 1시간
    public static final String TOKEN_PREFIX = "Bearer ";
}
