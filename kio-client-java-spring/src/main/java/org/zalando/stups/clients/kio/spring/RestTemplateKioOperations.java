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

    public static final ParameterizedTypeReference<List<String>> AS_STRING_LIST =
        new ParameterizedTypeReference<List<String>>() { };

    private static final ParameterizedTypeReference<List<ApplicationBase>> AS_APP_BASE_LIST =
        new ParameterizedTypeReference<List<ApplicationBase>>() { };

    private static final ParameterizedTypeReference<List<ApplicationSearchResult>> AS_APP_SEARCH_RESULT_LIST =
        new ParameterizedTypeReference<List<ApplicationSearchResult>>() { };

    private static final ParameterizedTypeReference<List<VersionBase>> AS_VERSION_LIST =
        new ParameterizedTypeReference<List<VersionBase>>() { };

    private static final ParameterizedTypeReference<List<Approval>> AS_APPROVAL_LIST =
        new ParameterizedTypeReference<List<Approval>>() { };

    private final RestOperations restOperations;

    private final String baseUrl;

    public RestTemplateKioOperations(final RestOperations restOperations, final String baseUrl) {
        this.restOperations = restOperations;
        this.baseUrl = baseUrl;
    }

    @Override
    public List<ApplicationBase> listApplications() {
        return listApplications(Optional.<ZonedDateTime>empty(), Optional.<ZonedDateTime>empty());
    }

    @Override
    public List<ApplicationBase> listApplications(final Optional<ZonedDateTime> modifiedBefore,
            final Optional<ZonedDateTime> modifiedAfter) {
        final UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(baseUrl).pathSegment("apps");
        modifiedBefore.ifPresent((timestamp) -> uriBuilder.queryParam("modified_before", toIsoString(timestamp)));
        modifiedAfter.ifPresent((timestamp) -> uriBuilder.queryParam("modified_after", toIsoString(timestamp)));

        final URI uri = uriBuilder.build().toUri();
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

        final URI uri = uriBuilder.build().toUri();
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

    @Override
    public List<String> getApplicationApprovalTypes(final String applicationId) {
        Assert.hasText(applicationId, "applicationId must not be blank");

        final URI uri = URI.create(baseUrl + "/apps/" + applicationId + "/approvals");
        return getRestOperations().exchange(get(uri).build(), AS_STRING_LIST).getBody();
    }

    @Override
    public List<VersionBase> getApplicationVersions(final String applicationId) {
        Assert.hasText(applicationId, "applicationId must not be blank");

        final URI uri = URI.create(baseUrl + "/apps/" + applicationId + "/versions");
        return getRestOperations().exchange(get(uri).build(), AS_VERSION_LIST).getBody();
    }

    @Override
    public Version getApplicationVersion(final String applicationId, final String versionId) {
        Assert.hasText(applicationId, "applicationId must not be blank");
        Assert.hasText(versionId, "versionId must not be blank");

        final URI uri = URI.create(baseUrl + "/apps/" + applicationId + "/versions/" + versionId);
        return getRestOperations().getForObject(uri, Version.class);
    }

    @Override
    public void createOrUpdateVersion(final CreateOrUpdateVersionRequest request, final String applicationId,
            final String versionId) {
        Assert.hasText(applicationId, "applicationId must not be blank");
        Assert.hasText(versionId, "versionId must not be blank");

        Map<String, String> uriVariables = new HashMap<>();
        uriVariables.put("applicationId", applicationId);
        uriVariables.put("versionId", versionId);

        getRestOperations().put(baseUrl + "/apps/{applicationId}/versions/{versionId}", request, uriVariables);
    }

    @Override
    public List<Approval> getApplicationVersionApprovals(final String applicationId, final String versionId) {
        Assert.hasText(applicationId, "applicationId must not be blank");
        Assert.hasText(versionId, "versionId must not be blank");

        final URI uri = URI.create(baseUrl + "/apps/" + applicationId + "/versions/" + versionId + "/approvals");
        return getRestOperations().exchange(get(uri).build(), AS_APPROVAL_LIST).getBody();
    }

    @Override
    public void approveApplicationVersion(final ApprovalBase request, final String applicationId,
            final String versionId) {
        Assert.hasText(applicationId, "applicationId must not be blank");
        Assert.hasText(versionId, "versionId must not be blank");

        final URI uri = URI.create(baseUrl + "/apps/" + applicationId + "/versions/" + versionId + "/approvals");
        getRestOperations().postForObject(uri, request, Void.class);
    }

    protected RestOperations getRestOperations() {
        return this.restOperations;
    }

}
