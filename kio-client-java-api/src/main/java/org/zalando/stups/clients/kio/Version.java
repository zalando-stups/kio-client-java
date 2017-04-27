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
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author  jbellmann
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@EqualsAndHashCode
@Data
public class Version extends VersionBase {

    @JsonProperty("last_modified_by")
    private String lastModifiedBy;

    private ZonedDateTime created;

    @JsonProperty("created_by")
    private String createdBy;

    private String notes;

    @Override
    protected void addToStringFields(final StringJoiner fields) {
        super.addToStringFields(fields);
        fields.add("lastModifiedBy='" + lastModifiedBy + '\'');
        fields.add("created=" + created);
        fields.add("createdBy='" + createdBy + '\'');
        fields.add("notes='" + notes + '\'');
    }
}
