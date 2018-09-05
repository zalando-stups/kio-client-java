package org.zalando.stups.clients.kio.spring;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.util.Assert;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import org.zalando.stups.clients.kio.*;

import java.net.URI;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.springframework.http.RequestEntity.get;
import static org.zalando.stups.clients.kio.spring.DateTimeUtils.toIsoString;

/**
 * Implementation of {@link KioOperations} that uses Springs {@link RestTemplate}.
 *
 * @author  jbellmann
 */
public class RestTemplateKioOperations implements KioOperations {

    private ParameterizedTypeReference<List<ApplicationBase>> AS_APP_BASE_LIST =
        new ParameterizedTypeReference<List<ApplicationBase>>() { };

    private ParameterizedTypeReference<List<ApplicationSearchResult>> AS_APP_SEARCH_RESULT_LIST =
        new ParameterizedTypeReference<List<ApplicationSearchResult>>() { };

    private final RestOperations restOperations;

    private final String baseUrl;

    public RestTemplateKioOperations(final RestOperations restOperations, final String baseUrl) {
        this.restOperations = restOperations;
        this.baseUrl = baseUrl;
    }

    @Override
    public List<ApplicationBase> listApplications() {
        return listApplications(Optional.empty(), Optional.empty());
    }

    @Override
    public List<ApplicationBase> listApplications(final Optional<ZonedDateTime> modifiedBefore,
            final Optional<ZonedDateTime> modifiedAfter) {
        ParameterizedTypeReference<List<ApplicationBase>> typeReference = new ParameterizedTypeReference<List<ApplicationBase>>() { };
        final UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(baseUrl).pathSegment("apps");
        modifiedBefore.ifPresent((timestamp) -> uriBuilder.queryParam("modified_before", toIsoString(timestamp)));
        modifiedAfter.ifPresent((timestamp) -> uriBuilder.queryParam("modified_after", toIsoString(timestamp)));

        final URI uri = uriBuilder.build().encode().toUri();
        return getRestOperations().exchange(get(uri).build(), AS_APP_BASE_LIST).getBody();
    }

    @Override
    public List<ApplicationSearchResult> searchApplications(final String query,
            final Optional<ZonedDateTime> modifiedBefore, final Optional<ZonedDateTime> modifiedAfter) {
        Assert.hasText(query, "search query must not be blank");

        final UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(baseUrl).pathSegment("apps");
        uriBuilder.queryParam("search", query);
        modifiedBefore.ifPresent((timestamp) -> uriBuilder.queryParam("modified_before", toIsoString(timestamp)));
        modifiedAfter.ifPresent((timestamp) -> uriBuilder.queryParam("modified_after", toIsoString(timestamp)));

        final URI uri = uriBuilder.build().encode().toUri();
        return getRestOperations().exchange(get(uri).build(), AS_APP_SEARCH_RESULT_LIST).getBody();
    }

    @Override
    public Application getApplicationById(final String applicationId) {
        Assert.hasText(applicationId, "applicationId must not be blank");

        return getRestOperations().getForObject(URI.create(baseUrl + "/apps/" + applicationId), Application.class);
    }

    @Override
    public void createOrUpdateApplication(final CreateOrUpdateApplicationRequest request, final String applicationId) {

        Assert.hasText(applicationId, "applicationId must not be blank");

        final Map<String, String> uriVariables = new HashMap<>();
        uriVariables.put("applicationId", applicationId);
        getRestOperations().put(baseUrl + "/apps/{applicationId}", request, uriVariables);
    }

    protected RestOperations getRestOperations() {
        return this.restOperations;
    }

}
