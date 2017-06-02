package org.zalando.stups.clients.kio;

import java.util.StringJoiner;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@JsonIgnoreProperties(ignoreUnknown = true)
@EqualsAndHashCode(callSuper = true)
@Data
public class ApplicationSearchResult extends ApplicationBase {

    @JsonProperty("matched_rank")
    private Float matchedRank;

    @JsonProperty("matched_description")
    private String matchedDescription;

    @Override
    protected void addToStringFields(final StringJoiner fields) {
        super.addToStringFields(fields);
        fields.add("matchedRank=" + matchedRank);
        fields.add("matchedDescription" + matchedDescription);
    }
}
