package com.tekworks.microservice.config.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.OAuth2AuthorizeRequest;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;

import feign.RequestInterceptor;
import feign.RequestTemplate;

@Configuration
public class FeignClientInterceptor implements RequestInterceptor {

    @Autowired
    private OAuth2AuthorizedClientManager manager;

    @Override
    public void apply(RequestTemplate template) {
        OAuth2AuthorizedClient client = manager.authorize(
                OAuth2AuthorizeRequest.withClientRegistrationId("my-client").principal("internal").build());

        if (client == null || client.getAccessToken() == null) {
            throw new RuntimeException("OAuth2 Authorization Failed: No Access Token");
        }

        String token = client.getAccessToken().getTokenValue();
        template.header("Authorization", "Bearer " + token);
    }
}
