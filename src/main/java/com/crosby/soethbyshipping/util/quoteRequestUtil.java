package com.crosby.soethbyshipping.util;

import com.crosby.soethbyshipping.enums.ResponseFormat;
import com.crosby.soethbyshipping.service.dto.QuoteDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import liquibase.pro.packaged.bW;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class quoteRequestUtil {

    private static final HttpClient httpClient = HttpClient.newBuilder()
        .version(HttpClient.Version.HTTP_2)
        .connectTimeout(Duration.ofSeconds(10))
        .build();

    private final ObjectMapper jsonMapper = new ObjectMapper();
    private final XmlMapper xmlMapper = new XmlMapper();

    private final Logger log = LoggerFactory.getLogger(quoteRequestUtil.class);


    public List<QuoteDTO> requestQuotes(JsonNode json, Map<String, String> urls, ResponseFormat format) {
        //could retrieve url from config here, but easier to split by protocol or only send a single provider
        ObjectMapper mapper;
        if(format.JSON.equals(format)){
            mapper = jsonMapper;
        } else if(format.XML.equals(format)){
            mapper = xmlMapper;
        } else {
            return null;
        }
        try {
            final String body = mapper.writeValueAsString(json);
            var wrapper = new Object(){ List<QuoteDTO> fullResponse; };
             urls.forEach( (provider, url) -> {
                 CompletableFuture<HttpResponse<String>> response = httpRequest(body, url);
                 String result;
                 try {
                     result = response.thenApply(HttpResponse::body).get(5, TimeUnit.SECONDS);
                     wrapper.fullResponse.add(mapper.readValue(result, QuoteDTO.class));
                     //messy, but puts each quote into an object for saving
                 } catch (InterruptedException|ExecutionException|TimeoutException| JsonProcessingException e) {
                     log.error(e.getMessage()); //move all this??
                 }

             });
             return wrapper.fullResponse;

        } catch (JsonProcessingException e) {
            log.error(e.getMessage());
        }
        return null;
    }

    private CompletableFuture<HttpResponse<String>> httpRequest(String body, String url){

        HttpRequest request = HttpRequest.newBuilder()
            .GET()
            .uri(URI.create(url))
            .setHeader("User-Agent", "Java 11 HttpClient Bot")
            .build();
        return httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString());

    }
}
