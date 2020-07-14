package com.crosby.soethbyshipping.util;

import com.crosby.soethbyshipping.domain.Quote;
import com.crosby.soethbyshipping.domain.Shipment;
import com.crosby.soethbyshipping.enums.ResponseFormat;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

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

@Component
public class QuoteRequestUtil {

    private static final HttpClient httpClient = HttpClient.newBuilder()
        .version(HttpClient.Version.HTTP_2)
        .connectTimeout(Duration.ofSeconds(10))
        .build();

    private static final ObjectMapper jsonMapper = new ObjectMapper();
    private static final XmlMapper xmlMapper = new XmlMapper();

    private static final Logger log = LoggerFactory.getLogger(QuoteRequestUtil.class);


    public static List<Quote> requestQuotes(Shipment shipment, Map<String, String> urls, ResponseFormat format) {
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
            final String body = mapper.writeValueAsString(shipment);
            var wrapper = new Object(){ List<Quote> fullResponse; };
             urls.forEach( (provider, url) -> {
                 CompletableFuture<HttpResponse<String>> response = httpRequest(body, url);
                 String result;
                 try {
                     result = response.thenApply(HttpResponse::body).get(5, TimeUnit.SECONDS);
                     wrapper.fullResponse.add(mapper.readValue(result, Quote.class));
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

    private static CompletableFuture<HttpResponse<String>> httpRequest(String body, String url){

        HttpRequest request = HttpRequest.newBuilder()
            .GET()
            .uri(URI.create(url))
            .setHeader("User-Agent", "Java 11 HttpClient Bot")
            .build();
        return httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString());

    }
}
