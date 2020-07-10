package com.crosby.soethbyshipping.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "shipping.providers")
public class ShippingConfiguration {
    private HashMap<String, String> json;
    private HashMap<String, String> xml;

    public HashMap<String, String> getJson() {
        return json;
    }
    public HashMap<String, String> getXML() {
        return xml;
    }

}
