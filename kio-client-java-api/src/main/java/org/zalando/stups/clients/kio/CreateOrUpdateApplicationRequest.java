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

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@JsonIgnoreProperties(ignoreUnknown = true)
@EqualsAndHashCode
@Data
public class CreateOrUpdateApplicationRequest {

    @JsonProperty("team_id")
    private String teamId;

    @JsonProperty("active")
    private Boolean active;

    @JsonProperty("name")
    private String name;

    @JsonProperty("subtitle")
    private String subtitle;

    @JsonProperty("description")
    private String description;

    @JsonProperty("service_url")
    private String serviceUrl;

    @JsonProperty("scm_url")
    private String scmUrl;

    @JsonProperty("documentation_url")
    private String documentationUrl;

    @JsonProperty("specification_url")
    private String specificationUrl;

    @JsonProperty("required_approvers")
    private String requiredApprovers;

    @JsonProperty("specification_type")
    private String specificationType;

    @JsonProperty("publicly_accessible")
    private Boolean publiclyAccessible;

}
