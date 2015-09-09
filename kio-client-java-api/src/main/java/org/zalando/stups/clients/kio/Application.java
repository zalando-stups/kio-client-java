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
public class Application extends ApplicationBase {

    private boolean active;

    private String description;

    @JsonProperty("required_approvers")
    private int requiredApprovers;

    @JsonProperty("specification_type")
    private String specificationType;

    @JsonProperty("criticality_level")
    private int criticalityLevel;

    @JsonProperty("last_modified_by")
    private String lastModifiedBy;

    private ZonedDateTime created;

    @JsonProperty("created_by")
    private String createdBy;

    @JsonProperty("publicly_accessible")
    private boolean publiclyAccessible;

    public boolean isActive() {
        return active;
    }

    public void setActive(final boolean active) {
        this.active = active;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public int getRequiredApprovers() {
        return requiredApprovers;
    }

    public void setRequiredApprovers(final int requiredApprovers) {
        this.requiredApprovers = requiredApprovers;
    }

    public String getSpecificationType() {
        return specificationType;
    }

    public void setSpecificationType(final String specificationType) {
        this.specificationType = specificationType;
    }

    public int getCriticalityLevel() {
        return criticalityLevel;
    }

    public void setCriticalityLevel(final int criticalityLevel) {
        this.criticalityLevel = criticalityLevel;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(final String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public ZonedDateTime getCreated() {
        return created;
    }

    public void setCreated(final ZonedDateTime created) {
        this.created = created;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(final String createdBy) {
        this.createdBy = createdBy;
    }

    public boolean isPubliclyAccessible() {
        return publiclyAccessible;
    }

    public void setPubliclyAccessible(final boolean publiclyAccessible) {
        this.publiclyAccessible = publiclyAccessible;
    }

    @Override
    protected void addToStringFields(final StringJoiner fields) {
        super.addToStringFields(fields);
        fields.add("active=" + active);
        fields.add("description='" + description + '\'');
        fields.add("requiredApprovers=" + requiredApprovers);
        fields.add("specificationType='" + specificationType + '\'');
        fields.add("criticalityLevel=" + criticalityLevel);
        fields.add("lastModifiedBy='" + lastModifiedBy + '\'');
        fields.add("created=" + created);
        fields.add("createdBy='" + createdBy + '\'');
        fields.add("publiclyAccessible=" + publiclyAccessible);
    }
}
