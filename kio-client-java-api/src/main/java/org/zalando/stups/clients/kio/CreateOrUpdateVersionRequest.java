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

/**
 * @deprecated Please note that the GitHub approval flow obsoletes Kio versions,
 *   i.e. you don't have to maintain version information in Kio anymore.
 *   The corresponding API endpoints will be disabled on July 31st, 2017.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@EqualsAndHashCode
@Data
@Deprecated
public class CreateOrUpdateVersionRequest {

    @JsonProperty("artifact")
    private String artifact;

    @JsonProperty("notes")
    private String notes;

}