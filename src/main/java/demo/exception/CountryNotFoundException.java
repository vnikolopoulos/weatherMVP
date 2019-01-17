package demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Country not found exception is thrown when the country code in not valid or
 * not found by the service.
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class CountryNotFoundException extends RuntimeException {
    public CountryNotFoundException(String error) {
        super(error);
    }

    // Override the default fillInStackTrace to hide the stack trace from the client.
    @Override
    public synchronized Throwable fillInStackTrace() {
        return this;
    }
}
