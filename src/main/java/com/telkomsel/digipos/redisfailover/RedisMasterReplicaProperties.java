package com.telkomsel.digipos.redisfailover;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "spring.redis")
public class RedisMasterReplicaProperties {
    private String host;
    private int port;
    private String password;
    private RedisMasterReplicaProperties master;
    private RedisMasterReplicaProperties replica;

}
