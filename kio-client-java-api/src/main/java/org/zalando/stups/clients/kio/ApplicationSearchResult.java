package org.zalando.stups.clients.kio;

import java.util.StringJoiner;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ApplicationSearchResult extends ApplicationBase {

    @JsonProperty("matched_rank")
    private Float matchedRank;

    @JsonProperty("matched_description")
    private String matchedDescription;

    public Float getMatchedRank() {
        return matchedRank;
    }

    public void setMatchedRank(final Float matchedRank) {
        this.matchedRank = matchedRank;
    }

    public String getMatchedDescription() {
        return matchedDescription;
    }

    public void setMatchedDescription(final String matchedDescription) {
        this.matchedDescription = matchedDescription;
    }

    @Override
    protected void addToStringFields(final StringJoiner fields) {
        super.addToStringFields(fields);
        fields.add("matchedRank=" + matchedRank);
        fields.add("matchedDescription" + matchedDescription);
    }
}
