package demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class CountryNotFoundException extends RuntimeException {
    public CountryNotFoundException(String error) {
        super(error);
    }

    @Override
    public synchronized Throwable fillInStackTrace() {
        return this;
    }
}
