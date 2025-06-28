package com.pixelocura.bitscafe.security;

import org.springframework.http.HttpHeaders;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CustomDiscordOAuth2UserService extends DefaultOAuth2UserService {
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getInterceptors().add((request, body, execution) -> {
            request.getHeaders().add(HttpHeaders.USER_AGENT, "8Bits1Cafe/1.0");
            return execution.execute(request, body);
        });
        setRestOperations(restTemplate);
        return super.loadUser(userRequest);
    }
}
