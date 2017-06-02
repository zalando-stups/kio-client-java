package org.zalando.stups.clients.kio.spring;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;
import org.zalando.stups.clients.kio.Application;
import org.zalando.stups.clients.kio.ApplicationBase;
import org.zalando.stups.clients.kio.ApplicationSearchResult;
import org.zalando.stups.clients.kio.CreateOrUpdateApplicationRequest;

import java.text.ParseException;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

import static java.lang.String.format;
import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.slf4j.LoggerFactory.getLogger;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.PUT;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;
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

        final String modifiedBeforeString = "2000-12-31T00:00:00.000%2B01:00";
        final String modifiedAfterString = "2000-01-01T00:00:00.000-11:00";

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

        final List<ApplicationSearchResult> results = client.searchApplications(query, Optional.empty(),
                Optional.empty());
        log.debug("Results: {}", results);
        assertThat(results).hasSize(2);

        mockServer.verify();
    }

    @Test
    public void testSearchApplicationsInRange() throws Exception {
        final String query = "foobar";
        final ZonedDateTime modifiedBefore = ZonedDateTime.parse("2000-12-31T00:00:00.000Z");
        final ZonedDateTime modifiedAfter = ZonedDateTime.parse("2000-01-01T00:00:00.000Z[Greenwich]");

        final String modifiedBeforeString = "2000-12-31T00:00:00.000Z";
        final String modifiedAfterString = "2000-01-01T00:00:00.000Z";

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
}
