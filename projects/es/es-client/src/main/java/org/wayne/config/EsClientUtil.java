package org.wayne.config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @author: lwq
 */
public class EsClientUtil {

    private static RestClient client;

    private EsClientUtil() {
    }

    public static RestClient getInstance() {
        if (client == null) {
            final String host = "192.168.2.2";
            final RestClientBuilder builder = RestClient.builder(
                    new HttpHost(host, 9200, "http"),
                    new HttpHost(host, 9201, "http"),
                    new HttpHost(host, 9202, "http")
            );
            client = builder.build();
        }
        return client;
    }
}
