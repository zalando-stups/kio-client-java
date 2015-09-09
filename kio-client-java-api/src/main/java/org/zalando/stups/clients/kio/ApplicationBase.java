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
package org.zalando.stups.clients.kio;

import java.time.ZonedDateTime;

import java.util.StringJoiner;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author  jbellmann
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ApplicationBase {

    private String id;

    @JsonProperty("team_id")
    private String teamId;

    private String name;

    private String subtitle;

    @JsonProperty("service_url")
    private String serviceUrl;

    @JsonProperty("scm_url")
    private String scmUrl;

    @JsonProperty("documentation_url")
    private String documentationUrl;

    @JsonProperty("specification_url")
    private String specificationUrl;

    @JsonProperty("last_modified")
    private ZonedDateTime lastModified;

    public String getId() {
        return id;
    }

    public void setId(final String id) {
        this.id = id;
    }

    public String getTeamId() {
        return teamId;
    }

    public void setTeamId(final String teamId) {
        this.teamId = teamId;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(final String subtitle) {
        this.subtitle = subtitle;
    }

    public String getServiceUrl() {
        return serviceUrl;
    }

    public void setServiceUrl(final String serviceUrl) {
        this.serviceUrl = serviceUrl;
    }

    public String getScmUrl() {
        return scmUrl;
    }

    public void setScmUrl(final String scmUrl) {
        this.scmUrl = scmUrl;
    }

    public String getDocumentationUrl() {
        return documentationUrl;
    }

    public void setDocumentationUrl(final String documentationUrl) {
        this.documentationUrl = documentationUrl;
    }

    public String getSpecificationUrl() {
        return specificationUrl;
    }

    public void setSpecificationUrl(final String specificationUrl) {
        this.specificationUrl = specificationUrl;
    }

    public ZonedDateTime getLastModified() {
        return lastModified;
    }

    public void setLastModified(final ZonedDateTime lastModified) {
        this.lastModified = lastModified;
    }

    @Override
    public String toString() {
        final StringJoiner fields = new StringJoiner(", ", getClass().getSimpleName() + "{", "}");
        addToStringFields(fields);
        return fields.toString();
    }

    protected void addToStringFields(final StringJoiner fields) {
        fields.add("id='" + id + '\'');
        fields.add("teamId='" + teamId + '\'');
        fields.add("name='" + name + '\'');
        fields.add("subtitle='" + subtitle + '\'');
        fields.add("serviceUrl='" + serviceUrl + '\'');
        fields.add("scmUrl='" + scmUrl + '\'');
        fields.add("documentationUrl='" + documentationUrl + '\'');
        fields.add("specificationUrl='" + specificationUrl + '\'');
        fields.add("lastModified=" + lastModified);
    }

}
