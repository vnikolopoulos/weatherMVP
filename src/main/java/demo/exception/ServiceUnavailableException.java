package demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Service Unavailable Exception is thrown when the remote service (ie. geonames.org) is not available
 */
@ResponseStatus(value = HttpStatus.SERVICE_UNAVAILABLE)
public class ServiceUnavailableException extends RuntimeException {
    public ServiceUnavailableException(String error) {
        super(error);
    }

    // Override the default fillInStackTrace to hide the stack trace from the client.
    @Override
    public synchronized Throwable fillInStackTrace() {
        return this;
    }
}
