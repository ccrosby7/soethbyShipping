package com.crosby.soethbyshipping.util;

import com.crosby.soethbyshipping.domain.Quote;
import com.crosby.soethbyshipping.domain.Shipment;
import com.crosby.soethbyshipping.enums.ResponseFormat;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static com.crosby.soethbyshipping.enums.ResponseFormat.JSON;
import static com.crosby.soethbyshipping.enums.ResponseFormat.XML;

public class QuoteRequestUtil {

    private static final HttpClient httpClient = HttpClient.newBuilder()
        .version(HttpClient.Version.HTTP_2)
        .connectTimeout(Duration.ofSeconds(10))
        .build();

    private static final ObjectMapper jsonMapper = new ObjectMapper();
    private static final XmlMapper xmlMapper = new XmlMapper();

    private static final Logger log = LoggerFactory.getLogger(QuoteRequestUtil.class);

    private QuoteRequestUtil() { throw new IllegalStateException("Utility class"); }

    public static List<Quote> requestQuotes(Shipment shipment, Map<String, String> urls, ResponseFormat format) {
        //could retrieve url from config here, but easier to split by protocol or only send a single provider
        ObjectMapper mapper;
        String body;
        var wrapper = new Object() { List<Quote> fullResponse; };

        if(JSON.equals(format)){
            mapper = jsonMapper;
        } else if(XML.equals(format)){
            mapper = xmlMapper;
        } else {
            return Collections.emptyList();
        }
        try {
             body = mapper.writeValueAsString(shipment);
        } catch (JsonProcessingException e) {
            log.error(e.getMessage());
            return Collections.emptyList();
        }
        urls.forEach( (provider, url) -> {
            HttpResponse<String> response = httpRequest(body, url);
            try {
                wrapper.fullResponse.add(mapper.readValue(response.body(), Quote.class));
                //messy, but puts each quote into an object for saving
            } catch (JsonProcessingException e) {
                log.error(e.getMessage());
            }
        });
        return wrapper.fullResponse;
    }

    private static HttpResponse<String> httpRequest(String body, String url){

        HttpRequest request = HttpRequest.newBuilder()
            .POST(HttpRequest.BodyPublishers.ofString(body))
            .uri(URI.create(url))
            .build();
        try {
            return httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException e) {
            log.error(e.getMessage());
        } catch (InterruptedException e) {
            log.error(e.getMessage());
        }
        return null;
    }
}
