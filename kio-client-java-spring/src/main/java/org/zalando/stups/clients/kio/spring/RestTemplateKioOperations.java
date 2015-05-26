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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.core.ParameterizedTypeReference;

import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;

import org.zalando.stups.clients.kio.Application;
import org.zalando.stups.clients.kio.ApplicationBase;
import org.zalando.stups.clients.kio.Approval;
import org.zalando.stups.clients.kio.ApprovalBase;
import org.zalando.stups.clients.kio.CreateOrUpdateApplicationRequest;
import org.zalando.stups.clients.kio.CreateOrUpdateVersionRequest;
import org.zalando.stups.clients.kio.KioOperations;
import org.zalando.stups.clients.kio.Version;
import org.zalando.stups.clients.kio.VersionBase;

/**
 * Implementation of {@link KioOperations} that uses Springs {@link RestTemplate}.
 *
 * @author  jbellmann
 */
public class RestTemplateKioOperations implements KioOperations {

    private final RestOperations restOperations;

    private final String baseUrl;

    private final ParameterizedTypeReference<List<ApplicationBase>> applicationBaseParameterizedType =
        new ParameterizedTypeReference<List<ApplicationBase>>() { };

    private final ParameterizedTypeReference<List<VersionBase>> versionBaseParameterizedType =
        new ParameterizedTypeReference<List<VersionBase>>() { };

    private final ParameterizedTypeReference<List<Approval>> approvalParameterizedType =
        new ParameterizedTypeReference<List<Approval>>() { };

    public RestTemplateKioOperations(final RestOperations restOperations, final String baseUrl) {
        this.restOperations = restOperations;
        this.baseUrl = baseUrl;
    }

    @Override
    public List<ApplicationBase> listApplications() {
        ResponseEntity<List<ApplicationBase>> response = getRestOperations().exchange(baseUrl + "/apps", HttpMethod.GET,
                null, applicationBaseParameterizedType);

        return response.getBody();
    }

    @Override
    public Application getApplicationById(final String applicationId) {
        Map<String, String> uriVariables = new HashMap<String, String>();
        uriVariables.put("applicationId", applicationId);

        return getRestOperations().getForObject(baseUrl + "/apps/{applicationId}", Application.class, uriVariables);
    }

    @Override
    public void createOrUpdateApplication(final CreateOrUpdateApplicationRequest request, final String applicationId) {
        Map<String, String> uriVariables = new HashMap<String, String>();
        uriVariables.put("applicationId", applicationId);

        getRestOperations().put(baseUrl + "/apps/{applicationId}", request);
    }

    @Override
    public List<String> getApplicationApprovals(final String applicationId) {
        Map<String, String> uriVariables = new HashMap<String, String>();
        uriVariables.put("applicationId", applicationId);

        //
        return getRestOperations().getForObject(baseUrl + "/apps/{applicationId}", List.class, uriVariables);
    }

    @Override
    public List<VersionBase> getApplicationVersions(final String applicationId) {

        Map<String, String> uriVariables = new HashMap<String, String>();
        uriVariables.put("applicationId", applicationId);

        ResponseEntity<List<VersionBase>> response = getRestOperations().exchange(baseUrl
                    + "/apps/{applicationId}/versions", HttpMethod.GET, null, versionBaseParameterizedType,
                uriVariables);

        return response.getBody();

    }

    @Override
    public Version getApplicationVersion(final String applicationId, final String versionId) {

        Map<String, String> uriVariables = new HashMap<String, String>();
        uriVariables.put("applicationId", applicationId);
        uriVariables.put("versionId", versionId);

        return getRestOperations().getForObject(baseUrl + "/apps/{applicationId}/versions/{versionId}", Version.class,
                uriVariables);
    }

    @Override
    public void createOrUpdateVersion(final CreateOrUpdateVersionRequest request, final String applicationId,
            final String versionId) {

        Map<String, String> uriVariables = new HashMap<String, String>();
        uriVariables.put("applicationId", applicationId);
        uriVariables.put("versionId", versionId);

        getRestOperations().put(baseUrl + "/apps/{applicationId}/versions/{versionId}", request, uriVariables);
    }

    @Override
    public List<Approval> getApplicationApprovals(final String applicationId, final String versionId) {

        Map<String, String> uriVariables = new HashMap<String, String>();
        uriVariables.put("applicationId", applicationId);
        uriVariables.put("versionId", versionId);

        ResponseEntity<List<Approval>> response = getRestOperations().exchange(baseUrl
                    + "/apps/{applicationId}/versions/{versionId}", HttpMethod.GET, null, approvalParameterizedType,
                uriVariables);

        return response.getBody();
    }

    @Override
    public void approveApplicationVersion(final ApprovalBase request, final String applicationId,
            final String versionId) {

        Map<String, String> uriVariables = new HashMap<String, String>();
        uriVariables.put("applicationId", applicationId);
        uriVariables.put("versionId", versionId);

        getRestOperations().put(baseUrl + "/apps/{applicationId}/versions/{versionsId}", request, uriVariables);
    }

    protected RestOperations getRestOperations() {
        return this.restOperations;
    }

}
