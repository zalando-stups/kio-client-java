/**
 * Copyright (C) 2015 Zalando SE (http://tech.zalando.com)
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.zalando.stups.clients.kio.spring;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;
import org.zalando.stups.clients.kio.*;

import java.text.ParseException;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

import static java.lang.String.format;
import static java.time.ZonedDateTime.now;
import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.failBecauseExceptionWasNotThrown;
import static org.slf4j.LoggerFactory.getLogger;
import static org.springframework.http.HttpMethod.*;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.*;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;
import static org.zalando.stups.clients.kio.spring.DateTimeUtils.toIsoString;
import static org.zalando.stups.clients.kio.spring.ResourceUtil.resource;

/**
 * @author jbellmann
 */
public class RestTemplateKioOperationsTest {

    private static final String BASE_URL = "http://localhost:8080";

    private final Logger log = getLogger(getClass());

    private MockRestServiceServer mockServer;

    private RestTemplateKioOperations client;

    @Before
    public void setUp() {
        final ObjectMapper om = new ObjectMapper();
        om.findAndRegisterModules();

        final RestTemplate restTemplate = new RestTemplate();
        restTemplate.setErrorHandler(new KioClientResponseErrorHandler());
        restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());
        restTemplate.setMessageConverters(singletonList(new MappingJackson2HttpMessageConverter(om)));

        client = new RestTemplateKioOperations(restTemplate, BASE_URL);

