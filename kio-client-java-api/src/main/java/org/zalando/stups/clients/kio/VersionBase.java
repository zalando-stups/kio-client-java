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
import lombok.EqualsAndHashCode;

@JsonIgnoreProperties(ignoreUnknown = true)
@EqualsAndHashCode
public class VersionBase {

    private String id;

    @JsonProperty("application_id")
    private String applicationId;

    private String artifact;

    @JsonProperty("last_modified")
    private ZonedDateTime lastModified;

    public String getId() {
        return id;
    }

    public void setId(final String id) {
        this.id = id;
    }

    public String getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(final String applicationId) {
        this.applicationId = applicationId;
    }

    public String getArtifact() {
        return artifact;
    }

    public void setArtifact(final String artifact) {
        this.artifact = artifact;
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
        fields.add("applicationId='" + applicationId + '\'');
        fields.add("artifact='" + artifact + '\'');
        fields.add("lastModified=" + lastModified);
    }
}
