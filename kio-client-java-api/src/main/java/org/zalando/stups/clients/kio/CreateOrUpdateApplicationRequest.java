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
