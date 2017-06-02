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
@EqualsAndHashCode
@Data
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
