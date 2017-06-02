package org.zalando.stups.clients.kio.spring;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;

import org.springframework.web.client.DefaultResponseErrorHandler;

import org.zalando.stups.clients.kio.NotFoundException;

/**
 * Just to transform '404' into {@link NotFoundException}.
 *
 * @author  jbellmann
 */
public class KioClientResponseErrorHandler extends DefaultResponseErrorHandler {

    @Override
    public void handleError(final ClientHttpResponse response) throws IOException {
        HttpStatus statusCode = response.getStatusCode();
        if (HttpStatus.NOT_FOUND.equals(statusCode)) {
            throw new NotFoundException();
        }

        super.handleError(response);
    }

}
