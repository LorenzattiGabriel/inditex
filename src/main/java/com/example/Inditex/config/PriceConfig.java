package com.example.Inditex.config;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;


@Configuration
@ConfigurationProperties(prefix = "general-config")
public class PriceConfig {

    private HashMap<String, String> properties;

    public HashMap<String, String> getProperties() {
        return this.properties;
    }


}
