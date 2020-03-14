package io.jmlim.chat.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

/**
 * jmlim@neowiz.com
 */
@Data
@ConfigurationProperties(prefix = "broker.relay")
public class BrokerRelayProperties {
    private String relayHost;
    private int relayPort;
    private String clientLogin;
    private String clientPasscode;
}
