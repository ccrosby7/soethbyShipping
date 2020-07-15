package com.crosby.soethbyshipping.config;

import org.springframework.beans.factory.annotation.Value;
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
    public HashMap<String, String> getXml() {
        return xml;
    }

    public void setXml(HashMap<String, String> xml) {
        this.xml = xml;
    }

    public void setJson(HashMap<String, String> json) {
        this.json = json;
    }
}