        mockServer = MockRestServiceServer.createServer(restTemplate);
    }

    @Test
    public void testGetAllApplications() {
        mockServer.expect(requestTo(BASE_URL + "/apps")) //
                .andExpect(method(GET))                //
                .andRespond(withSuccess(resource("/getApplications"), APPLICATION_JSON));

        final List<ApplicationBase> results = client.listApplications();
        log.debug("Results: {}", results);
        assertThat(results).hasSize(2);

        mockServer.verify();
    }

    @Test
    public void testGetRangeOfApplications() throws ParseException {

        final ZonedDateTime modifiedBefore = ZonedDateTime.parse("2000-12-31T00:00:00.000+01:00[Europe/Berlin]");
        final ZonedDateTime modifiedAfter = ZonedDateTime.parse("2000-01-01T00:00:00.000-11:00[Pacific/Midway]");

        final String modifiedBeforeString = "2000-12-31T00:00:00.000%2B0100";
        final String modifiedAfterString = "2000-01-01T00:00:00.000-1100";

        mockServer.expect(requestTo(format("%s/apps?modified_before=%s&modified_after=%s", //
                BASE_URL, modifiedBeforeString, modifiedAfterString))) //
                .andExpect(method(GET)) //
                .andRespond(withSuccess(resource("/getApplications"), APPLICATION_JSON));

        final List<ApplicationBase> results = client.listApplications(Optional.of(modifiedBefore),
                Optional.of(modifiedAfter));

        log.debug("Results: {}", results);
        assertThat(results).hasSize(2);

        mockServer.verify();
    }

    @Test
    public void testSearchApplications() throws Exception {
        final String query = "foobar";

        mockServer.expect(requestTo(BASE_URL + "/apps?search=" + query)) //
                .andExpect(method(GET))                                //
                .andRespond(withSuccess(resource("/searchApplications"), APPLICATION_JSON));

        final List<ApplicationSearchResult> results = client.searchApplications(query, Optional.<ZonedDateTime>empty(),
                Optional.<ZonedDateTime>empty());
        log.debug("Results: {}", results);
        assertThat(results).hasSize(2);

        mockServer.verify();
    }

    @Test
    public void testSearchApplicationsInRange() throws Exception {
        final String query = "foobar";
        final ZonedDateTime modifiedBefore = ZonedDateTime.parse("2000-12-31T00:00:00.000Z");
        final ZonedDateTime modifiedAfter = ZonedDateTime.parse("2000-01-01T00:00:00.000Z[Greenwich]");

        final String modifiedBeforeString = "2000-12-31T00:00:00.000%2B0000";
        final String modifiedAfterString = "2000-01-01T00:00:00.000%2B0000";

        mockServer.expect(requestTo(
                BASE_URL + "/apps?search=" + query + "&modified_before=" + modifiedBeforeString
                        + "&modified_after=" + modifiedAfterString)) //
                .andExpect(method(GET))                               //
                .andRespond(withSuccess(resource("/searchApplications"), APPLICATION_JSON));

        final List<ApplicationSearchResult> results = client.searchApplications(query, Optional.of(modifiedBefore),
                Optional.of(modifiedAfter));
        log.debug("Results: {}", results);
        assertThat(results).hasSize(2);

        mockServer.verify();
    }

    @Test
    public void getApplicationById() {
        mockServer.expect(requestTo(BASE_URL + "/apps/kio")) //
                .andExpect(method(GET))                    //
                .andRespond(withSuccess(resource("/getApplicationById"), APPLICATION_JSON));

        final Application application = client.getApplicationById("kio");
        assertThat(application).isNotNull();
        assertThat(application.getId()).isEqualTo("kio");
        assertThat(application.getRequiredApprovers()).isEqualTo(12);
        mockServer.verify();
    }

    @Test
    public void testGetApplicationVersions() throws Exception {
        mockServer.expect(requestTo(BASE_URL + "/apps/kio/versions")) //
                .andExpect(method(GET))                             //
                .andRespond(withSuccess(resource("/getApplicationVersions"), APPLICATION_JSON));

        final List<VersionBase> results = client.getApplicationVersions("kio");
        log.debug("getApplicationVersions Results: {}", results);
        assertThat(results).hasSize(4);

        mockServer.verify();
    }

    @Test
    public void getApplicationVersion() {
        mockServer.expect(requestTo(BASE_URL + "/apps/kio/versions/1")) //
                .andExpect(method(GET))                               //
                .andRespond(withSuccess(resource("/getApplicationVersion"), APPLICATION_JSON));

        Version version = client.getApplicationVersion("kio", "1");
        assertThat(version).isNotNull();
        assertThat(version.getApplicationId()).isEqualTo("kio");
        assertThat(version.getId()).isEqualTo("1");

        mockServer.verify();
    }

    @Test
    public void getApplicationVersionNotFound() {
        mockServer.expect(requestTo(BASE_URL + "/apps/kio/versions/1")) //
                .andExpect(method(GET))                               //
                .andRespond(withStatus(NOT_FOUND));

        try {
            client.getApplicationVersion("kio", "1");
            failBecauseExceptionWasNotThrown(NotFoundException.class);
        } catch (final NotFoundException ignore) {
        }

        mockServer.verify();
    }

    @Test
    public void testGetApplicationApprovals() throws Exception {
        mockServer.expect(requestTo(BASE_URL + "/apps/hello-world/approvals")) //
                .andExpect(method(GET))                                      //
                .andRespond(withSuccess("[\"SPECIFICATION\",\"CODE_CHANGE\",\"TEST\",\"DEPLOY\"]", APPLICATION_JSON));

        final List<String> result = client.getApplicationApprovalTypes("hello-world");
        assertThat(result).isEqualTo(asList("SPECIFICATION", "CODE_CHANGE", "TEST", "DEPLOY"));

        mockServer.verify();
    }

    @Test
    public void testGetApplicationVersionApprovals() throws Exception {
        mockServer.expect(requestTo(BASE_URL + "/apps/kio/versions/0.14.0/approvals")) //
                .andExpect(method(GET))                                              //
                .andRespond(withSuccess(resource("/getApplicationVersionApprovals"), APPLICATION_JSON));

        final List<Approval> results = client.getApplicationVersionApprovals("kio", "0.14.0");
        assertThat(results).hasSize(5);

        mockServer.verify();
    }

    @Test
    public void testApproveApplicationVersion() throws Exception {
        mockServer.expect(requestTo(BASE_URL + "/apps/kio/versions/1.0/approvals")) //
                .andExpect(method(POST))                                          //
                .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON)) //
                .andExpect(jsonPath("$.approval_type").value("DEPLOY"))           //
                .andExpect(jsonPath("$.notes").value("bla bla bla"))              //
                .andRespond(withSuccess());

        final ApprovalBase request = new ApprovalBase();
        request.setApprovalType("DEPLOY");
        request.setNotes("bla bla bla");
        client.approveApplicationVersion(request, "kio", "1.0");

        mockServer.verify();
    }

    @Test
    public void testCreateApplication() throws Exception {
        mockServer.expect(requestTo(BASE_URL + "/apps/kio")) //
                .andExpect(method(PUT))                                          //
                .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON)) //
                .andExpect(jsonPath("$.name").value("kio"))           //
                .andExpect(jsonPath("$.active").value(true))              //
                .andRespond(withSuccess());

        final CreateOrUpdateApplicationRequest request = new CreateOrUpdateApplicationRequest();
        request.setName("kio");
        request.setActive(true);
        client.createOrUpdateApplication(request, "kio");

        mockServer.verify();
    }

    @Test
    public void testCreateApplicationVersion() throws Exception {
        mockServer.expect(requestTo(BASE_URL + "/apps/kio/versions/1.0")) //
                .andExpect(method(PUT))                                          //
                .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON)) //
                .andExpect(jsonPath("$.artifact").value("this is my artifact"))           //
                .andExpect(jsonPath("$.notes").value("some notes here"))              //
                .andRespond(withSuccess());

        final CreateOrUpdateVersionRequest request = new CreateOrUpdateVersionRequest();
        request.setArtifact("this is my artifact");
        request.setNotes("some notes here");
        client.createOrUpdateVersion(request, "kio", "1.0");

        mockServer.verify();
    }
}
