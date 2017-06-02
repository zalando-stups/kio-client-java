package org.zalando.stups.clients.kio;

import java.time.ZonedDateTime;

import java.util.StringJoiner;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author  jbellmann
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@EqualsAndHashCode(callSuper = true)
@Data
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
