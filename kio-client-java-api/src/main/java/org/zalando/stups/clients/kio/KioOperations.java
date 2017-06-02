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

import java.util.List;
import java.util.Optional;

public interface KioOperations {

    /**
     * Returns a list of registered applications in Kio. This is equivalent to<br />
     * <code>listApplications(Optional.empty(), Optional.empty())</code>
     *
     * @return  {@link ApplicationBase} subset of {@link Application}.
     */
    List<ApplicationBase> listApplications();

    /**
     * Returns a list of registered applications in Kio. Optionally, the result can be filtered to show only apps, that
     * have been modified within a certain range in time.
     *
     * @return  {@link ApplicationBase} subset of {@link Application}.
     */
    List<ApplicationBase> listApplications(Optional<ZonedDateTime> modifiedBefore,
            Optional<ZonedDateTime> modifiedAfter);

    List<ApplicationSearchResult> searchApplications(String query, Optional<ZonedDateTime> modifiedBefore,
            Optional<ZonedDateTime> modifiedAfter);

    /**
     * Returns an application for the given applicationId.
     *
     * @return  an {@link Application}
     */
    Application getApplicationById(String applicationId);

    /**
     * Creates or updates an application.
     */
    void createOrUpdateApplication(CreateOrUpdateApplicationRequest request, String applicationId);

    /**
     * Returns a list of all approval types for the specified applicationId.
     * @deprecated Please note that the GitHub approval flow obsoletes Kio versions,
     *   i.e. you don't have to maintain version information in Kio anymore.
     *   The corresponding API endpoints will be disabled on July 31st, 2017.
     */
    @Deprecated
    List<String> getApplicationApprovalTypes(String applicationId);

    /**
     * @deprecated Please note that the GitHub approval flow obsoletes Kio versions,
     *   i.e. you don't have to maintain version information in Kio anymore.
     *   The corresponding API endpoints will be disabled on July 31st, 2017.
     */
    @Deprecated
    List<VersionBase> getApplicationVersions(String applicationId);

    /**
     * @deprecated Please note that the GitHub approval flow obsoletes Kio versions,
     * i.e. you don't have to maintain version information in Kio anymore.
     * The corresponding API endpoints will be disabled on July 31st, 2017.
     */
    @Deprecated
    Version getApplicationVersion(String applicationId, String versionId);

    /**
     * @deprecated Please note that the GitHub approval flow obsoletes Kio versions,
     *   i.e. you don't have to maintain version information in Kio anymore.
     *   The corresponding API endpoints will be disabled on July 31st, 2017.
     */
    @Deprecated
    void createOrUpdateVersion(CreateOrUpdateVersionRequest request, String applicationId, String versionId);

    /**
     * @deprecated Please note that the GitHub approval flow obsoletes Kio versions,
     *   i.e. you don't have to maintain version information in Kio anymore.
     *   The corresponding API endpoints will be disabled on July 31st, 2017.
     */
    @Deprecated
    List<Approval> getApplicationVersionApprovals(String applicationId, String versionId);

    /**
     * @deprecated Please note that the GitHub approval flow obsoletes Kio versions,
     *   i.e. you don't have to maintain version information in Kio anymore.
     *   The corresponding API endpoints will be disabled on July 31st, 2017.
     */
    @Deprecated
    void approveApplicationVersion(ApprovalBase request, String applicationId, String versionId);

}
