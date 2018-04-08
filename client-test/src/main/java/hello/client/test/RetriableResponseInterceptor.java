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
 * If the response represents a retriable error,
 * then RetriableResponseInterceptor wraps the exception in a RetriableException.
 */
public class RetriableResponseInterceptor implements ClientHttpRequestInterceptor {

    private final List<Integer> retriableStatusCodes;

    public RetriableResponseInterceptor(Integer ... retriableStatusCodes) {
        this.retriableStatusCodes = Arrays.asList(retriableStatusCodes);
    }

    @Override
    public ClientHttpResponse intercept(HttpRequest httpRequest, byte[] bytes,
                                        ClientHttpRequestExecution clientHttpRequestExecution)
    {
        try {
            final ClientHttpResponse response = clientHttpRequestExecution.execute(httpRequest, bytes);
            if (retriableStatusCodes.contains(response.getRawStatusCode())) {
                throw new RetriableException(new HttpStatusCodeException(response.getStatusCode(),
                        response.getStatusText()) {});
            }
            return response;
        } catch (IOException e) {
            // IOExceptions are automatically retriable
            throw new RetriableException(e);
        }
    }

    public static class RetriableException extends RuntimeException {
        public RetriableException(String msg) {
            super(msg);
        }

        public RetriableException(String msg, Throwable cause) {
            super(msg, cause);
        }

        public RetriableException(Throwable cause) {
            super(cause);
        }
    }
}
