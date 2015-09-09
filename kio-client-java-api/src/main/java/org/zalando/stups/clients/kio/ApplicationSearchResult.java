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
