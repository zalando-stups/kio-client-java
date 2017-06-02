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
}
