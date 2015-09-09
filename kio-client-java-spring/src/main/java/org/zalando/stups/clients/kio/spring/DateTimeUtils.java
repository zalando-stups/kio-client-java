package org.zalando.stups.clients.kio.spring;

import static java.time.format.DateTimeFormatter.ISO_OFFSET_DATE_TIME;

import java.time.temporal.TemporalAccessor;

public final class DateTimeUtils {

    private DateTimeUtils() { }

    public static String toIsoString(final TemporalAccessor temporal) {
        return ISO_OFFSET_DATE_TIME.format(temporal);
    }
}
