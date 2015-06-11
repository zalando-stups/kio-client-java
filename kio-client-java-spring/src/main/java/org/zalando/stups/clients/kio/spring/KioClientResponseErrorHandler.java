package org.zalando.stups.clients.kio.spring;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;

import org.springframework.web.client.DefaultResponseErrorHandler;

import org.zalando.stups.clients.kio.NotFoudException;

/**
 * Just to transform '404' into {@link NotFoudException}.
 *
 * @author  jbellmann
 */
public class KioClientResponseErrorHandler extends DefaultResponseErrorHandler {

    @Override
    public void handleError(final ClientHttpResponse response) throws IOException {
        HttpStatus statusCode = response.getStatusCode();
        if (HttpStatus.NOT_FOUND.equals(statusCode)) {
            throw new NotFoudException();
        }

        super.handleError(response);
    }

}
