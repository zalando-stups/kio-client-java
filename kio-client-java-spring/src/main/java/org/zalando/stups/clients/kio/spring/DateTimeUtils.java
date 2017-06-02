package org.zalando.stups.clients.kio.spring;

import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;

public final class DateTimeUtils {

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");

    private DateTimeUtils() {
    }

    public static String toIsoString(final TemporalAccessor temporal) {

        return DATE_TIME_FORMATTER.format(temporal);
    }
}
