package hello.client.test;

import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.HttpStatusCodeException;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;


/**
 * If the response represents a retryable error,
 * then RetryableResponseInterceptor wraps the exception in a RetryableException.
 */
public class RetryableResponseInterceptor implements ClientHttpRequestInterceptor {

    private final List<Integer> retryableStatusCodes;

    public RetryableResponseInterceptor(Integer ... retryableStatusCodes) {
        this.retryableStatusCodes = Arrays.asList(retryableStatusCodes);
    }

    @Override
    public ClientHttpResponse intercept(HttpRequest httpRequest, byte[] bytes,
                                        ClientHttpRequestExecution clientHttpRequestExecution)
    {
        try {
            final ClientHttpResponse response = clientHttpRequestExecution.execute(httpRequest, bytes);
            if (retryableStatusCodes.contains(response.getRawStatusCode())) {
                throw new RetryableException(new HttpStatusCodeException(response.getStatusCode(),
                        response.getStatusText()) {});
            }
            return response;
        } catch (IOException e) {
            // IOExceptions are automatically retriable
            throw new RetryableException(e);
        }
    }

    public static class RetryableException extends RuntimeException {
        public RetryableException(String msg) {
            super(msg);
        }

        public RetryableException(String msg, Throwable cause) {
            super(msg, cause);
        }

        public RetryableException(Throwable cause) {
            super(cause);
        }
    }
}
