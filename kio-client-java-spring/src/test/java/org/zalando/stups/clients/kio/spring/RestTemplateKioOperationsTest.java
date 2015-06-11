/**
 * Copyright (C) 2015 Zalando SE (http://tech.zalando.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.zalando.stups.clients.kio.spring;

import static org.assertj.core.api.Assertions.assertThat;

import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.MediaType.APPLICATION_JSON;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

import static org.zalando.stups.clients.kio.spring.ResourceUtil.resource;

import java.util.List;

import org.assertj.core.api.Assertions;

import org.junit.Before;
import org.junit.Test;

import org.springframework.http.HttpStatus;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;

import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.BaseOAuth2ProtectedResourceDetails;

import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.client.response.MockRestResponseCreators;

import org.zalando.stups.clients.kio.Application;
import org.zalando.stups.clients.kio.ApplicationBase;
import org.zalando.stups.clients.kio.NotFoudException;
import org.zalando.stups.clients.kio.Version;

/**
 * @author  jbellmann
 */
public class RestTemplateKioOperationsTest {

    private final String baseUrl = "http://localhost:8080";

    private OAuth2RestTemplate restTemplate;

    private MockRestServiceServer mockServer;

    private RestTemplateKioOperations client;

// private AccessTo

    @Before
    public void setUp() {

        BaseOAuth2ProtectedResourceDetails resource = new BaseOAuth2ProtectedResourceDetails();
        resource.setClientId("what_here");

        restTemplate = new OAuth2RestTemplate(resource);
        restTemplate.setErrorHandler(new KioClientResponseErrorHandler());
        restTemplate.setAccessTokenProvider(new TestAccessTokenProvider("86c45354-8bc4-44bf-905f-5f34ebe0b599"));
        restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());

        client = new RestTemplateKioOperations(restTemplate, baseUrl);

        mockServer = MockRestServiceServer.createServer(restTemplate);
    }

    //J-
    @Test
    public void getRefoles() {
        mockServer.expect(requestTo(baseUrl + "/apps"))
                    .andExpect(method(GET))
                    .andRespond(withSuccess(resource("/getApplications"), APPLICATION_JSON));

        List<ApplicationBase> resultLists = client.listApplications();
        assertThat(resultLists).isNotNull();
        assertThat(resultLists).isNotEmpty();
        assertThat(resultLists.size()).isEqualTo(1);

        mockServer.verify();
    }

    @Test
    public void getApplicationById() {
        mockServer.expect(requestTo(baseUrl + "/apps/kio"))
                    .andExpect(method(GET))
                    .andRespond(withSuccess(resource("/getApplicationById"), APPLICATION_JSON));

        Application application = client.getApplicationById("kio");
        assertThat(application).isNotNull();
        assertThat(application.getId()).isEqualTo("kio");

        mockServer.verify();
    }

    @Test
    public void getApplicationVersion() {
        mockServer.expect(requestTo(baseUrl + "/apps/kio/versions/1"))
                    .andExpect(method(GET))
                    .andRespond(withSuccess(resource("/getApplicationVersion"), APPLICATION_JSON));

        Version version = client.getApplicationVersion("kio", "1");
        assertThat(version).isNotNull();
        assertThat(version.getApplicationId()).isEqualTo("kio");
        assertThat(version.getId()).isEqualTo("1");


        mockServer.verify();
    }

    @Test
    public void getApplicationVersionNotFound() {
        mockServer.expect(requestTo(baseUrl + "/apps/kio/versions/1"))
                    .andExpect(method(GET))
                    .andRespond(MockRestResponseCreators.withStatus(HttpStatus.NOT_FOUND));

        Exception ex = null;
        try{
            Version version = client.getApplicationVersion("kio", "1");
        }catch(NotFoudException e){
            ex = e;
        }

        Assertions.assertThat(ex).isInstanceOf(NotFoudException.class);

        mockServer.verify();
    }
    //J+

}
